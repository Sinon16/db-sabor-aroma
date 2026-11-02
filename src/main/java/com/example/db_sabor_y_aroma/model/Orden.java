package com.saboraroma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoOrden estadoOrden;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleOrden> detalles;

    @PrePersist
    public void prePersist() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}