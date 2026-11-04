package com.example.db_sabor_y_aroma.repository;

import com.example.db_sabor_y_aroma.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuarioId(Long usuarioId);
}