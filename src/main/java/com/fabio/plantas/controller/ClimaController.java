package com.fabio.plantas.controller;

import com.fabio.plantas.dto.ClimaResponse;
import com.fabio.plantas.dto.RecomendacionRiegoResponse;
import com.fabio.plantas.service.ClimaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clima")
public class ClimaController {

    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @GetMapping
    public ResponseEntity<ClimaResponse> consultar(
            @RequestParam double lat,
            @RequestParam double lon) {

        return ResponseEntity.ok(climaService.consultarClima(lat, lon));
    }

    @GetMapping("/plantas/{id}/recomendacion-riego")
    public ResponseEntity<RecomendacionRiegoResponse> recomendacionRiego(
            @PathVariable Long id,
            @RequestParam double lat,
            @RequestParam double lon) {

        return ResponseEntity.ok(
                climaService.recomendacionRiego(id, lat, lon)
        );
    }
}
