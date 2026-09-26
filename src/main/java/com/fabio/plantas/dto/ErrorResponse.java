package com.fabio.plantas.dto;
import java.time.LocalDateTime;
public record ErrorResponse(
    LocalDateTime fecha,
    int status,
    String error,
    String mensaje,
    String ruta
) {}
