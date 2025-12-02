package com.tienda.mascota.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido = LocalDateTime.now();
    
    @Column(name = "estado", nullable = false)
    private String estado = "PENDIENTE"; // PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO
    
    @Column(name = "direccion_envio")
    private String direccionEnvio;
    
    @Column(name = "metodo_pago")
    private String metodoPago;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();
    
    // Método para calcular total
    public void calcularTotal() {
        BigDecimal nuevoTotal = BigDecimal.ZERO;
        if (detalles != null && !detalles.isEmpty()) {
            for (DetallePedido detalle : detalles) {
                if (detalle.getSubtotal() != null) {
                    nuevoTotal = nuevoTotal.add(detalle.getSubtotal());
                }
            }
        }
        this.total = nuevoTotal;
    }
    
    @PrePersist
    @PreUpdate
    private void onSave() {
        calcularTotal();
    }
    
    // Método para agregar detalle
    public void agregarDetalle(DetallePedido detalle) {
        detalle.setPedido(this);
        this.detalles.add(detalle);
        calcularTotal();
    }
}
