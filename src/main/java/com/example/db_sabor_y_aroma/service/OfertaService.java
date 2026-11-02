package com.saboraroma.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saboraroma.model.Oferta;
import com.saboraroma.repository.OfertaRepository;

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