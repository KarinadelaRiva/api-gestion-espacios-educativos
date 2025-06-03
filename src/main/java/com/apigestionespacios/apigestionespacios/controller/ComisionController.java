package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Comision;
import com.apigestionespacios.apigestionespacios.service.ComisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comisiones")
public class ComisionController {

    private final ComisionService comisionService;

    @Autowired
    public ComisionController(ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    /**
     * Obtiene todas las comisiones.
     *
     * @return Lista de todas las comisiones
     */
    @GetMapping
    public ResponseEntity<List<Comision>> obtenerTodas() {
        return new ResponseEntity<>(comisionService.obtenerTodas(), HttpStatus.OK);
    }

    /**
     * Obtiene una comisión por su ID.
     * Si la comisión no existe, lanza una ResourceNotFoundException.
     *
     * @param id ID de la comisión a buscar
     * @return Comisión encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comision> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(comisionService.obtenerPorId(id), HttpStatus.OK);
    }

    /**
     * Crea una nueva comisión.
     * Valida que el profesor, carrera y asignatura sean correctos.
     * Valida que la cantidad de alumnos sea mayor a cero y no exceda 200.
     *
     * @param comision Comisión a crear
     * @return Comisión creada
     */
    @PostMapping
    public ResponseEntity<Comision> crearComision(@RequestBody Comision comision) {
        return new ResponseEntity<>(comisionService.crearComision(comision), HttpStatus.CREATED);
    }

    /**
     * Actualiza una comisión existente.
     * Valida que el ID de la comisión exista.
     *
     * @param id ID de la comisión a actualizar
     * @param comision Datos actualizados de la comisión
     * @return Comisión actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<Comision> actualizarComision(@PathVariable Long id, @RequestBody Comision comision) {
        return new ResponseEntity<>(comisionService.actualizarComision(id, comision), HttpStatus.OK);
    }

     /**
     * Elimina una comisión por su ID.
     * Si la comisión no existe, lanza una ResourceNotFoundException.
     *
     * @param id ID de la comisión a eliminar
     * @return Respuesta vacía con estado NO_CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComision(@PathVariable Long id) {
        comisionService.eliminarComision(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Obtiene las comisiones asociadas a una asignatura específica.
     *
     * @param asignaturaId ID de la asignatura
     * @return Lista de comisiones asociadas a la asignatura
     */
    @GetMapping("/por-asignatura/{asignaturaId}")
    public ResponseEntity<List<Comision>> obtenerComisionesPorAsignatura(@PathVariable Long asignaturaId) {
        List<Comision> comisiones = comisionService.obtenerComisionesPorAsignatura(asignaturaId);

        if (comisiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(comisiones);
    }

    /**
     * Obtiene las comisiones asociadas a un profesor específico.
     *
     * @param profesorId ID del profesor
     * @return Lista de comisiones asociadas al profesor
     */
    @GetMapping("/por-profesor/{profesorId}")
    public ResponseEntity<List<Comision>> obtenerComisionesPorProfesor(@PathVariable Long profesorId) {
        List<Comision> comisiones = comisionService.obtenerComisionesPorProfesor(profesorId);

        if (comisiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(comisiones);
    }

}
