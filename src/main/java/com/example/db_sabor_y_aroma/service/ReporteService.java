package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.Reporte;
import com.example.db_sabor_y_aroma.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> obtenerTodos() {
        return reporteRepository.findAll();
    }

    public Optional<Reporte> obtenerPorId(Integer id) {
        return reporteRepository.findById(id);
    }

    public Reporte guardar(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public void eliminar(Integer id) {
        reporteRepository.deleteById(id);
    }
}