package com.tienda.mascota.service;

import com.tienda.mascota.model.*;
import com.tienda.mascota.repository.PedidoRepository;
import com.tienda.mascota.repository.ProductoRepository;
import com.tienda.mascota.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    
    @Transactional
    public Pedido crearPedido(Long usuarioId, List<Map<String, Object>> items, 
                            String direccionEnvio, String metodoPago) {
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDireccionEnvio(direccionEnvio);
        pedido.setMetodoPago(metodoPago);
        pedido.setEstado("PENDIENTE");
        
        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        
        for (Map<String, Object> item : items) {
            Long productoId = Long.valueOf(item.get("productoId").toString());
            Integer cantidad = Integer.valueOf(item.get("cantidad").toString());
            
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoId));
            
            // Verificar stock
            if (producto.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }
            
            // Actualizar stock
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);
            
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.calcularSubtotal(); // Calcular subtotal
            
            detalles.add(detalle);
            total = total.add(detalle.getSubtotal());
        }
        
        pedido.setDetalles(detalles);
        pedido.setTotal(total); // Establecer el total calculado
        
        return pedidoRepository.save(pedido);
    }
    
    // El resto de los métodos permanecen igual...
    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }
    
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
    
    public List<Pedido> obtenerTodosPedidos() {
        return pedidoRepository.findAllOrderByFechaDesc();
    }
    
    @Transactional
    public Pedido actualizarEstadoPedido(Long id, String estado) {
        Pedido pedido = obtenerPedidoPorId(id);
        pedido.setEstado(estado);
        return pedidoRepository.save(pedido);
    }
    
    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = obtenerPedidoPorId(id);
        
        // Solo se puede cancelar si está pendiente
        if (!"PENDIENTE".equals(pedido.getEstado())) {
            throw new RuntimeException("Solo se pueden cancelar pedidos pendientes");
        }
        
        // Devolver stock de productos
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }
        
        pedido.setEstado("CANCELADO");
        pedidoRepository.save(pedido);
    }
}
