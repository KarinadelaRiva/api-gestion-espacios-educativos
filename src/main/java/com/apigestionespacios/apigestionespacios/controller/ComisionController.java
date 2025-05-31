package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Comision;
import com.apigestionespacios.apigestionespacios.service.ComisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comisiones")
public class ComisionController {

    private final ComisionService comisionService;

    @Autowired
    public ComisionController(ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    @GetMapping
    public ResponseEntity<List<Comision>> listar() {
        return new ResponseEntity<>(comisionService.obtenerTodas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comision> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(comisionService.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comision> crear(@RequestBody Comision comision) {
        return new ResponseEntity<>(comisionService.guardar(comision), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comision> actualizar(@PathVariable Long id, @RequestBody Comision comision) {
        return new ResponseEntity<>(comisionService.actualizar(id, comision), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        comisionService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
