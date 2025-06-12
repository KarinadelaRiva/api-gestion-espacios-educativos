package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.EspacioCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.EspacioResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.EspacioUpdateDTO;
import com.apigestionespacios.apigestionespacios.dtos.ReservaResponseDTO;
import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.service.EspacioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/espacios")
@Tag(name = "Espacios", description = "Operaciones relacionadas con los espacios.")
public class EspacioController {
    private final EspacioService espacioService;

    @Autowired
    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    /**
     * Endpoint para listar todos los espacios.
     * Permite a administradores y profesores acceder a la lista de espacios.
     *
     * @return Lista de espacios en formato DTO.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener todos los espacios.", description = "Permite a administradores y profesores obtener un listado con todos los espacios.")
    public ResponseEntity<List<EspacioResponseDTO>> obtenerEspacios() {
        return new ResponseEntity<>(espacioService.obtenerTodos(), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un espacio por su ID.
     * Permite a administradores y profesores acceder a los detalles de un espacio específico.
     *
     * @param id ID del espacio a buscar.
     * @return Espacio encontrado en formato DTO.
     */
    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener espacio por id.", description = "Permite a administradores y profesores obtener un espacio por medio de su id.")
    public ResponseEntity<EspacioResponseDTO> obtenerEspacioPorId(@PathVariable Long id) {
        return new ResponseEntity<>(espacioService.obtenerDTOPorId(id), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un espacio por su nombre.
     * Permite a administradores y profesores acceder a los detalles de un espacio específico por su nombre.
     *
     * @param nombre Nombre del espacio a buscar.
     * @return Espacio encontrado en formato DTO.
     */
    @GetMapping("/nombre/{nombre}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener espacio por nombre.", description = "Permite a administradores y profesores obtener un espacio por medio de su nombre.")
    public ResponseEntity<EspacioResponseDTO> obtenerEspacioPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(espacioService.obtenerPorNombre(nombre), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener las reservas de un espacio específico.
     * Solo accesible por administradores.
     *
     * @param id ID del espacio.
     * @return Lista de reservas asociadas al espacio.
     */
    @GetMapping("/{id}/reservas")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener reservas de un espacio.", description = "Permite a administradores obtener las reservas de un espacio por medio de su id.")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorEspacio(@PathVariable Long id) {
        return new ResponseEntity<>(espacioService.obtenerReservasPorEspacio(id), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener espacios por capacidad mínima.
     * Permite a administradores y profesores filtrar espacios por capacidad.
     *
     * @param capacidad Capacidad mínima del espacio.
     * @return Lista de espacios que cumplen con la capacidad mínima.
     */
    @GetMapping("/capacidad/{capacidad}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener espacio por capacidad mayor a valor pasado por parámetro.", description = "Permite a administradores y profesores obtener un listado de los espacios que cumplan al menos con la capacidad especificada.")
    public ResponseEntity<List<EspacioResponseDTO>> obtenerEspaciosPorCapacidadMayorA(@PathVariable Integer capacidad) {
        return new ResponseEntity<>(espacioService.obtenerPorCapacidadMayorA(capacidad), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener espacios con proyector.
     * Permite a administradores y profesores acceder a los espacios que cuentan con proyector.
     *
     * @return Lista de espacios con proyector en formato DTO.
     */
    @GetMapping("/proyector")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener espacios que tengan proyectores.", description = "Permite a administradores y profesores obtener un listado de los espacios que incluyan un proyector.")

    public ResponseEntity<List<EspacioResponseDTO>> obtenerEspaciosConProyector() {
        return new ResponseEntity<>(espacioService.obtenerConProyector(), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener espacios con TV.
     * Permite a administradores y profesores acceder a los espacios que cuentan con TV.
     *
     * @return Lista de espacios con TV en formato DTO.
     */
    @GetMapping("/tv")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener espacios que tengan tv.", description = "Permite a administradores y profesores obtener un listado de los espacios que incluyan un televisor.")
    public ResponseEntity<List<EspacioResponseDTO>> obtenerEspaciosConTV() {
        return new ResponseEntity<>(espacioService.obtenerConTV(), HttpStatus.OK);
    }

    /**
     * Endpoint para crear un nuevo espacio.
     * Solo accesible por administradores.
     *
     * @param espacio Objeto DTO con los datos del espacio a crear.
     * @return Espacio creado con su ID asignado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear espacio.", description = "Permite a administradores crear un nuevo espacio.")

    public ResponseEntity<Espacio> crearEspacio(@RequestBody EspacioCreateDTO espacio) {
        return new  ResponseEntity<>(espacioService.guardar(espacio), HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar un espacio existente.
     * Solo accesible por administradores.
     *
     * @param espacio Objeto DTO con los datos actualizados del espacio.
     * @return Espacio actualizado.
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar espacio.", description = "Permite a administradores actualizar un espacio.")

    public ResponseEntity<Espacio> actualizarEspacio(@RequestBody EspacioUpdateDTO espacio) {
        return new ResponseEntity<>(espacioService.actualizar(espacio), HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar un espacio por su ID.
     * Solo accesible por administradores.
     *
     * @param id ID del espacio a eliminar.
     * @return Respuesta vacía con código 204 No Content si la operación es exitosa.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar espacio.", description = "Permite a administradores eliminar un espacio.")

    public  ResponseEntity<Espacio> eliminarEspacio(@PathVariable Long id) {
        espacioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
