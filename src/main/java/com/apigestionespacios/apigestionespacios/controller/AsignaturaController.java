package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.service.AsignaturaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AsignaturaController {
    private final AsignaturaService asignaturaService;

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @GetMapping
    public List<Asignatura> listar() {
        return asignaturaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Asignatura obtener(@PathVariable Long id) {
        return asignaturaService.obtenerPorId(id);
    }

    @PostMapping
    public Asignatura crear(@RequestBody Asignatura asignatura) {
        return asignaturaService.guardar(asignatura);
    }

    @PutMapping("/{id}")
    public Asignatura actualizar(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        return asignaturaService.actualizar(id, asignatura);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        asignaturaService.eliminar(id);
    }
}
