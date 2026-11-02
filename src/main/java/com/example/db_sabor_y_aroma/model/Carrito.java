package com.saboraroma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "carrito", uniqueConstraints = {
    @UniqueConstraint(name = "usuario_producto_unique", columnNames = {"usuario_id", "producto_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer cantidad = 1;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
