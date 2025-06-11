package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.ComisionCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.ComisionResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.ComisionUpdateDTO;
import com.apigestionespacios.apigestionespacios.dtos.CronogramaComisionDTO;
import com.apigestionespacios.apigestionespacios.entities.Comision;
import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.service.ComisionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
     * Solo accesible por administradores.
     *
     * @return Lista de todas las comisiones
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComisionResponseDTO>> obtenerComisiones() {
        return new ResponseEntity<>(comisionService.obtenerTodasComisionesDTO(), HttpStatus.OK);
    }

    /**
     * Obtiene una comisión por su ID.
     * Si la comisión no existe, lanza una ResourceNotFoundException.
     * Solo accesible por administradores.
     *
     * @param id ID de la comisión a buscar
     * @return Comisión encontrada
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CronogramaComisionDTO> obtenerComisonPorId(@PathVariable Long id) {
        return new ResponseEntity<>(comisionService.obtenerComisionDTOPorId(id), HttpStatus.OK);
    }

    /**
     * Crea una nueva comisión.
     * Valida que el DTO de creación sea válido.
     * Solo accesible por administradores y profesores.
     *
     * @param comisionCreateDTO DTO de creación de comisión
     * @return Comisión creada
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    public ResponseEntity<Comision> crearComision(@Valid @RequestBody ComisionCreateDTO comisionCreateDTO) {
        return new ResponseEntity<>(comisionService.crearComisionDesdeDTO(comisionCreateDTO), HttpStatus.CREATED);
    }

    /**
     * Actualiza una comisión existente.
     * Valida que el ID de la comisión y el DTO de actualización sean válidos.
     * Solo accesible por administradores y profesores.
     *
     * @param id ID de la comisión a actualizar
     * @param comisionUpdateDTO DTO de actualización de comisión
     * @return Comisión actualizada
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    public ResponseEntity<Comision> actualizarComision(@PathVariable Long id, @Valid @RequestBody ComisionUpdateDTO comisionUpdateDTO) {
        return new ResponseEntity<>(comisionService.actualizarComision(id, comisionUpdateDTO), HttpStatus.OK);
    }

     /**
     * Elimina una comisión por su ID.
     * Si la comisión no existe, lanza una ResourceNotFoundException
     * Solo accesible por administradores.
     *
     * @param id ID de la comisión a eliminar
     * @return Respuesta vacía con estado NO_CONTENT
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarComision(@PathVariable Long id) {
        comisionService.eliminarComision(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Obtiene las comisiones asociadas a una asignatura específica.
     * Solo accesible por administradores.
     *
     * @param asignaturaId ID de la asignatura
     * @return Lista de comisiones asociadas a la asignatura
     */
    @GetMapping("/por-asignatura/{asignaturaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComisionResponseDTO>> obtenerComisionesPorAsignatura(@PathVariable Long asignaturaId) {
        List<ComisionResponseDTO> comisiones = comisionService.obtenerComisionesPorAsignatura(asignaturaId);

        if (comisiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(comisiones);
    }

    /**
     * Obtiene las comisiones asociadas al profesor autenticado.
     * Solo accesible por profesores.
     *
     * @param authentication Autenticación del usuario
     * @return Lista de comisiones asociadas al profesor autenticado
     */
    @GetMapping("/mis-comisiones")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<List<ComisionResponseDTO>> obtenerMisComisiones(Authentication authentication) {
        Usuario usuarioLogueado = (Usuario) authentication.getPrincipal();
        Long profesorId = usuarioLogueado.getId(); // extraemos el ID del profesor autenticado

        List<ComisionResponseDTO> comisiones = comisionService.obtenerComisionesPorProfesor(profesorId);

        if (comisiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(comisiones);
    }

}
