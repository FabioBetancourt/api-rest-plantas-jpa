package com.fabio.plantas.service;

import com.fabio.plantas.dto.ClimaResponse;
import com.fabio.plantas.dto.RecomendacionRiegoResponse;
import com.fabio.plantas.exception.ServicioExternoException;
import com.fabio.plantas.model.Planta;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
public class ClimaService {

    private static final Logger log = LoggerFactory.getLogger(ClimaService.class);

    private final RestClient restClient;
    private final PlantaService plantaService;
    private final Counter consultasClimaCounter;

    public ClimaService(
            RestClient openMeteoRestClient,
            PlantaService plantaService,
            MeterRegistry meterRegistry) {

        this.restClient = openMeteoRestClient;
        this.plantaService = plantaService;

        this.consultasClimaCounter = Counter.builder("clima.consultas")
                .description("Cantidad de consultas realizadas a Open-Meteo")
                .register(meterRegistry);
    }

    public ClimaResponse consultarClima(double latitud, double longitud) {
        try {
            consultasClimaCounter.increment();

            Map respuesta = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("latitude", latitud)
                            .queryParam("longitude", longitud)
                            .queryParam("current",
                                    "temperature_2m,relative_humidity_2m,weather_code")
                            .build())
                    .retrieve()
                    .body(Map.class);

            if (respuesta == null || respuesta.get("current") == null) {
                throw new ServicioExternoException(
                        "La API externa no devolvió información climática válida", null);
            }

            Map current = (Map) respuesta.get("current");
            Map units = (Map) respuesta.get("current_units");

            double temperatura = ((Number) current.get("temperature_2m")).doubleValue();
            double humedad = ((Number) current.get("relative_humidity_2m")).doubleValue();
            int codigo = ((Number) current.get("weather_code")).intValue();
            String unidad = units != null
                    ? String.valueOf(units.get("temperature_2m"))
                    : "°C";

            log.info("Consulta climática exitosa para lat {} lon {}", latitud, longitud);

            return new ClimaResponse(temperatura, humedad, codigo, unidad);

        } catch (RestClientException | ClassCastException | NullPointerException ex) {
            log.error("Error al consultar Open-Meteo", ex);
            throw new ServicioExternoException(
                    "No fue posible consultar el servicio externo de clima", ex);
        }
    }

    public RecomendacionRiegoResponse recomendacionRiego(
            Long plantaId, double latitud, double longitud) {

        Planta planta = plantaService.buscarPorId(plantaId);
        ClimaResponse clima = consultarClima(latitud, longitud);

        String recomendacion;

        if (clima.humedadRelativa() >= 75) {
            recomendacion = "La humedad es alta. Revisa el sustrato antes de regar.";
        } else if (clima.temperatura() >= 28 && planta.isNecesitaSolDirecto()) {
            recomendacion = "Hace calor y la planta necesita sol directo. Revisa si requiere riego adicional.";
        } else if (clima.humedadRelativa() <= 45) {
            recomendacion = "La humedad es baja. Revisa el sustrato y considera regar si está seco.";
        } else {
            recomendacion = "Las condiciones son moderadas. Mantén el riego habitual y revisa el sustrato.";
        }

        return new RecomendacionRiegoResponse(
                planta.getId(),
                planta.getNombre(),
                clima.temperatura(),
                clima.humedadRelativa(),
                recomendacion
        );
    }
}
