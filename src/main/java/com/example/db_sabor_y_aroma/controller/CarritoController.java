package com.example.db_sabor_y_aroma.controller;

import com.example.db_sabor_y_aroma.model.*;
import com.example.db_sabor_y_aroma.service.*;

import java.util.HashMap;
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


@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public ResponseEntity<List<Carrito>> obtenerCarritoPorParam(@RequestParam(name = "usuario_id") Integer usuarioId) {
        return ResponseEntity.ok(carritoService.findByUsuario(usuarioId));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Carrito>> obtenerCarritoPorPath(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(carritoService.findByUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> agregarAlCarrito(@RequestBody Map<String, Integer> body) {
        Integer usuarioId = body.get("usuario_id");
        Integer productoId = body.get("producto_id");
        Integer cantidad = body.getOrDefault("cantidad", 1);

        carritoService.addProducto(usuarioId, productoId, cantidad);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Producto agregado al carrito.");
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        
        Integer cantidad = body.get("cantidad");
        Integer usuarioId = body.get("usuario_id");

        return carritoService.updateCantidad(id, usuarioId, cantidad)
                .map(c -> {
                    Map<String, String> res = new HashMap<>();
                    res.put("mensaje", "Cantidad actualizada correctamente.");
                    return ResponseEntity.ok(res);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarItem(
            @PathVariable Integer id,
            @RequestParam(name = "usuario_id", required = false) Integer usuarioId) {

        if (carritoService.deleteItem(id, usuarioId)) {
            Map<String, String> res = new HashMap<>();
            res.put("mensaje", "Producto eliminado del carrito.");
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/vaciar/{usuarioId}")
    public ResponseEntity<?> vaciarCarritoPath(@PathVariable Integer usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        Map<String, String> res = new HashMap<>();
        res.put("mensaje", "Carrito vaciado exitosamente.");
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<?> vaciarCarritoParam(@RequestParam(name = "usuario_id") Integer usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        Map<String, String> res = new HashMap<>();
        res.put("mensaje", "Carrito vaciado exitosamente.");
        return ResponseEntity.ok(res);
    }
}