package com.tienda.mascota.repository;

import com.tienda.mascota.model.Categoria;
import com.tienda.mascota.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class CategoriaController {
    
    private final CategoriaService categoriaService;
    
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodasCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerTodasCategorias());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.crearCategoria(categoria));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, categoria));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
