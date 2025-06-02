package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.entities.Carrera;
import com.apigestionespacios.apigestionespacios.exceptions.EntityValidationException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceConflictException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.CarreraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraService {
    private final CarreraRepository carreraRepository;
    private final AsignaturaService asignaturaService;

    public CarreraService(CarreraRepository carreraRepository, AsignaturaService asignaturaService) {
        this.carreraRepository = carreraRepository;
        this.asignaturaService = asignaturaService;
    }

    public List<Carrera> obtenerTodas() {
        return carreraRepository.findAll();
    }

    public Carrera obtenerPorId(Long id) {
        return carreraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada con ID: " + id));
    }

    public Carrera obtenerPorNombre(String nombre) {
        return carreraRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no econtrada con nombre: " + nombre));
    }

    public List<Asignatura> obtenerAsignaturasDeCarrera(Long carreraId) {
        Carrera carrera = obtenerPorId(carreraId);
        return carrera.getAsignaturas();
    }

    public Carrera crearCarrera(Carrera carrera) {
        if (carreraRepository.existsByNombre(carrera.getNombre())) {
            throw new ResourceConflictException("Ya existe una carrera con ese nombre.");
        }
        return carreraRepository.save(carrera);
    }

    public Carrera actualizarCarrera(Long id, Carrera nueva) {
        Carrera existente = obtenerPorId(id);
        existente.setNombre(nueva.getNombre());
        return carreraRepository.save(existente);
    }

    public void eliminarCarrera(Long id) {
        if (!carreraRepository.existsById(id)) {
            throw new ResourceNotFoundException("No, se pudo eliminarCarrera. Carrera no encontrada.");
        }
        carreraRepository.deleteById(id);
    }

    @Transactional
    public void asignarAsignaturaACarrera(Long carreraId, List<Long> asignaturaIds) {
        Carrera carrera = obtenerPorId(carreraId);
        List<Asignatura> asignaturasCarrera = carrera.getAsignaturas();

        asignaturaIds.stream()
                .map(asignaturaService::obtenerPorId)
                .forEach(asignatura -> {
                    if (carrera.getAsignaturas().contains(asignatura)) {
                        throw new EntityValidationException(
                                "La asignatura ID = " + asignatura.getId() +
                                        " nombre = " + asignatura.getNombre() + " ya está asignada a la carrera.");
                    }
                    else {
                        asignaturasCarrera.add(asignatura);
                    }
                });

        carrera.setAsignaturas(asignaturasCarrera);
        carreraRepository.save(carrera);
    }

    @Transactional
    public void eliminarAsignaturaDeCarrera(Long carreraId, List<Long> asignaturaIds) {
        Carrera carrera = obtenerPorId(carreraId);
        List<Asignatura> asignaturasCarrera = carrera.getAsignaturas();

        asignaturaIds.stream()
                .map(asignaturaService::obtenerPorId)
                .forEach(asignatura -> {
                    if (!asignaturasCarrera.contains(asignatura)) {
                        throw new ResourceNotFoundException(
                                "La asignatura ID = " + asignatura.getId() +
                                " nombre = " + asignatura.getNombre() + " no está asignada a la carrera.");
                    }
                    asignaturasCarrera.remove(asignatura);
                });

        carrera.setAsignaturas(asignaturasCarrera);
        carreraRepository.save(carrera);
    }

}
