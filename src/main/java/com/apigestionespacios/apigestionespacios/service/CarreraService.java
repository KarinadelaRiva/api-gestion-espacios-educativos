package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.dtos.AsignaturaResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.CarreraCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.CarreraResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.CarreraUpdateDTO;
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

    @Autowired
    public CarreraService(CarreraRepository carreraRepository, AsignaturaService asignaturaService) {
        this.carreraRepository = carreraRepository;
        this.asignaturaService = asignaturaService;
    }

    public Carrera carreraCreateDTOtoCarrera(CarreraCreateDTO carrera) {
        return Carrera.builder()
                .nombre(carrera.getNombre())
                .build();
    }

    public CarreraResponseDTO carreraToCarreraResponseDTO(Carrera carrera) {
        return CarreraResponseDTO.builder()
                .id(carrera.getId())
                .nombre(carrera.getNombre())
                .asignaturas(asignaturaService.obtenerAsignaturasPorCarreraId(carrera.getId()).stream()
                        .map(asignatura -> "Código: " + asignatura.getCodigo() + " - Nombre: " + asignatura.getNombre())
                        .toList())
                .build();
    }

    public List<CarreraResponseDTO> obtenerTodas() {
        return carreraRepository.findAll().stream()
                .map(this::carreraToCarreraResponseDTO)
                .toList();
    }

    public CarreraResponseDTO obtenerDTOPorId(Long id) {
        return carreraRepository.findById(id)
                .map(this::carreraToCarreraResponseDTO)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + id));
    }

    public Carrera obtenerPorId(Long id) {
        return carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + id));
    }

    public CarreraResponseDTO obtenerPorNombre(String nombre) {
        return carreraRepository.findByNombre(nombre)
                .map(this::carreraToCarreraResponseDTO)
                .orElseThrow(() -> new RuntimeException("Carrera no econtrada con nombre: " + nombre));
    }

    public List<AsignaturaResponseDTO> obtenerAsignaturasDeCarrera(Long carreraId) {
        return asignaturaService.obtenerAsignaturasPorCarreraId(carreraId);
    }

    public Carrera crearCarrera(CarreraCreateDTO carreraDTO) {
        Carrera carrera = carreraCreateDTOtoCarrera(carreraDTO);
        if (carreraRepository.existsByNombre(carrera.getNombre())) {
            throw new ResourceConflictException("Ya existe una carrera con ese nombre.");
        }
        return carreraRepository.save(carrera);
    }

    public Carrera actualizarCarrera(CarreraUpdateDTO nueva) {
        //Carrera existente = obtenerPorId(id);
        Carrera existente = obtenerPorId(nueva.getId());

        if(carreraRepository.existsByNombre(nueva.getNombre())) {
            throw new ResourceConflictException("Ya existe una carrera con ese nombre.");
        }

        existente.setNombre(nueva.getNombre());
        return carreraRepository.save(existente);
    }

    public void eliminarCarrera(Long id) {
        if (!carreraRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe una carrera con ID: " + id);
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
