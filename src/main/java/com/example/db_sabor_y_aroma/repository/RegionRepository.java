package com.example.db_sabor_y_aroma.repository;

import com.example.db_sabor_y_aroma.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findAllByOrderByNombreRegionAsc();
}