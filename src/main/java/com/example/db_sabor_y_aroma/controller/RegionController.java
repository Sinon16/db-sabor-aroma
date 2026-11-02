package com.saboraroma.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saboraroma.model.Region;
import com.saboraroma.service.RegionService;

@RestController
@RequestMapping("/api/regiones")
@CrossOrigin(origins = "*")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<List<Region>> obtenerRegiones() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> obtenerRegionPorId(@PathVariable Integer id) {
        return regionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}