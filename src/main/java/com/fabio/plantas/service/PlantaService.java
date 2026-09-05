package com.fabio.plantas.service;

import com.fabio.plantas.model.Planta;
import com.fabio.plantas.model.PlantaRequest;
import com.fabio.plantas.repository.PlantaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlantaService {

    private final PlantaRepository plantaRepository;

    public PlantaService(PlantaRepository plantaRepository) {
        this.plantaRepository = plantaRepository;
    }

    public List<Planta> obtenerTodas() {
        return plantaRepository.findAll();
    }

    public Optional<Planta> buscarPorId(Long id) {
        return plantaRepository.findById(id);
    }

    public Planta crear(PlantaRequest request) {
        Planta planta = new Planta(
            request.nombre(),
            request.tipo(),
            request.ubicacion(),
            request.necesitaSolDirecto()
        );
        return plantaRepository.save(planta);
    }

    public Optional<Planta> actualizar(Long id, PlantaRequest request) {
        return plantaRepository.findById(id).map(planta -> {
            planta.setNombre(request.nombre());
            planta.setTipo(request.tipo());
            planta.setUbicacion(request.ubicacion());
            planta.setNecesitaSolDirecto(request.necesitaSolDirecto());
            return plantaRepository.save(planta);
        });
    }

    public boolean eliminar(Long id) {
        if (!plantaRepository.existsById(id)) return false;
        plantaRepository.deleteById(id);
        return true;
    }

    public List<Planta> buscarPorTipo(String tipo) {
        return plantaRepository.findByTipoIgnoreCase(tipo);
    }

    public List<Planta> buscarPorNombre(String nombre) {
        return plantaRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
