package com.tienda.mascota.repository;

import com.tienda.mascota.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByDestacadoTrue();
    
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Producto> buscarPorNombreODescripcion(String query);
    
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);
}
