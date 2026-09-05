package com.fabio.plantas.repository;

import com.fabio.plantas.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantaRepository extends JpaRepository<Planta, Long> {
    List<Planta> findByTipoIgnoreCase(String tipo);
    List<Planta> findByNombreContainingIgnoreCase(String nombre);
}
