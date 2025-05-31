package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.service.AsignaturaService;
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

    @GetMapping
    public ResponseEntity<List<Asignatura>> listarAsignaturas() {
        return new ResponseEntity<>(asignaturaService.obtenerTodas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> obtenerAsignaturaPorId(@PathVariable Long id) {
        return new ResponseEntity<>(asignaturaService.obtenerPorId(id), HttpStatus.OK);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Asignatura> obtenerAsignaturaPorCodigo(@PathVariable Integer codigo) {
        return new ResponseEntity<>(asignaturaService.obtenerPorCodigo(codigo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Asignatura> crearAsignatura(@RequestBody Asignatura asignatura) {
        return new ResponseEntity<>(asignaturaService.crearAsignatura(asignatura), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> actualizarAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        return new ResponseEntity<>(asignaturaService.actualizarAsignatura(id, asignatura), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignatura(@PathVariable Long id) {
        asignaturaService.eliminarAsignatura(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
