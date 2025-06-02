package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.service.ReservaService;
import com.apigestionespacios.apigestionespacios.dtos.CronogramaEspaciosDTO;
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

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas()
    {
        return new ResponseEntity<>(reservaService.listarReservas(), HttpStatus.OK);
    }

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


    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReserva(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.crearReserva(reserva), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Reserva> actualizarReserva(@RequestBody Reserva reserva) {
        return new ResponseEntity<>(reservaService.actualizarReserva(reserva), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profesor/{usuarioId}")
    public ResponseEntity<List<Reserva>> getReservasDelProfesor(@PathVariable Long usuarioId) {
        List<Reserva> reservas = reservaService.obtenerReservasPorProfesor(usuarioId);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/profesor/{usuarioId}/vigentes")
    public ResponseEntity<List<Reserva>> getReservasVigentesDelProfesor(@PathVariable Long usuarioId) {
        List<Reserva> reservas = reservaService.obtenerReservasVigentesPorProfesor(usuarioId);

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/cronograma")
    public ResponseEntity<Map<String, Object>> obtenerCronogramaAgrupadoPorEspacio(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(defaultValue = "0") int page // cada página es un día
    ) {
        LocalDate fechaConsulta = fechaInicio.plusDays(page);
        Map<Espacio, List<Reserva>> agrupado = reservaService.obtenerReservasPorDiaAgrupadas(fechaConsulta);

        List<CronogramaEspaciosDTO> espacios = agrupado.entrySet().stream()
                .map(e -> new CronogramaEspaciosDTO(e.getKey().getId(), e.getKey().getNombre(), e.getValue()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("fecha", fechaConsulta);
        response.put("espacios", espacios);

        return ResponseEntity.ok(response);
    }





}