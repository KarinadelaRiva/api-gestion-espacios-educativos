package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.entities.Carrera;
import com.apigestionespacios.apigestionespacios.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {
    private final CarreraService carreraService;

    @Autowired
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public ResponseEntity<List<Carrera>> listar(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre
    )
    {
        if (id != null && nombre != null) {
            return new ResponseEntity<>(List.of(carreraService.obtenerPorId(id)), HttpStatus.OK);
        } else if (id != null) {
            return new ResponseEntity<>(List.of(carreraService.obtenerPorId(id)), HttpStatus.OK);
        } else if (nombre != null) {
            return new ResponseEntity<>(List.of(carreraService.obtenerPorNombre(nombre)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(carreraService.obtenerTodas(), HttpStatus.OK);
        }
    }

    @GetMapping("/{carreraId}/asignaturas")
    public ResponseEntity<List<Asignatura>> obtenerAsignaturasDeCarrera(@PathVariable Long carreraId) {
        return new ResponseEntity<>(carreraService.obtenerAsignaturasDeCarrera(carreraId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Carrera> crear(@RequestBody Carrera carrera) {
        return new  ResponseEntity<>(carreraService.crearCarrera(carrera), HttpStatus.CREATED);
    }

    @PostMapping("/{carreraId}/asignaturas")
    public ResponseEntity<Void> asignarAsignaturaACarrera(
            @PathVariable Long carreraId,
            @RequestParam List<Long> asignaturaIds
    ) {
        carreraService.asignarAsignaturaACarrera(carreraId, asignaturaIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrera> actualizar(@PathVariable Long id, @RequestBody Carrera carrera) {
        return new ResponseEntity<>(carreraService.actualizarCarrera(id, carrera), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Carrera> eliminar(@PathVariable Long id) {
        carreraService.eliminarCarrera(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/asignaturas")
    public ResponseEntity<Void> eliminarAsignaturaDeCarrera(
            @PathVariable Long id,
            @RequestParam List<Long> asignaturaIds
    ) {
        carreraService.eliminarAsignaturaDeCarrera(id, asignaturaIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
