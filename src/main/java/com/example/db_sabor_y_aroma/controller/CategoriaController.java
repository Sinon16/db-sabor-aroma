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

import com.saboraroma.model.Categoria;
import com.saboraroma.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Integer id) {
        return categoriaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.create(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "mensaje", "Categoría creada con éxito",
            "id", nueva.getIdCategoria()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity> actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return categoriaService.update(id, categoria)
                .map(c -> ResponseEntity.ok(Map.of("mensaje", "Categoría actualizada correctamente")))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity> eliminarCategoria(@PathVariable Integer id) {
        if (categoriaService.delete(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Categoría eliminada correctamente"));
        }
        return ResponseEntity.notFound().build();
    }
}