package com.saboraroma.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saboraroma.model.Comuna;
import com.saboraroma.service.ComunaService;

@RestController
@RequestMapping("/api/comunas")
@CrossOrigin(origins = "*")
public class ComunaController {

    private final ComunaService comunaService;

    public ComunaController(ComunaService comunaService) {
        this.comunaService = comunaService;
    }

    @GetMapping
    public ResponseEntity<List<Comuna>> obtenerComunas(@RequestParam(required = false) String region) {
        return ResponseEntity.ok(comunaService.findByRegion(region));
    }
}