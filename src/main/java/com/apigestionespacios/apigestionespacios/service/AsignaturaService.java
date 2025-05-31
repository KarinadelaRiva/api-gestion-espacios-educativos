package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.exceptions.EntityValidationException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignaturaService {
    private final AsignaturaRepository asignaturaRepository;

    @Autowired
    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    public List<Asignatura> obtenerTodas() {
        return asignaturaRepository.findAll();
    }

    public Asignatura obtenerPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada con ID: " + id));
    }

    public Asignatura obtenerPorCodigo(Integer codigo) {
        return asignaturaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada con código: " + codigo));
    }

    public Asignatura crearAsignatura(Asignatura asignatura) {
        if (asignaturaRepository.existsByCodigo(asignatura.getCodigo())) {
            throw new EntityValidationException("Ya existe una asignatura con ese código");
        }
        return asignaturaRepository.save(asignatura);
    }

    public Asignatura actualizarAsignatura(Long id, Asignatura nueva) {
        Asignatura existente = obtenerPorId(id);
        if (!existente.getCodigo().equals(nueva.getCodigo()) &&
                asignaturaRepository.existsByCodigo(nueva.getCodigo())) {
            throw new EntityValidationException("Ya existe una asignatura con ese código");
        }
        existente.setNombre(nueva.getNombre());
        existente.setRequiereLaboratorio(nueva.getRequiereLaboratorio());
        // NOTA: No modificamos comisiones acá para evitar problemas de sincronización
        return asignaturaRepository.save(existente);
    }

    public void eliminarAsignatura(Long id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asignatura no encontrada");
        }
        asignaturaRepository.deleteById(id);
    }
}
