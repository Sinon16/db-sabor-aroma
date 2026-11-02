package com.example.db_sabor_y_aroma.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.db_sabor_y_aroma.model.Producto;
import com.example.db_sabor_y_aroma.service.ProductoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/admin/productos")
    public ResponseEntity<List<Producto>> obtenerProductosAdmin() {
        return ResponseEntity.ok(productoService.findAllAdmin());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/productos/categoria/{categoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.findByCategoria(categoria));
    }

    @PostMapping("/productos")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.create(producto);
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Producto creado con éxito");
        res.put("id", nuevo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        return productoService.update(id, producto)
                .map(p -> {
                    Map<String, String> res = new HashMap<>();
                    res.put("mensaje", "Producto actualizado correctamente");
                    return ResponseEntity.ok(res);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {
        if (productoService.delete(id)) {
            Map<String, String> res = new HashMap<>();
            res.put("mensaje", "Producto eliminado correctamente");
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
}