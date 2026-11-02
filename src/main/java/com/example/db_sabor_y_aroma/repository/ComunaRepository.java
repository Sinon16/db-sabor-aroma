package com.example.db_sabor_y_aroma.repository;

import com.example.db_sabor_y_aroma.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

    List<Comuna> findAllByOrderByNombreComunaAsc();

    @Query("SELECT c FROM Comuna c WHERE c.region.nombreRegion = :region OR CAST(c.region.idRegion AS string) = :region ORDER BY c.nombreComuna ASC")
    List<Comuna> findByRegionNombreOrId(@Param("region") String region);
}