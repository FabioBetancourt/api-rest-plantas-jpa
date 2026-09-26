package com.fabio.plantas.dto;
public record ClimaResponse(
    double temperatura,
    double humedadRelativa,
    int codigoClima,
    String unidadTemperatura
) {}
