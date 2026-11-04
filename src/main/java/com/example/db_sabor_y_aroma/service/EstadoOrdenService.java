package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.EstadoOrden;
import com.example.db_sabor_y_aroma.repository.EstadoOrdenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoOrdenService {

    private final EstadoOrdenRepository estadoOrdenRepository;

    EstadoOrdenService(EstadoOrdenRepository estadoOrdenRepository) {
        this.estadoOrdenRepository = estadoOrdenRepository;
    }

    public List<EstadoOrden> obtenerTodos() {
        return estadoOrdenRepository.findAll();
    }

    public Optional<EstadoOrden> obtenerPorId(Long id) {
        return estadoOrdenRepository.findById(id);
    }

    public EstadoOrden guardar(EstadoOrden estadoOrden) {
        return estadoOrdenRepository.save(estadoOrden);
    }

    public void eliminar(Long id) {
        estadoOrdenRepository.deleteById(id);
    }
}