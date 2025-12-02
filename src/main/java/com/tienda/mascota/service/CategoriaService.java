package com.tienda.mascota.service;

import com.tienda.mascota.model.Categoria;
import com.tienda.mascota.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    
    public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.findAll();
    }
    
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
    
    @Transactional
    public Categoria crearCategoria(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria categoriaActualizada) {
        Categoria categoria = obtenerCategoriaPorId(id);
        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public void eliminarCategoria(Long id) {
        // Verificar si hay productos en esta categoría antes de eliminar
        Categoria categoria = obtenerCategoriaPorId(id);
        // Aquí podrías agregar validación si la categoría tiene productos
        categoriaRepository.delete(categoria);
    }
}