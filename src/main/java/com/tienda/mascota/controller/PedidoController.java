package com.tienda.mascota.controller;

import com.tienda.mascota.model.Pedido;
import com.tienda.mascota.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class PedidoController {
    
    private final PedidoService pedidoService;
    
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Map<String, Object> request) {
        try {
            Long usuarioId = Long.valueOf(request.get("usuarioId").toString());
            List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
            String direccionEnvio = (String) request.get("direccionEnvio");
            String metodoPago = (String) request.get("metodoPago");
            
            Pedido pedido = pedidoService.crearPedido(usuarioId, items, direccionEnvio, metodoPago);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Pedido creado exitosamente");
            response.put("pedido", pedido);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> obtenerPedidosUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorUsuario(usuarioId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPedidoPorId(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodosPedidos());
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String estado = request.get("estado");
            Pedido pedido = pedidoService.actualizarEstadoPedido(id, estado);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Estado actualizado");
            response.put("pedido", pedido);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        try {
            pedidoService.cancelarPedido(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Pedido cancelado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
