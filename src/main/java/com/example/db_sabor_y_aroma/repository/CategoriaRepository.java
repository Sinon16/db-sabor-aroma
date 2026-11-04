package com.example.db_sabor_y_aroma.repository;

import com.example.db_sabor_y_aroma.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findAllByIdCategoriaNotNullOrderByIdCategoriaAsc();
}