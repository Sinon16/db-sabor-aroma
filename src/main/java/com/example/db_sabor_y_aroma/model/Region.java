package com.example.db_sabor_y_aroma.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "regiones")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Integer idRegion;

    @Column(name = "nombre_region", nullable = false, length = 100)
    private String nombreRegion;

    public Region() {}

    public Integer getIdRegion() { return idRegion; }
    public void setIdRegion(Integer idRegion) { this.idRegion = idRegion; }

    public String getNombreRegion() { return nombreRegion; }
    public void setNombreRegion(String nombreRegion) { this.nombreRegion = nombreRegion; }
}