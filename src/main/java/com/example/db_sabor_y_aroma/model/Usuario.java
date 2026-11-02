package com.saboraroma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String comuna;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean esadmin = false;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}