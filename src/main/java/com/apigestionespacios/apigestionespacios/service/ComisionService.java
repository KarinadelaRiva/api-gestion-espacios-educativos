package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Carrera;
import com.apigestionespacios.apigestionespacios.entities.Comision;
import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.exceptions.EntityValidationException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.ComisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComisionService {

    private final ComisionRepository comisionRepository;
    private final UsuarioService usuarioService;
    private final CarreraService carreraService;

    @Autowired
    public ComisionService(ComisionRepository comisionRepository, UsuarioService usuarioService, CarreraService carreraService) {
        this.comisionRepository = comisionRepository;
        this.usuarioService = usuarioService;
        this.carreraService = carreraService;
    }

    public List<Comision> obtenerTodas() {
        return comisionRepository.findAll();
    }

    public Comision obtenerPorId(Long id) {
        return comisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comisión no encontrada con ID: " + id));
    }

    public Comision crearComision(Comision comision) {
        validarProfesor(comision.getProfesor().getId());
        validarCarrera(comision.getCarrera().getId());
        validarAsignatura(comision.getCarrera().getId(), comision.getAsignatura().getId());
        validarCantidadAlumnos(comision.getCantidadAlumnos());
        return comisionRepository.save(comision);
    }

    public Comision actualizarComision(Long id, Comision nueva) {
        Comision existente = obtenerPorId(id);
        validarProfesor(nueva.getProfesor().getId());
        validarCarrera(nueva.getCarrera().getId());
        validarAsignatura(nueva.getCarrera().getId(), nueva.getAsignatura().getId());
        validarCantidadAlumnos(nueva.getCantidadAlumnos());

        existente.setCantidadAlumnos(nueva.getCantidadAlumnos());
        existente.setAsignatura(nueva.getAsignatura());
        existente.setProfesor(nueva.getProfesor());
        existente.setCarrera(nueva.getCarrera());

        return comisionRepository.save(existente);
    }


    public void eliminarComision(Long id) {
        if (!comisionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comisión no encontrada");
        }
        comisionRepository.deleteById(id);
    }

    private void validarProfesor(Long idUsuarioProfesor) {
        Usuario usuario = usuarioService.obtenerPorId(idUsuarioProfesor);

        if (!"PROFESOR".equalsIgnoreCase(usuario.getRol().getNombre())) {
            throw new EntityValidationException("El usuario con ID " + idUsuarioProfesor + " no tiene el rol de profesor");
        }
    }

    private void validarCarrera(Long idCarrera) {
        carreraService.obtenerPorId(idCarrera); // Lanza excepción si no existe
    }

    private void validarAsignatura(Long idCarrera, Long idAsignatura) {
        Carrera carrera = carreraService.obtenerPorId(idCarrera);
        boolean asignaturaPertenece = carrera.getAsignaturas().stream()
                .anyMatch(asig -> asig.getId().equals(idAsignatura));

        if (!asignaturaPertenece) {
            throw new EntityValidationException("La asignatura con ID " + idAsignatura + " no pertenece a la carrera con ID " + idCarrera);
        }
    }

    private void validarCantidadAlumnos(Integer cantidad) {
        if (cantidad == null || cantidad < 1) {
            throw new EntityValidationException("La cantidad de alumnos debe ser mayor a cero.");
        }

        if (cantidad > 200) {
            throw new EntityValidationException("La cantidad de alumnos no puede exceder los 200.");
        }
    }
}
