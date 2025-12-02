package com.tienda.mascota.controller;

import com.tienda.mascota.model.Producto;
import com.tienda.mascota.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class ProductoController {
    
    private final ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosProductos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> obtenerProductosPorCategoria(
            @PathVariable Long categoriaId) {
        return ResponseEntity.ok(productoService.obtenerProductosPorCategoria(categoriaId));
    }
    
    @GetMapping("/destacados")
    public ResponseEntity<List<Producto>> obtenerProductosDestacados() {
        return ResponseEntity.ok(productoService.obtenerProductosDestacados());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam String q) {
        return ResponseEntity.ok(productoService.buscarProductos(q));
    }
    
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.crearProducto(producto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
