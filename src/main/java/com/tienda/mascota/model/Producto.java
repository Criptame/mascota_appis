package com.tienda.mascota.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @Column(nullable = false)
    private BigDecimal precio;
    
    @Column(name = "precio_oferta")
    private BigDecimal precioOferta;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Column(name = "url_imagen")
    private String urlImagen;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    @Column(name = "destacado")
    private boolean destacado = false;
}
