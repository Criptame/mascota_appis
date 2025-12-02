package com.tienda.mascota.repository;

import com.tienda.mascota.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long usuarioId);
    
    @Query("SELECT p FROM Pedido p ORDER BY p.fechaPedido DESC")
    List<Pedido> findAllOrderByFechaDesc();
    
    List<Pedido> findByEstado(String estado);
    
    @Query("SELECT p FROM Pedido p WHERE p.usuario.id = :usuarioId AND p.estado = :estado")
    List<Pedido> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}
