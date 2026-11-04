package com.example.db_sabor_y_aroma.repository;

import com.example.db_sabor_y_aroma.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findAllByOrderByIdDesc();

    @Query("SELECT p FROM Producto p WHERE p.categoria.nombreCategoria = :categoria OR CAST(p.categoria.idCategoria AS string) = :categoria")
    List<Producto> findByCategoriaNombreOrId(@Param("categoria") String categoria);
}