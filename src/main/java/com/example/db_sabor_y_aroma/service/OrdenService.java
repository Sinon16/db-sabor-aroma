package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.Orden;
import com.example.db_sabor_y_aroma.repository.OrdenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;

    OrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    public List<Orden> obtenerTodas() {
        return ordenRepository.findAll();
    }

    public Optional<Orden> obtenerPorId(Long id) {
        return ordenRepository.findById(id);
    }

    public List<Orden> obtenerPorUsuario(Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }

    public Orden guardar(Orden orden) {
        return ordenRepository.save(orden);
    }

    public void eliminar(Long id) {
        ordenRepository.deleteById(id);
    }
}