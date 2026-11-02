package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.*;
import com.example.db_sabor_y_aroma.repository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    public OfertaService(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    public List<Oferta> findAll() {
        return ofertaRepository.findAll();
    }

    public Optional<Oferta> findById(Integer id) {
        return ofertaRepository.findById(id);
    }

    public Oferta create(Oferta oferta) {
        oferta.setIdOferta(null);
        return ofertaRepository.save(oferta);
    }
}