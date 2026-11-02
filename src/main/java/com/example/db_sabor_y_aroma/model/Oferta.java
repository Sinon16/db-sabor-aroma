package com.saboraroma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ofertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private Integer idOferta;

    @Column(name = "porcentaje_descuento", nullable = false)
    private Integer porcentajeDescuento;
}