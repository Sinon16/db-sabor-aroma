package com.saboraroma.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saboraroma.model.Comuna;
import com.saboraroma.repository.ComunaRepository;

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