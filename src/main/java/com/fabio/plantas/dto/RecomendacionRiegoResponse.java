package com.fabio.plantas.dto;
public record RecomendacionRiegoResponse(
    Long plantaId,
    String planta,
    double temperatura,
    double humedadRelativa,
    String recomendacion
) {}
