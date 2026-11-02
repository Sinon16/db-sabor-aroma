package com.saboraroma.controller;

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

import com.saboraroma.model.Producto;
import com.saboraroma.service.ProductoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Catálogo público
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    // Listado panel admin (orden descendente por ID)
    @GetMapping("/admin/productos")
    public ResponseEntity<List<Producto>> obtenerProductosAdmin() {
        return ResponseEntity.ok(productoService.findAllAdmin());
    }

    // Detalle de producto
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Filtrar por categoría
    @GetMapping("/productos/categoria/{categoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.findByCategoria(categoria));
    }

    // Crear producto
    @PostMapping("/productos")
    public ResponseEntity> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.create(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "mensaje", "Producto creado con éxito",
            "id", nuevo.getId()
        ));
    }

    // Actualizar producto
    @PutMapping("/productos/{id}")
    public ResponseEntity> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        return productoService.update(id, producto)
                .map(p -> ResponseEntity.ok(Map.of("mensaje", "Producto actualizado correctamente")))
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity> eliminarProducto(@PathVariable Integer id) {
        if (productoService.delete(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente"));
        }
        return ResponseEntity.notFound().build();
    }
}
