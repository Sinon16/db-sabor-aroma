package com.example.db_sabor_y_aroma.controller;

import com.example.db_sabor_y_aroma.model.EstadoOrden;
import com.example.db_sabor_y_aroma.model.Orden;
import com.example.db_sabor_y_aroma.service.EstadoOrdenService;
import com.example.db_sabor_y_aroma.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "*")
@Tag(name = "Orden", description = "Gestión de órdenes de compra y sus estados")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private EstadoOrdenService estadoOrdenService;

    // --- ENDPOINTS DE ORDEN ---

    @GetMapping
    @Operation(summary = "Listar todas las órdenes")
    public List<Orden> listarOrdenes() {
        return ordenService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una orden por su ID")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable Long id) {
        return ordenService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar órdenes de un usuario específico")
    public List<Orden> listarPorUsuario(@PathVariable Long usuarioId) {
        return ordenService.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva orden")
    public Orden crearOrden(@RequestBody Orden orden) {
        return ordenService.guardar(orden);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una orden existente")
    public ResponseEntity<Orden> actualizarOrden(@PathVariable Long id, @RequestBody Orden orden) {
        return ordenService.obtenerPorId(id)
                .map(existente -> {
                    orden.setId(Math.toIntExact(id));
                    return ResponseEntity.ok(ordenService.guardar(orden));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una orden por ID")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        if (ordenService.obtenerPorId(id).isPresent()) {
            ordenService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- ENDPOINTS DE ESTADO ORDEN (Integrados en el mismo Controller) ---

    @GetMapping("/estados")
    @Operation(summary = "Listar todos los estados de orden disponibles")
    public List<EstadoOrden> listarEstadosOrden() {
        return estadoOrdenService.obtenerTodos();
    }

    @PostMapping("/estados")
    @Operation(summary = "Crear un nuevo estado de orden")
    public EstadoOrden crearEstadoOrden(@RequestBody EstadoOrden estadoOrden) {
        return estadoOrdenService.guardar(estadoOrden);
    }
}