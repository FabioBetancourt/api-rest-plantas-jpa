package com.fabio.plantas.controller;

import com.fabio.plantas.model.Planta;
import com.fabio.plantas.model.PlantaRequest;
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
        return plantaService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Planta> crear(@Valid @RequestBody PlantaRequest request) {
        Planta creada = plantaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planta> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody PlantaRequest request) {

        return plantaService.actualizar(id, request)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!plantaService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Planta>> buscarPorTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(plantaService.buscarPorTipo(tipo));
    }

    @GetMapping("/buscar-nombre")
    public ResponseEntity<List<Planta>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(plantaService.buscarPorNombre(nombre));
    }
}
