package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.CronogramaEspaciosDTO;
import com.apigestionespacios.apigestionespacios.dtos.ReservaCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.ReservaResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.ReservaUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Endpoint para crear una nueva reserva.
     *
     * @param dto Objeto DTO con los datos de la reserva a crear.
     * @return Reserva creada.
     */
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody ReservaCreateDTO dto) {
        Reserva reserva = reservaService.crearReservaDesdeDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }


    /**
     * Endpoint para actualizar una reserva existente.
     *
     * @param id ID de la reserva a actualizar.
     * @param reservaUpdateDTO Objeto DTO con los datos actualizados de la reserva.
     * @return Reserva actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaUpdateDTO reservaUpdateDTO) {

        // Valida que el ID de path y el DTO coincidan para evitar inconsistencia
        if (!id.equals(reservaUpdateDTO.getId())) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        Reserva reservaActualizada = reservaService.actualizarReservaDesdeDTO(reservaUpdateDTO);
        return ResponseEntity.ok(reservaActualizada);
    }

    /**
     * Endpoint para listar todas las reservas.
     *
     * @return Lista de reservas en formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarTodasReservas() {
        List<ReservaResponseDTO> reservasDTO = reservaService.listarReservasDTO();
        return ResponseEntity.ok(reservasDTO);
    }

    /**
     * Endpoint para obtener el historial completo de reservas paginadas.
     *
     * @param page Número de página a consultar.
     * @param size Tamaño de la página.
     * @param sortBy Campo por el cual ordenar las reservas.
     * @param sortDir Dirección del ordenamiento (ascendente o descendente).
     * @return Página de reservas.
     */
    @GetMapping("/historial-completo")
    public ResponseEntity<Page<ReservaResponseDTO>> obtenerHistorialCompleto(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaInicio") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<ReservaResponseDTO> reservas = reservaService.obtenerReservasPaginadasDTO(pageable);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    /**
     * Endpoint para obtener una reserva por su ID.
     *
     * @param id ID de la reserva a consultar.
     * @return Reserva encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReserva(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReservaDTO(id), HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar.
     * @return Respuesta vacía con estado NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para ver las reservas de un profesor específico.
     *
     * @param usuarioId ID del usuario (profesor) cuyas reservas se desean consultar.
     * @return Lista de reservas del profesor.
     */
    @GetMapping("/profesor/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> getReservasDelProfesor(@PathVariable Long usuarioId) {
        List<ReservaResponseDTO> reservas = reservaService.obtenerReservasPorProfesor(usuarioId);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    /**
     * Endpoint para ver las reservas vigentes de un profesor específico.
     *
     * @param usuarioId ID del usuario (profesor) cuyas reservas vigentes se desean consultar.
     * @return Lista de reservas vigentes del profesor.
     */
    @GetMapping("/profesor/{usuarioId}/vigentes")
    public ResponseEntity<List<ReservaResponseDTO>> getReservasVigentesDelProfesor(@PathVariable Long usuarioId) {
        List<ReservaResponseDTO> reservas = reservaService.obtenerReservasVigentesPorProfesor(usuarioId);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    /**
     * Endpoint para obtener el cronograma completo de reservas agrupado por espacio.
     *
     * @return Mapa donde la clave es el nombre del espacio y el valor es una lista de reservas.
     */
    @GetMapping("/cronograma")
    public ResponseEntity<Map<String, List<ReservaResponseDTO>>> obtenerCronogramaCompletoAgrupadoPorEspacio() {
        // Trae todas las reservas ordenadas por fechaInicio y horaInicio
        List<ReservaResponseDTO> reservas = reservaService.obtenerTodasOrdenadas();

        // Agrupa por espacio
        Map<String, List<ReservaResponseDTO>> cronograma = reservas.stream()
                .collect(Collectors.groupingBy(
                        ReservaResponseDTO::getNombreEspacio,
                        LinkedHashMap::new, // mantiene el orden de inserción
                        Collectors.toList()
                ));

        return ResponseEntity.ok(cronograma);
    }

    /**
     * Endpoint para obtener el cronograma de espacios para una fecha específica.
     *
     * @param fecha Fecha para la cual se desea obtener el cronograma.
     * @return Lista de cronogramas de espacios para la fecha especificada.
     */
    @GetMapping("/cronograma-por-dia")
    public ResponseEntity<List<CronogramaEspaciosDTO>> getCronogramaParaFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<CronogramaEspaciosDTO> cronograma = reservaService.obtenerCronogramaDTOParaFecha(fecha);
        return cronograma.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cronograma);
    }
    

}