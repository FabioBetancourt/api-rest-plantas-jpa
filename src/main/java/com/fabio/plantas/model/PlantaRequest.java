package com.fabio.plantas.model;

import jakarta.validation.constraints.NotBlank;

public record PlantaRequest(
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    @NotBlank(message = "El tipo es obligatorio") String tipo,
    @NotBlank(message = "La ubicación es obligatoria") String ubicacion,
    boolean necesitaSolDirecto
) {}
