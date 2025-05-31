package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Carrera;
import com.apigestionespacios.apigestionespacios.exceptions.EntityValidationException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraService {
    private final CarreraRepository carreraRepository;

    @Autowired
    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
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

    public Carrera crearCarrera(Carrera carrera) {
        if (carreraRepository.existsByNombre(carrera.getNombre())) {
            throw new EntityValidationException("Ya existe una carrera con ese nombre.");
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
}
