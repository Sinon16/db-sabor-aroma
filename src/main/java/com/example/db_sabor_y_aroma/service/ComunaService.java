package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.*;
import com.example.db_sabor_y_aroma.repository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ComunaService {

    private final ComunaRepository comunaRepository;

    public ComunaService(ComunaRepository comunaRepository) {
        this.comunaRepository = comunaRepository;
    }

    public List<Comuna> findAll() {
        return comunaRepository.findAllByOrderByNombreComunaAsc();
    }

    public List<Comuna> findByRegion(String region) {
        if (region != null && !region.trim().isEmpty()) {
            return comunaRepository.findByRegionNombreOrId(region);
        }
        return comunaRepository.findAllByOrderByNombreComunaAsc();
    }

    public Optional<Comuna> findById(Integer id) {
        return comunaRepository.findById(id);
    }
}