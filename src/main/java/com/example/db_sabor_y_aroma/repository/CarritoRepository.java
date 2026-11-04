package com.example.db_sabor_y_aroma.repository;

import com.example.db_sabor_y_aroma.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    List<Carrito> findByUsuarioId(Integer usuarioId);

    Optional<Carrito> findByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);

    void deleteByUsuarioId(Integer usuarioId);
}