package com.tienda.mascota.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedido")
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;
    
    @Column(name = "subtotal")
    private BigDecimal subtotal = BigDecimal.ZERO;
    
    // Método público para calcular subtotal
    public void calcularSubtotal() {
        if (this.precioUnitario != null && this.cantidad != null) {
            this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
        }
    }
    
    // Método @PrePersist y @PreUpdate
    @PrePersist
    @PreUpdate
    private void onSave() {
        calcularSubtotal();
    }
}
