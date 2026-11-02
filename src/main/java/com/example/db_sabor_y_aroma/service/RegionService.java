package com.saboraroma.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saboraroma.model.Region;
import com.saboraroma.repository.RegionRepository;

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