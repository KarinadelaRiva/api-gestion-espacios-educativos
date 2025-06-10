package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.EspacioCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.EspacioResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.EspacioUpdateDTO;
import com.apigestionespacios.apigestionespacios.dtos.ReservaResponseDTO;
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
    public ResponseEntity<List<EspacioResponseDTO>> listar() {
        return new ResponseEntity<>(espacioService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EspacioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(espacioService.obtenerDTOPorId(id), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EspacioResponseDTO> obtenerPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(espacioService.obtenerPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/{id}/reservas")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorEspacio(@PathVariable Long id) {
        return new ResponseEntity<>(espacioService.obtenerReservasPorEspacio(id), HttpStatus.OK);
    }

    @GetMapping("/capacidad/{capacidad}")
    public ResponseEntity<List<EspacioResponseDTO>> obtenerPorCapacidadMinima(@PathVariable Integer capacidad) {
        return new ResponseEntity<>(espacioService.obtenerPorCapacidadMinima(capacidad), HttpStatus.OK);
    }

    @GetMapping("/proyector")
    public ResponseEntity<List<EspacioResponseDTO>> obtenerConProyector() {
        return new ResponseEntity<>(espacioService.obtenerConProyector(), HttpStatus.OK);
    }

    @GetMapping("/tv")
    public ResponseEntity<List<EspacioResponseDTO>> obtenerConTV() {
        return new ResponseEntity<>(espacioService.obtenerConTV(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Espacio> crear(@RequestBody EspacioCreateDTO espacio) {
        return new  ResponseEntity<>(espacioService.guardar(espacio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Espacio> actualizar(@PathVariable Long id, @RequestBody EspacioUpdateDTO espacio) {
        return new ResponseEntity<>(espacioService.actualizar(espacio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Espacio> eliminar(@PathVariable Long id) {
        espacioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
