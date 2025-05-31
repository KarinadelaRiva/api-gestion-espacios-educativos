package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Carrera;
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
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + id));
    }

    public Carrera obtenerPorNombre(String nombre) {
        return carreraRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Carrera no econtrada con nombre: " + nombre));
    }

    public Carrera guardar(Carrera carrera) {
        if (carreraRepository.existsByNombre(carrera.getNombre())) {
            throw new RuntimeException("Ya existe una carrera con ese nombre.");
        }
        return carreraRepository.save(carrera);
    }

    public Carrera actualizar(Long id, Carrera nueva) {
        Carrera existente = obtenerPorId(id);
        existente.setNombre(nueva.getNombre());
        return carreraRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!carreraRepository.existsById(id)) {
            throw new RuntimeException("No, se pudo eliminar. Carrera no encontrada.");
        }
        carreraRepository.deleteById(id);
    }
}
