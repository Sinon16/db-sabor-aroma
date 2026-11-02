package com.saboraroma.repository;

import com.saboraroma.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    List<Carrito> findByUsuarioId(Integer usuarioId);

    Optional<Carrito> findByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);

    void deleteByUsuarioId(Integer usuarioId);
}