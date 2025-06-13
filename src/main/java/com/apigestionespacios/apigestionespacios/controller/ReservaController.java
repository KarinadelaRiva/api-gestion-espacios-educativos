package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.cronograma.CronogramaEspaciosDTO;
import com.apigestionespacios.apigestionespacios.dtos.reserva.ReservaCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.reserva.ReservaResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.reserva.ReservaUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Operaciones relacionadas con las reservas.")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Endpoint para crear una nueva reserva.
     * Solo accesible por administradores.
     *
     * @param dto Objeto DTO con los datos de la reserva a crear.
     * @return Reserva creada.
     */
    @Operation(summary = "Crear reserva", description = "Permite a administradores crear una nueva reserva a partir de los datos proporcionados en el DTO.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody ReservaCreateDTO dto) {
        Reserva reserva = reservaService.crearReservaDesdeDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }


    /**
     * Endpoint para actualizar una reserva existente.
     * Solo accesible por administradores.
     *
     * @param reservaUpdateDTO Objeto DTO con los datos actualizados de la reserva.
     * @return Reserva actualizada.
     */
    @Operation(summary = "Actualizar reserva", description = "Permite a administradores actualizar una reserva existente usando los datos del DTO.")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reserva> actualizarReserva(@Valid @RequestBody ReservaUpdateDTO reservaUpdateDTO) {
        Reserva reservaActualizada = reservaService.actualizarReservaDesdeDTO(reservaUpdateDTO);
        return ResponseEntity.ok(reservaActualizada);
    }

    /**
     * Endpoint para listar todas las reservas.
     * Solo accesible por administradores.
     *
     * @return Lista de reservas en formato DTO.
     */
    @Operation(summary = "Listar reservas", description = "Permite a administradores obtener la lista completa de reservas registradas.")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservas() {
        List<ReservaResponseDTO> reservasDTO = reservaService.listarReservasDTO();
        if (reservasDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservasDTO);
    }

    /**
     * Endpoint para obtener el historial completo de reservas paginadas.
     * Solo accesible por administradores.
     *
     * @param page Número de página a consultar.
     * @param size Tamaño de la página.
     * @param sortBy Campo por el cual ordenar las reservas.
     * @param sortDir Dirección del ordenamiento (ascendente o descendente).
     * @return Página de reservas.
     */
    @Operation(summary = "Historial paginado de reservas", description = "Permite a administradores obtener el historial completo de reservas de forma paginada y ordenada.")
    @GetMapping("/historial-paginado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ReservaResponseDTO>> obtenerReservas(
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
     * Solo accesible por administradores.
     *
     * @param id ID de la reserva a consultar.
     * @return Reserva encontrada.
     */
    @Operation(summary = "Obtener reserva por ID", description = "Permite a administradores obtener los detalles de una reserva específica por su ID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDTO> obtenerReserva(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReservaDTO(id), HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar una reserva por su ID.
     * Solo accesible por administradores.
     *
     * @param id ID de la reserva a eliminar.
     * @return Respuesta vacía con estado NO_CONTENT.
     */
    @Operation(summary = "Eliminar reserva", description = "Permite a administradores eliminar una reserva existente por su ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para obtener las reservas del profesor logueado.
     * Solo accesible por profesores.
     *
     * @param authentication Autenticación del usuario (profesor).
     * @return Lista de reservas del profesor autenticado.
     */
    @Operation(summary = "Listar reservas históricas y vigentes de profesor logueado.", description = "Permite a profesores obtener el historial de sus propias reservas.")
    @PreAuthorize("hasRole('PROFESOR')")
    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerMiHistorialReservas(Authentication authentication) {
        Usuario usuarioLogueado = (Usuario) authentication.getPrincipal();
        Long profesorId = usuarioLogueado.getId();

        List<ReservaResponseDTO> reservas = reservaService.obtenerReservasPorProfesor(profesorId);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    /**
     * Endpoint para obtener las reservas vigentes del profesor logueado.
     * Solo accesible por profesores.
     *
     * @param authentication Autenticación del usuario (profesor).
     * @return Lista de reservas vigentes del profesor autenticado.
     */
    @Operation(summary = "Listar reservas vigentes de profesor logueado", description = "Permite a profesores obtener la lista de sus reservas vigentes.")
    @PreAuthorize("hasRole('PROFESOR')")
    @GetMapping("/mis-reservas/vigentes")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerMisReservasVigentes(Authentication authentication) {
        Usuario usuarioLogueado = (Usuario) authentication.getPrincipal();
        Long profesorId = usuarioLogueado.getId();

        List<ReservaResponseDTO> reservas = reservaService.obtenerReservasVigentesPorProfesor(profesorId);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }


    /**
     * Endpoint para obtener el cronograma completo de reservas agrupado por espacio.
     * Acceso público, no requiere autenticación.
     *
     * @return Mapa donde la clave es el nombre del espacio y el valor es una lista de reservas.
     */
    @Operation(summary = "Cronograma completo de reservas", description = "Obtiene el cronograma completo de reservas agrupado por espacio. Acceso público.")
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
     * Acceso público, no requiere autenticación.
     *
     * @param fecha Fecha para la cual se desea obtener el cronograma.
     * @return Lista de cronogramas de espacios para la fecha especificada.
     */
    @Operation(summary = "Cronograma de espacios por fecha", description = "Obtiene el cronograma de reservas para una fecha específica, agrupado por espacio. Acceso público.")
    @GetMapping("/cronograma-por-dia")
    public ResponseEntity<List<CronogramaEspaciosDTO>> obtenerCronogramaParaFechaAgrupadoPorEspacio(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<CronogramaEspaciosDTO> cronograma = reservaService.obtenerCronogramaDTOParaFecha(fecha);
        return cronograma.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cronograma);
    }

}