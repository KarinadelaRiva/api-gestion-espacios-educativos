package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.CronogramaEspaciosDTO;
import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.service.ReservaService;
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
import java.util.HashMap;
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
     * Endpoint para listar todas las reservas.
     *
     * @return Lista de reservas.
     */
    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas()
    {
        return new ResponseEntity<>(reservaService.listarReservas(), HttpStatus.OK);
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
    public ResponseEntity<Page<Reserva>> obtenerHistorialCompleto(
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

        Page<Reserva> reservas = reservaService.obtenerTodasPaginadas(pageable);

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
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReserva(id), HttpStatus.OK);
    }

    /**
     * Endpoint para crear una nueva reserva.
     *
     * @param reserva Reserva a crear.
     * @return Reserva creada.
     */
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.crearReserva(reserva), HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar una reserva existente.
     *
     * @param reserva Reserva con los datos actualizados.
     * @return Reserva actualizada.
     */
    @PutMapping
    public ResponseEntity<Reserva> actualizarReserva(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.actualizarReserva(reserva), HttpStatus.OK);
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
    public ResponseEntity<List<Reserva>> getReservasDelProfesor(@PathVariable Long usuarioId) {
        List<Reserva> reservas = reservaService.obtenerReservasPorProfesor(usuarioId);

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
    public ResponseEntity<List<Reserva>> getReservasVigentesDelProfesor(@PathVariable Long usuarioId) {
        List<Reserva> reservas = reservaService.obtenerReservasVigentesPorProfesor(usuarioId);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/cronograma")
    public ResponseEntity<Map<String, List<Reserva>>> obtenerCronogramaCompletoAgrupadoPorEspacio() {
        // Trae todas las reservas ordenadas por fechaInicio y horaInicio
        List<Reserva> reservas = reservaService.obtenerTodasOrdenadas();

        // Agrupa por espacio
        Map<String, List<Reserva>> cronograma = reservas.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getEspacio().getNombre(),
                        LinkedHashMap::new, // mantiene el orden de inserción
                        Collectors.toList()
                ));

        return ResponseEntity.ok(cronograma);
    }

    @GetMapping("/cronograma-dia")
    public ResponseEntity<Map<String, List<Reserva>>> obtenerCronogramaDia(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        Map<Espacio, List<Reserva>> reservasAgrupadas = reservaService.obtenerCronogramaDiario(fecha);

        // Transformar la clave de Espacio a String (por ejemplo, el nombre del espacio)
        Map<String, List<Reserva>> resultado = reservasAgrupadas.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getNombre(),
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/cronograma-por-dia")
    public ResponseEntity<List<CronogramaEspaciosDTO>> getCronogramaParaFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<CronogramaEspaciosDTO> cronograma = reservaService.obtenerCronogramaDTOParaFecha(fecha);
        return cronograma.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cronograma);
    }
    

}