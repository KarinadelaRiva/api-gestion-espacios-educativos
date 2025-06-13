package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.asignatura.AsignaturaCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.asignatura.AsignaturaResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.asignatura.AsignaturaUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.service.AsignaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaturas")
@Tag(name = "Asignaturas", description = "Operaciones relacionadas con las asignaturas.")
public class AsignaturaController {
    private final AsignaturaService asignaturaService;

    @Autowired
    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    /**
     * Obtiene todas las asignaturas en formato DTO.
     * Permite a administradores y profesores acceder a la lista de asignaturas.
     *
     * @return Lista de asignaturas con formato DTO.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(
            summary = "Obtener todas las asignaturas.",
            description = "Devuelve una lista de todas las asignaturas en el sistema.")
    public ResponseEntity<List<AsignaturaResponseDTO>> obtenerAsignaturas() {
        return new ResponseEntity<>(asignaturaService.obtenerTodasAsignaturasDTO(), HttpStatus.OK);
    }

    /**
     * Obtiene una asignatura por su ID.
     * Permite a administradores y profesores acceder a los detalles de una asignatura específica.
     *
     * @param id ID de la asignatura.
     * @return Asignatura con formato DTO.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(
            summary = "Obtener asignatura por ID.", description =
            "Devuelve un DTO con la asignatura con ID coincidente.")
    public ResponseEntity<AsignaturaResponseDTO> obtenerAsignaturaPorId(
            @Parameter(description = "ID de la asignatura a buscar", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(asignaturaService.obtenerDTOPorId(id), HttpStatus.OK);
    }

    /**
     * Obtiene una asignatura por su código.
     * Permite a administradores y profesores acceder a los detalles de una asignatura específica por su código.
     *
     * @param codigo Código de la asignatura.
     * @return Asignatura con formato DTO.
     */
    @GetMapping("/codigo/{codigo}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @Operation(
            summary = "Obtener asignatura por ID.",
            description = "Devuelve un DTO con la asignatura con código coincidente.")
    public ResponseEntity<AsignaturaResponseDTO> obtenerAsignaturaPorCodigo(
            @Parameter(description = "Código de la asignatura a buscar", example = "1001")
            @PathVariable Integer codigo) {
        return new ResponseEntity<>(asignaturaService.obtenerPorCodigo(codigo), HttpStatus.OK);
    }

    /**
     * Crea una nueva asignatura a partir de un DTO.
     * Permite a administradores crear nuevas asignaturas en el sistema.
     *
     * @param dto DTO con los datos de la asignatura a crear.
     * @return Asignatura creada con formato DTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Crea una nueva asignatura.",
            description = "Crea una nueva asignatura a partir de un DTO.")
    public ResponseEntity<Asignatura> crearAsignatura(
            @Parameter(description = "DTO de creación de asignatura", required = true)
            @Valid @RequestBody AsignaturaCreateDTO dto) {
        return new ResponseEntity<>(asignaturaService.crearAsignaturaDesdeDTO(dto), HttpStatus.CREATED);
    }

    /**
     * Actualiza una asignatura existente.
     * Permite a administradores actualizar los datos de una asignatura específica.
     *
     * @param dto DTO con los datos actualizados de la asignatura.
     * @return Asignatura actualizada con formato DTO.
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Actualiza una asignatura existente.",
            description = "Permite a administradores actualizar los datos de una asignatura específica.")
    public ResponseEntity<Asignatura> actualizarAsignatura(
            @Parameter(description = "DTO de actualización de asignatura", required = true)
            @Valid @RequestBody AsignaturaUpdateDTO dto) {
        return new ResponseEntity<>(asignaturaService.actualizarAsignatura(dto), HttpStatus.OK);
    }

    /**
     * Elimina una asignatura por su ID.
     * Permite a administradores eliminar asignaturas del sistema.
     *
     * @param id ID de la asignatura a eliminar.
     * @return Respuesta vacía con estado NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Elimina una asignatura por su ID.",
            description = "Permite a administradores eliminar asignaturas del sistema.")
    public ResponseEntity<Void> eliminarAsignatura(
            @Parameter(description = "ID de la asignatura a eliminar", example = "1")
            @PathVariable Long id) {
        asignaturaService.eliminarAsignatura(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
