package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.AsignaturaCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.AsignaturaResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.AsignaturaUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.service.AsignaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaturas")
public class AsignaturaController {
    private final AsignaturaService asignaturaService;

    @Autowired
    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    /**
     * Obtiene una lista de todas las asignaturas.
     *
     * @return Lista de asignaturas con formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<AsignaturaResponseDTO>> listarAsignaturas() {
        return new ResponseEntity<>(asignaturaService.obtenerTodasAsignaturasDTO(), HttpStatus.OK);
    }

    /**
     * Obtiene una asignatura por su ID.
     *
     * @param id ID de la asignatura.
     * @return Asignatura con formato DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaResponseDTO> obtenerAsignaturaPorId(@PathVariable Long id) {
        return new ResponseEntity<>(asignaturaService.obtenerDTOPorId(id), HttpStatus.OK);
    }

    /**
     * Obtiene una asignatura por su código.
     *
     * @param codigo Código de la asignatura.
     * @return Asignatura con formato DTO.
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<AsignaturaResponseDTO> obtenerAsignaturaPorCodigo(@PathVariable Integer codigo) {
        return new ResponseEntity<>(asignaturaService.obtenerPorCodigo(codigo), HttpStatus.OK);
    }

    /**
     * Crea una nueva asignatura a partir de un DTO.
     *
     * @param dto DTO con los datos de la asignatura a crear.
     * @return Asignatura creada con formato DTO.
     */
    @PostMapping
    public ResponseEntity<Asignatura> crearAsignatura(@Valid @RequestBody AsignaturaCreateDTO dto) {
        return new ResponseEntity<>(asignaturaService.crearAsignaturaDesdeDTO(dto), HttpStatus.CREATED);
    }

    /**
     * Actualiza una asignatura existente.
     *
     * @param id ID de la asignatura a actualizar.
     * @param dto DTO con los datos actualizados de la asignatura.
     * @return Asignatura actualizada con formato DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> actualizarAsignatura(@PathVariable Long id, @Valid @RequestBody AsignaturaUpdateDTO dto) {
        return new ResponseEntity<>(asignaturaService.actualizarAsignatura(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignatura(@PathVariable Long id) {
        asignaturaService.eliminarAsignatura(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
