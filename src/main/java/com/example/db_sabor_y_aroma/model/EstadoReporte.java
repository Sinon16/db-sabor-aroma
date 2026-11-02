package com.saboraroma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado_reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;
}