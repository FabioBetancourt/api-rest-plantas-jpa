package com.fabio.plantas.observability;

import com.fabio.plantas.repository.PlantaRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("plantas")
public class PlantasHealthIndicator implements HealthIndicator {

    private final PlantaRepository plantaRepository;

    public PlantasHealthIndicator(PlantaRepository plantaRepository) {
        this.plantaRepository = plantaRepository;
    }

    @Override
    public Health health() {
        try {
            long cantidad = plantaRepository.count();

            return Health.up()
                    .withDetail("baseDeDatos", "disponible")
                    .withDetail("plantasRegistradas", cantidad)
                    .build();

        } catch (Exception ex) {
            return Health.down()
                    .withDetail("baseDeDatos", "no disponible")
                    .withException(ex)
                    .build();
        }
    }
}
