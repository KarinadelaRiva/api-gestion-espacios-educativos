package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas(
            @RequestParam (required = false) Long usuarioId)
    {
        if(usuarioId != null) {
            return new ResponseEntity<>(reservaService.listarReservasPorUsuario(usuarioId), HttpStatus.OK);
        }
        return new ResponseEntity<>(reservaService.listarReservas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReserva(id), HttpStatus.OK);
    }

    @GetMapping("/")

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




}