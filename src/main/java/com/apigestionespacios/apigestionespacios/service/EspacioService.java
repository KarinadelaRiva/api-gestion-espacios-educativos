package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceConflictException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.EspacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspacioService {
    private final EspacioRepository espacioRepository;

    @Autowired
    public EspacioService(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    public List<Espacio> obtenerTodos() {
        return espacioRepository.findAll();
    }

    public Espacio obtenerPorId(Long id) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado con ID: " + id));
    }

    public Espacio obtenerPorNombre(String nombre) {
        return espacioRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado con nombre: " + nombre));
    }

    public List<Reserva> obtenerReservasPorEspacio(Long espacioId) {
        return obtenerPorId(espacioId).getReservas();
    }

    public List<Espacio> obtenerPorCapacidadMinima(Integer capacidad) {
        return espacioRepository.findByCapacidadGreaterThanEqual(capacidad);
    }

    public List<Espacio> obtenerConProyector() {
        return espacioRepository.findByTieneProyectorTrue();
    }

    public List<Espacio> obtenerConTV() {
        return espacioRepository.findByTieneTVTrue();
    }

    public Espacio guardar(Espacio espacio) {
        if (espacioRepository.existsByNombre(espacio.getNombre())) {
            throw new ResourceConflictException("Ya existe un espacio con ese nombre");
        }
        return espacioRepository.save(espacio);
    }

    public Espacio actualizar(Long id, Espacio nuevoEspacio) {
        Espacio existente = obtenerPorId(id);
        existente.setNombre(nuevoEspacio.getNombre());
        existente.setCapacidad(nuevoEspacio.getCapacidad());
        existente.setTieneProyector(nuevoEspacio.getTieneProyector());
        existente.setTieneTV(nuevoEspacio.getTieneTV());
        return espacioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if(!espacioRepository.existsById(id)){
            throw new ResourceNotFoundException("Espacio no encontrado");
        }
        espacioRepository.deleteById(id);
    }

}
