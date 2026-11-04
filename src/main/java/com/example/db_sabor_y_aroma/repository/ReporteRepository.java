package com.example.db_sabor_y_aroma.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.db_sabor_y_aroma.model.Reporte;


public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
    List<Reporte> findAllByOrderByFechaDesc();
}