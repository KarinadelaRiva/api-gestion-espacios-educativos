package com.apigestionespacios.apigestionespacios.controller;

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
    public ResponseEntity<List<Carrera>> listar(){
        return new ResponseEntity<>(carreraService.obtenerTodas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(carreraService.obtenerPorId(id), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Carrera> obtenerPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(carreraService.obtenerPorNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Carrera> crear(@RequestBody Carrera carrera) {
        return new  ResponseEntity<>(carreraService.crearCarrera(carrera), HttpStatus.CREATED);
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

}
