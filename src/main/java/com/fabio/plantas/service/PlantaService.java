package com.fabio.plantas.service;

import com.fabio.plantas.dto.PlantaRequest;
import com.fabio.plantas.exception.RecursoNoEncontradoException;
import com.fabio.plantas.model.Categoria;
import com.fabio.plantas.model.Planta;
import com.fabio.plantas.repository.CategoriaRepository;
import com.fabio.plantas.repository.PlantaRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantaService {

    private static final Logger log = LoggerFactory.getLogger(PlantaService.class);

    private final PlantaRepository plantaRepository;
    private final CategoriaRepository categoriaRepository;
    private final Counter plantasCreadasCounter;

    public PlantaService(
            PlantaRepository plantaRepository,
            CategoriaRepository categoriaRepository,
            MeterRegistry meterRegistry) {

        this.plantaRepository = plantaRepository;
        this.categoriaRepository = categoriaRepository;

        this.plantasCreadasCounter = Counter.builder("plantas.creadas")
                .description("Cantidad de plantas creadas")
                .register(meterRegistry);
    }

    public List<Planta> obtenerTodas() {
        log.info("Consulta de todas las plantas");
        return plantaRepository.findAll();
    }

    public Planta buscarPorId(Long id) {
        return plantaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró la planta con id {}", id);
                    return new RecursoNoEncontradoException(
                            "Planta no encontrada con id " + id);
                });
    }

    public Planta crear(PlantaRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría no encontrada con id " + request.categoriaId()));

        Planta guardada = plantaRepository.save(
                new Planta(
                        request.nombre(),
                        request.ubicacion(),
                        request.necesitaSolDirecto(),
                        categoria
                )
        );

        plantasCreadasCounter.increment();
        log.info("Planta creada con id {} y nombre {}", guardada.getId(), guardada.getNombre());

        return guardada;
    }

    public Planta actualizar(Long id, PlantaRequest request) {
        Planta planta = buscarPorId(id);

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría no encontrada con id " + request.categoriaId()));

        planta.setNombre(request.nombre());
        planta.setUbicacion(request.ubicacion());
        planta.setNecesitaSolDirecto(request.necesitaSolDirecto());
        planta.setCategoria(categoria);

        Planta actualizada = plantaRepository.save(planta);
        log.info("Planta actualizada con id {}", id);

        return actualizada;
    }

    public void eliminar(Long id) {
        plantaRepository.delete(buscarPorId(id));
        log.info("Planta eliminada con id {}", id);
    }

    public List<Planta> buscarPorNombre(String nombre) {
        return plantaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Planta> buscarPorCategoria(String categoria) {
        return plantaRepository.findByCategoriaNombreIgnoreCase(categoria);
    }
}
