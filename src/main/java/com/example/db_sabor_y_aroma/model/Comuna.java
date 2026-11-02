package com.example.db_sabor_y_aroma.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comunas")
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    private Integer idComuna;

    @Column(name = "nombre_comuna", nullable = false, length = 100)
    private String nombreComuna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_region", nullable = false)
    private Region region;

    public Comuna() {}

    public Integer getIdComuna() { return idComuna; }
    public void setIdComuna(Integer idComuna) { this.idComuna = idComuna; }

    public String getNombreComuna() { return nombreComuna; }
    public void setNombreComuna(String nombreComuna) { this.nombreComuna = nombreComuna; }

    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
}