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

    /**
     * Obtiene todas las comisiones.
     *
     * @return Lista de todas las comisiones
     */
    public List<Comision> obtenerTodas() {
        return comisionRepository.findAll();
    }

    /**
     * Obtiene una comisión por su ID.
     * Si la comisión no existe, lanza una ResourceNotFoundException.
     *
     * @param id ID de la comisión a buscar
     * @return Comisión encontrada
     */
    public Comision obtenerPorId(Long id) {
        return comisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comisión no encontrada con ID: " + id));
    }

    /**
     * Crea una nueva comisión.
     * Valida que el profesor, carrera y asignatura sean correctos.
     * Valida que la cantidad de alumnos sea mayor a cero y no exceda 200.
     *
     * @param comision Comisión a crear
     * @return Comisión creada
     */
    public Comision crearComision(Comision comision) {
        validarProfesor(comision.getProfesor().getId());
        validarCarrera(comision.getCarrera().getId());
        validarAsignatura(comision.getCarrera().getId(), comision.getAsignatura().getId());
        validarCantidadAlumnos(comision.getCantidadAlumnos());
        return comisionRepository.save(comision);
    }

    /**
     * Actualiza una comisión existente.
     * Valida que el profesor, carrera y asignatura sean correctos.
     * Valida que la cantidad de alumnos sea mayor a cero y no exceda 200.
     *
     * @param id ID de la comisión a actualizar
     * @param nueva Nueva comisión con los datos actualizados
     * @return Comisión actualizada
     */
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

    /**
     * Elimina una comisión por su ID.
     * Si la comisión no existe, lanza una ResourceNotFoundException.
     *
     * @param id ID de la comisión a eliminar
     */
    public void eliminarComision(Long id) {
        if (!comisionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comisión no encontrada");
        }
        comisionRepository.deleteById(id);
    }


    /**
     * Valida que el usuario tenga el rol de profesor.
     * Si no es así, lanza una EntityValidationException.
     *
     * @param idUsuarioProfesor ID del usuario a validar
     */
    private void validarProfesor(Long idUsuarioProfesor) {
        Usuario usuario = usuarioService.obtenerPorId(idUsuarioProfesor);

        if (!"PROFESOR".equalsIgnoreCase(usuario.getRol().getNombre())) {
            throw new EntityValidationException("El usuario con ID " + idUsuarioProfesor + " no tiene el rol de profesor");
        }
    }

    /**
     * Valida que la carrera exista.
     * Si no existe, lanza una excepción ResourceNotFoundException.
     *
     * @param idCarrera ID de la carrera a validar
     */
    private void validarCarrera(Long idCarrera) {
        carreraService.obtenerPorId(idCarrera); // Lanza excepción si no existe
    }

    /**
     * Valida que la asignatura pertenezca a la carrera.
     * Si no pertenece, lanza una excepción EntityValidationException.
     *
     * @param idCarrera ID de la carrera
     * @param idAsignatura ID de la asignatura a validar
     */
    private void validarAsignatura(Long idCarrera, Long idAsignatura) {
        Carrera carrera = carreraService.obtenerPorId(idCarrera);
        boolean asignaturaPertenece = carrera.getAsignaturas().stream()
                .anyMatch(asig -> asig.getId().equals(idAsignatura));

        if (!asignaturaPertenece) {
            throw new EntityValidationException("La asignatura con ID " + idAsignatura + " no pertenece a la carrera con ID " + idCarrera);
        }
    }

    /**
     * Valida que la cantidad de alumnos sea mayor a cero y no exceda 200.
     * Si no cumple con estas condiciones, lanza una EntityValidationException.
     *
     * @param cantidad Cantidad de alumnos a validar
     */
    private void validarCantidadAlumnos(Integer cantidad) {
        if (cantidad == null || cantidad < 1) {
            throw new EntityValidationException("La cantidad de alumnos debe ser mayor a cero.");
        }

        if (cantidad > 200) {
            throw new EntityValidationException("La cantidad de alumnos no puede exceder los 200.");
        }
    }

    /**
     * Obtiene todas las comisiones asociadas a una asignatura específica.
     *
     * @param asignaturaId ID de la asignatura
     * @return Lista de comisiones asociadas a la asignatura
     */
    public List<Comision> obtenerComisionesPorAsignatura(Long asignaturaId) {
        return comisionRepository.findByAsignaturaId(asignaturaId);
    }

    /**
     * Obtiene todas las comisiones asociadas a un profesor específico.
     *
     * @param profesorId ID del profesor
     * @return Lista de comisiones asociadas al profesor
     */
    public List<Comision> obtenerComisionesPorProfesor(Long profesorId) {
        return comisionRepository.findByProfesorId(profesorId);
    }

}
