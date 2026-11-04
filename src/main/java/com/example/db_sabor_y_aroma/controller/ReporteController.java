package com.example.db_sabor_y_aroma.controller;

import com.example.db_sabor_y_aroma.model.EstadoReporte;
import com.example.db_sabor_y_aroma.model.Reporte;
import com.example.db_sabor_y_aroma.service.EstadoReporteService;
import com.example.db_sabor_y_aroma.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
@Tag(name = "Reporte", description = "Auditoría de reportes y estados de reporte")
public class ReporteController {

    private final ReporteService reporteService;

    private final EstadoReporteService estadoReporteService;

    ReporteController(ReporteService reporteService, EstadoReporteService estadoReporteService) {
        this.reporteService = reporteService;
        this.estadoReporteService = estadoReporteService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los reportes de auditoría")
    public List<Reporte> listarReportes() {
        return reporteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un reporte por ID")
    public ResponseEntity<Reporte> obtenerPorId(@PathVariable Integer id) {
        return reporteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo reporte")
    public Reporte crearReporte(@RequestBody Reporte reporte) {
        return reporteService.guardar(reporte);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte por ID")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Integer id) {
        if (reporteService.obtenerPorId(id).isPresent()) {
            reporteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- ENDPOINTS DE ESTADO REPORTE ---

    @GetMapping("/estados")
    @Operation(summary = "Listar todos los estados de reporte (Nuevo, Editar, Eliminar)")
    public List<EstadoReporte> listarEstadosReporte() {
        return estadoReporteService.obtenerTodos();
    }

    @PostMapping("/estados")
    @Operation(summary = "Crear un nuevo estado de reporte")
    public EstadoReporte crearEstadoReporte(@RequestBody EstadoReporte estadoReporte) {
        return estadoReporteService.guardar(estadoReporte);
    }
}