package com.fabio.plantas.service;

import com.fabio.plantas.dto.CategoriaRequest;
import com.fabio.plantas.exception.RecursoNoEncontradoException;
import com.fabio.plantas.model.Categoria;
import com.fabio.plantas.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría no encontrada con id " + id));
    }

    public Categoria crear(CategoriaRequest request) {
        return categoriaRepository.save(
                new Categoria(request.nombre(), request.descripcion())
        );
    }

    public Categoria actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = buscarPorId(id);
        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        categoriaRepository.delete(buscarPorId(id));
    }
}
