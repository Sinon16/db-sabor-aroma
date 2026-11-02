package com.saboraroma.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saboraroma.model.Oferta;
import com.saboraroma.service.OfertaService;

@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(origins = "*")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> obtenerOfertas() {
        return ResponseEntity.ok(ofertaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oferta> obtenerOfertaPorId(@PathVariable Integer id) {
        return ofertaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Oferta> crearOferta(@RequestBody Oferta oferta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ofertaService.create(oferta));
    }
}
