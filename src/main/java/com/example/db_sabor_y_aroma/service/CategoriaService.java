package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.*;
import com.example.db_sabor_y_aroma.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ReporteRepository reporteRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ReporteRepository reporteRepository) {
        this.categoriaRepository = categoriaRepository;
        this.reporteRepository = reporteRepository;
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAllByIdCategoriaNotNullOrderByIdCategoriaAsc();
    }

    public Optional<Categoria> findById(Integer id) {
        return categoriaRepository.findById(id);
    }

    public Categoria create(Categoria categoria) {
        categoria.setIdCategoria(null);
        Categoria creada = categoriaRepository.save(categoria);

        // Registro de historial / reporte
        Reporte reporte = new Reporte();
        reporte.setTipoReporte("Categoría");
        reporte.setEspecialidad("Nuevo");
        reporte.setDescripcion("Se creó la categoría: " + creada.getNombreCategoria());
        reporte.setFecha(LocalDateTime.now());
        reporteRepository.save(reporte);

        return creada;
    }

    public Optional<Categoria> update(Integer id, Categoria categoria) {
        return categoriaRepository.findById(id).map(existing -> {
            existing.setNombreCategoria(categoria.getNombreCategoria());
            Categoria actualizada = categoriaRepository.save(existing);

            // Registro de historial / reporte
            Reporte reporte = new Reporte();
            reporte.setTipoReporte("Categoría");
            reporte.setEspecialidad("Editar");
            reporte.setDescripcion("Se editó la categoría ID " + id + " a: " + actualizada.getNombreCategoria());
            reporte.setFecha(LocalDateTime.now());
            reporteRepository.save(reporte);

            return actualizada;
        });
    }

    public boolean delete(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            return false;
        }
        categoriaRepository.deleteById(id);

        // Registro de historial / reporte
        Reporte reporte = new Reporte();
        reporte.setTipoReporte("Categoría");
        reporte.setEspecialidad("Eliminar");
        reporte.setDescripcion("Se eliminó la categoría con ID: " + id);
        reporte.setFecha(LocalDateTime.now());
        reporteRepository.save(reporte);

        return true;
    }

    public boolean existsByName(String nombreCategoria) {
        return categoriaRepository.findAll().stream()
                .anyMatch(c -> c.getNombreCategoria().equalsIgnoreCase(nombreCategoria));
    }
}