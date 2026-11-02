package com.saboraroma.repository;

import com.saboraroma.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findAllByOrderByIdDesc();

    @Query("SELECT p FROM Producto p WHERE p.categoria.nombreCategoria = :categoria OR CAST(p.categoria.idCategoria AS string) = :categoria")
    List<Producto> findByCategoriaNombreOrId(@Param("categoria") String categoria);
}