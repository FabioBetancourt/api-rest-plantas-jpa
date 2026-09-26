package com.fabio.plantas.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlantaRequest(
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    @NotBlank(message = "La ubicación es obligatoria") String ubicacion,
    boolean necesitaSolDirecto,
    @NotNull(message = "La categoría es obligatoria") Long categoriaId
) {}
