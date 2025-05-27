package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reservas")
public class ReservaService {

    @Autowired
    private ReservaRepository ReservaRepository;

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() {
        return new ResponseEntity<>(ReservaRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        return ReservaRepository.findById(id)
                .map(reserva -> new ResponseEntity<>(reserva, HttpStatus.OK))
                .orElseThrow(() -> new NoSuchElementException("Reserva con ID " + id + " no encontrada."));
    }

    @PostMapping
    public void crearReserva(@RequestBody Reserva reserva) {
        ReservaRepository.save(reserva);
    }

    @PutMapping
    public ResponseEntity<Reserva> actualizarReserva(@RequestBody Reserva reserva) {
        if (ReservaRepository.existsById(Long.valueOf(reserva.getId()))) {
            Reserva updatedReserva = ReservaRepository.save(reserva);
            return new ResponseEntity<>(updatedReserva, HttpStatus.OK);
        } else {
            throw new NoSuchElementException("Reserva con ID " + reserva.getId() + " no encontrada.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        if (ReservaRepository.existsById(id)) {
            ReservaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new NoSuchElementException("Reserva con ID " + id + " no encontrada.");
        }
    }




}
