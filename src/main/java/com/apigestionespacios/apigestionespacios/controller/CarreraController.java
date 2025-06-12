package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.AsignaturaResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.CarreraCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.CarreraResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.CarreraUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Carrera;
import com.apigestionespacios.apigestionespacios.service.CarreraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras")
@Tag(name = "Carreras", description = "Operaciones relacionadas con las carreras.")
public class CarreraController {
    private final CarreraService carreraService;

    @Autowired
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    /**
     * Endpoint para listar carreras.
     * Permite filtrar por ID o nombre.
     * Los administradores y profesores pueden acceder a esta información.
     *
     * @param id     ID de la carrera (opcional).
     * @param nombre Nombre de la carrera (opcional).
     * @return Lista de carreras filtradas o todas las carreras si no se especifica filtro.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Filtrar carreras por ID o nombre.", description = "Permite a administradores y profesores filtrar entre las distintas carreras. En caso de no proporcionar parámetros devolverá el listado total.")
    public ResponseEntity<List<CarreraResponseDTO>> obtenerCarreras(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre
    )
    {
        if (id != null && nombre != null) {
            return new ResponseEntity<>(List.of(carreraService.obtenerDTOPorId(id)), HttpStatus.OK);
        } else if (id != null) {
            return new ResponseEntity<>(List.of(carreraService.obtenerDTOPorId(id)), HttpStatus.OK);
        } else if (nombre != null) {
            return new ResponseEntity<>(List.of(carreraService.obtenerPorNombre(nombre)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(carreraService.obtenerTodas(), HttpStatus.OK);
        }
    }

    /**
     * Endpoint para obtener las asignaturas de una carrera específica.
     * Permite a administradores y profesores acceder a las asignaturas de una carrera.
     *
     * @param carreraId ID de la carrera.
     * @return Lista de asignaturas asociadas a la carrera.
     */
    @GetMapping("/{carreraId}/asignaturas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener asignaturas de una carrera.", description = "Permite a administradores y profesores obtener un listado de las asignaturas de una carrera específica.")
    public ResponseEntity<List<AsignaturaResponseDTO>> obtenerAsignaturasDeCarrera(@PathVariable Long carreraId) {
        return new ResponseEntity<>(carreraService.obtenerAsignaturasDeCarrera(carreraId), HttpStatus.OK);
    }

    /**
     * Endpoint para crear una nueva carrera.
     * Solo accesible por administradores.
     *
     * @param carrera Objeto DTO con los datos de la carrera a crear.
     * @return Carrera creada con su ID asignado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear nueva carrera.", description = "Permite a administradores crear una nueva carrera.")
    public ResponseEntity<Carrera> crearCarrera(@RequestBody CarreraCreateDTO carrera) {
        return new  ResponseEntity<>(carreraService.crearCarrera(carrera), HttpStatus.CREATED);
    }

    /**
     * Endpoint para asignar asignaturas a una carrera.
     * Solo accesible por administradores.
     *
     * @param carreraId ID de la carrera a la que se le asignarán las asignaturas.
     * @param asignaturaIds Lista de IDs de las asignaturas a asignar.
     * @return Respuesta vacía con código 204 No Content si la operación es exitosa.
     */
    @PostMapping("/{carreraId}/asignaturas")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Asignar asignaturas a una carrera.", description = "Permite a administradores asignar asignaturas a una carrera.")
    public ResponseEntity<Void> asignarAsignaturaACarrera(
            @PathVariable Long carreraId,
            @RequestParam List<Long> asignaturaIds
    ) {
        carreraService.asignarAsignaturaACarrera(carreraId, asignaturaIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para actualizar una carrera existente.
     * Solo accesible por administradores.
     *
     * @param carrera Objeto DTO con los nuevos datos de la carrera.
     * @return Carrera actualizada.
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar carrera.", description = "Permite a administradores actualizar una carrera existente.")
    public ResponseEntity<Carrera> actualizarCarrera(@RequestBody CarreraUpdateDTO carrera) {
        return new ResponseEntity<>(carreraService.actualizarCarrera(carrera), HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar una carrera por su ID.
     * Solo accesible por administradores.
     *
     * @param id ID de la carrera a eliminar.
     * @return Respuesta vacía con código 204 No Content si la operación es exitosa.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar carrera por id.", description = "Permite a administradores eliminar una carrera.")
    public  ResponseEntity<Carrera> eliminarCarrera(@PathVariable Long id) {
        carreraService.eliminarCarrera(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para eliminar asignaturas de una carrera.
     * Solo accesible por administradores.
     *
     * @param id ID de la carrera de la que se eliminarán las asignaturas.
     * @param asignaturaIds Lista de IDs de las asignaturas a eliminar de la carrera.
     * @return Respuesta vacía con código 204 No Content si la operación es exitosa.
     */
    @DeleteMapping("/{id}/asignaturas")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar asignaturas de una carrera.", description = "Permite a administradores eliminar asignaturas de una carrera existente.")

    public ResponseEntity<Void> removerAsignaturaDeCarrera(
            @PathVariable Long id,
            @RequestParam List<Long> asignaturaIds
    ) {
        carreraService.eliminarAsignaturaDeCarrera(id, asignaturaIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
