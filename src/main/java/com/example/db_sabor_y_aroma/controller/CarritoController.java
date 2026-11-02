package com.saboraroma.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saboraroma.model.Carrito;
import com.saboraroma.service.CarritoService;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Obtener carrito por usuario_id (vía param o path variable)
    @GetMapping
    public ResponseEntity<List<Carrito>> obtenerCarritoPorParam(@RequestParam(name = "usuario_id") Integer usuarioId) {
        return ResponseEntity.ok(carritoService.findByUsuario(usuarioId));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Carrito>> obtenerCarritoPorPath(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(carritoService.findByUsuario(usuarioId));
    }

    // Agregar producto al carrito
    @PostMapping
    public ResponseEntity> agregarAlCarrito(@RequestBody Map<String, Integer> body) {
        Integer usuarioId = body.get("usuario_id");
        Integer productoId = body.get("producto_id");
        Integer cantidad = body.getOrDefault("cantidad", 1);

        carritoService.addProducto(usuarioId, productoId, cantidad);
        return ResponseEntity.ok(Map.of("mensaje", "Producto agregado al carrito."));
    }

    // Actualizar cantidad en el carrito
    @PutMapping("/{id}")
    public ResponseEntity> actualizarCantidad(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        
        Integer cantidad = body.get("cantidad");
        Integer usuarioId = body.get("usuario_id");

        return carritoService.updateCantidad(id, usuarioId, cantidad)
                .map(c -> ResponseEntity.ok(Map.of("mensaje", "Cantidad actualizada correctamente.")))
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar ítem del carrito
    @DeleteMapping("/{id}")
    public ResponseEntity> eliminarItem(
            @PathVariable Integer id,
            @RequestParam(name = "usuario_id", required = false) Integer usuarioId) {

        if (carritoService.deleteItem(id, usuarioId)) {
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado del carrito."));
        }
        return ResponseEntity.notFound().build();
    }

    // Vaciar carrito
    @DeleteMapping("/vaciar/{usuarioId}")
    public ResponseEntity> vaciarCarritoPath(@PathVariable Integer usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.ok(Map.of("mensaje", "Carrito vaciado exitosamente."));
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity> vaciarCarritoParam(@RequestParam(name = "usuario_id") Integer usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.ok(Map.of("mensaje", "Carrito vaciado exitosamente."));
    }
}