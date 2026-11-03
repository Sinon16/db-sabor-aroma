package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.EstadoReporte;
import com.example.db_sabor_y_aroma.repository.EstadoReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoReporteService {

    @Autowired
    private EstadoReporteRepository estadoReporteRepository;

    public List<EstadoReporte> obtenerTodos() {
        return estadoReporteRepository.findAll();
    }

    public Optional<EstadoReporte> obtenerPorId(Long id) {
        return estadoReporteRepository.findById(id);
    }

    public EstadoReporte guardar(EstadoReporte estadoReporte) {
        return estadoReporteRepository.save(estadoReporte);
    }

    public void eliminar(Long id) {
        estadoReporteRepository.deleteById(id);
    }
}