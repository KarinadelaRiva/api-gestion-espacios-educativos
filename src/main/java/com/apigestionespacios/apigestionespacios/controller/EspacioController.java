package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.service.EspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/espacios")
public class EspacioController {
    private final EspacioService espacioService;

    @Autowired
    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @GetMapping
    public ResponseEntity<List<Espacio>> listar() {
        return new ResponseEntity<>(espacioService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Espacio> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(espacioService.obtenerPorId(id), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Espacio> obtenerPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(espacioService.obtenerPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/{id}/reservas")
    public ResponseEntity<List<Reserva>> obtenerReservasPorEspacio(@PathVariable Long id) {
        return new ResponseEntity<>(espacioService.obtenerReservasPorEspacio(id), HttpStatus.OK);
    }

    @GetMapping("/capacidad/{capacidad}")
    public ResponseEntity<List<Espacio>> obtenerPorCapacidadMinima(@PathVariable Integer capacidad) {
        return new ResponseEntity<>(espacioService.obtenerPorCapacidadMinima(capacidad), HttpStatus.OK);
    }

    @GetMapping("/proyector")
    public ResponseEntity<List<Espacio>> obtenerConProyector() {
        return new ResponseEntity<>(espacioService.obtenerConProyector(), HttpStatus.OK);
    }

    @GetMapping("/tv")
    public ResponseEntity<List<Espacio>> obtenerConTV() {
        return new ResponseEntity<>(espacioService.obtenerConTV(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Espacio> crear(@RequestBody Espacio espacio) {
        return new  ResponseEntity<>(espacioService.guardar(espacio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Espacio> actualizar(@PathVariable Long id, @RequestBody Espacio espacio) {
        return new ResponseEntity<>(espacioService.actualizar(id, espacio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Espacio> eliminar(@PathVariable Long id) {
        espacioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
