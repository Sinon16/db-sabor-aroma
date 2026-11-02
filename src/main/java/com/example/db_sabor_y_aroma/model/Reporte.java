package com.saboraroma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_reporte", nullable = false, length = 50)
    private String tipoReporte;

    @Column(nullable = false, length = 50)
    private String especialidad;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoReporte estadoReporte;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}