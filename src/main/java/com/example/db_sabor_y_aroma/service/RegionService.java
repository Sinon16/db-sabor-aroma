package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.*;
import com.example.db_sabor_y_aroma.repository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> findAll() {
        return regionRepository.findAllByOrderByNombreRegionAsc();
    }

    public Optional<Region> findById(Integer id) {
        return regionRepository.findById(id);
    }
}