package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Carrera;
import com.apigestionespacios.apigestionespacios.entities.Comision;
import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.repository.ComisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComisionService {

    private final ComisionRepository comisionRepository;
    private final UsuarioService usuarioService;
//    private final CarreraService carreraService;

    @Autowired
    public ComisionService(ComisionRepository comisionRepository, UsuarioService usuarioService) {
        this.comisionRepository = comisionRepository;
        this.usuarioService = usuarioService;
    }

    public List<Comision> obtenerTodas() {
        return comisionRepository.findAll();
    }

    public Comision obtenerPorId(Long id) {
        return comisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comisión no encontrada con ID: " + id));
    }

    public Comision guardar(Comision comision) {
//        validarProfesor(comision.getProfesor().getId());
//        validarCarrera(comision.getCarrera().getId());
//        validarAsignatura(comision.getCarrera().getId(), comision.getAsignatura().getId());
        return comisionRepository.save(comision);
    }

    public Comision actualizar(Long id, Comision nueva) {
        Comision existente = obtenerPorId(id);
//        validarProfesor(nueva.getProfesor().getId());
//        validarCarrera(nueva.getCarrera().getId());
//        validarAsignatura(nueva.getCarrera().getId(), nueva.getAsignatura().getId());

        existente.setCantidadAlumnos(nueva.getCantidadAlumnos());
        existente.setAsignatura(nueva.getAsignatura());
        existente.setProfesor(nueva.getProfesor());
        existente.setCarrera(nueva.getCarrera());

        return comisionRepository.save(existente);
    }


    public void eliminar(Long id) {
        if (!comisionRepository.existsById(id)) {
            throw new RuntimeException("Comisión no encontrada");
        }
        comisionRepository.deleteById(id);
    }

//    private void validarProfesor(Long idUsuarioProfesor) {
//        Usuario usuario = usuarioService.obtenerPorId(idUsuarioProfesor);
//        if (usuario == null) {
//            throw new RuntimeException("El usuario con ID " + idUsuarioProfesor + " no existe");
//        }
//
//        boolean esProfesor = usuario.getRol().getNombre().equalsIgnoreCase("PROFESOR");
//
//        if (!esProfesor) {
//            throw new RuntimeException("El usuario con ID " + idUsuarioProfesor + " no tiene el rol de profesor");
//        }
//    }

//    private void validarCarrera(Long idCarrera) {
//        carreraService.obtenerPorId(idCarrera); // Lanza excepción si no existe
//    }

//    private void validarAsignatura(Long idCarrera, Long idAsignatura) {
//        Carrera carrera = carreraService.obtenerPorId(idCarrera);
//        boolean asignaturaPertenece = carrera.getAsignaturas().stream()
//                .anyMatch(asig -> asig.getId().equals(idAsignatura));
//
//        if (!asignaturaPertenece) {
//            throw new RuntimeException("La asignatura con ID " + idAsignatura + " no pertenece a la carrera con ID " + idCarrera);
//        }
//    }
}
