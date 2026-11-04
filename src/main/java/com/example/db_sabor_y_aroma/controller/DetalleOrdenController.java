package com.example.db_sabor_y_aroma.controller;

import com.example.db_sabor_y_aroma.model.DetalleOrden;
import com.example.db_sabor_y_aroma.service.DetalleOrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-ordenes")
@CrossOrigin(origins = "*")
@Tag(name = "Detalle Orden", description = "Operaciones relacionadas con los detalles de las órdenes")
public class DetalleOrdenController {

    private final DetalleOrdenService detalleOrdenService;

    DetalleOrdenController(DetalleOrdenService detalleOrdenService) {
        this.detalleOrdenService = detalleOrdenService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los detalles de órdenes")
    public List<DetalleOrden> listarTodos() {
        return detalleOrdenService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de orden por ID")
    public ResponseEntity<DetalleOrden> obtenerPorId(@PathVariable Long id) {
        return detalleOrdenService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/orden/{ordenId}")
    @Operation(summary = "Obtener los detalles asociados a una orden específica")
    public List<DetalleOrden> listarPorOrden(@PathVariable Long ordenId) {
        return detalleOrdenService.obtenerPorOrdenId(ordenId);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo detalle de orden")
    public DetalleOrden crear(@RequestBody DetalleOrden detalleOrden) {
        return detalleOrdenService.guardar(detalleOrden);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un detalle de orden existente")
    public ResponseEntity<DetalleOrden> actualizar(@PathVariable Long id, @RequestBody DetalleOrden detalle) {
        return detalleOrdenService.obtenerPorId(id)
                .map(existente -> {
                    detalle.setId(Math.toIntExact(id));
                    return ResponseEntity.ok(detalleOrdenService.guardar(detalle));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un detalle de orden")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (detalleOrdenService.obtenerPorId(id).isPresent()) {
            detalleOrdenService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}