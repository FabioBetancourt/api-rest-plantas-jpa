package com.fabio.plantas.controller;

import com.fabio.plantas.dto.PlantaRequest;
import com.fabio.plantas.model.Planta;
import com.fabio.plantas.service.PlantaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantas")
public class PlantaController {

    private final PlantaService plantaService;

    public PlantaController(PlantaService plantaService) {
        this.plantaService = plantaService;
    }

    @GetMapping
    public ResponseEntity<List<Planta>> obtenerTodas() {
        return ResponseEntity.ok(plantaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(plantaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Planta> crear(@Valid @RequestBody PlantaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(plantaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planta> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlantaRequest request) {

        return ResponseEntity.ok(plantaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        plantaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Planta>> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria) {

        if (nombre != null && !nombre.isBlank()) {
            return ResponseEntity.ok(plantaService.buscarPorNombre(nombre));
        }

        if (categoria != null && !categoria.isBlank()) {
            return ResponseEntity.ok(plantaService.buscarPorCategoria(categoria));
        }

        return ResponseEntity.ok(plantaService.obtenerTodas());
    }
}
