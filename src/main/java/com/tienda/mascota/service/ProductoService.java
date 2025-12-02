package com.tienda.mascota.service;

import com.tienda.mascota.model.Producto;
import com.tienda.mascota.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    
    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }
    
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    
    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Producto> obtenerProductosDestacados() {
        return productoRepository.findByDestacadoTrue();
    }
    
    public List<Producto> buscarProductos(String query) {
        return productoRepository.buscarPorNombreODescripcion(query);
    }
    
    @Transactional
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    
    @Transactional
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto = obtenerProductoPorId(id);
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        return productoRepository.save(producto);
    }
    
    @Transactional
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
