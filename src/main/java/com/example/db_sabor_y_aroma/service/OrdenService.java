
package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.Orden;
import com.example.db_sabor_y_aroma.model.Reporte;
import com.example.db_sabor_y_aroma.repository.OrdenRepository;
import com.example.db_sabor_y_aroma.repository.ReporteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ReporteRepository reporteRepository;

    public OrdenService(
            OrdenRepository ordenRepository,
            ReporteRepository reporteRepository) {
        this.ordenRepository = ordenRepository;
        this.reporteRepository = reporteRepository;
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
        boolean nueva = orden.getId() == null;

        Orden guardada = ordenRepository.save(orden);

        Reporte reporte = new Reporte();
        reporte.setTipoReporte("Orden");
        reporte.setEspecialidad(nueva ? "Nuevo" : "Editar");

        if (nueva) {
            reporte.setDescripcion(
                "Se registró la orden #" + guardada.getId()
                + " por un total de $" + guardada.getTotal()
            );
        } else {
            String estado = guardada.getEstadoOrden() != null
                ? guardada.getEstadoOrden().getNombreEstado()
                : "Sin estado";

            reporte.setDescripcion(
                "Se actualizó la orden #" + guardada.getId()
                + " al estado " + estado
            );
        }

        reporte.setFecha(LocalDateTime.now());
        reporteRepository.save(reporte);

        return guardada;
    }

    public void eliminar(Long id) {
        if (ordenRepository.existsById(id)) {
            ordenRepository.deleteById(id);
            ordenRepository.flush();

            Reporte reporte = new Reporte();
            reporte.setTipoReporte("Orden");
            reporte.setEspecialidad("Eliminar");
            reporte.setDescripcion(
                "Se eliminó la orden #" + id
            );
            reporte.setFecha(LocalDateTime.now());

            reporteRepository.save(reporte);
        }
    }
}
