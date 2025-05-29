package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.repository.AsignaturaRepository;

import java.util.List;

public class AsignaturaService {
    private final AsignaturaRepository asignaturaRepository;

    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    public List<Asignatura> obtenerTodas() {
        return asignaturaRepository.findAll();
    }

    public Asignatura obtenerPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id));
    }

    public Asignatura obtenerPorCodigo(Integer codigo) {
        return asignaturaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con código: " + codigo));
    }

    public Asignatura guardar(Asignatura asignatura) {
        if (asignaturaRepository.existsByCodigo(asignatura.getCodigo())) {
            throw new RuntimeException("Ya existe una asignatura con ese código");
        }
        return asignaturaRepository.save(asignatura);
    }

    public Asignatura actualizar(Long id, Asignatura nueva) {
        Asignatura existente = obtenerPorId(id);
        existente.setNombre(nueva.getNombre());
        existente.setCodigo(nueva.getCodigo());
        existente.setRequiereLaboratorio(nueva.getRequiereLaboratorio());
        // NOTA: No modificamos comisiones acá para evitar problemas de sincronización
        return asignaturaRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new RuntimeException("Asignatura no encontrada");
        }
        asignaturaRepository.deleteById(id);
    }
}
