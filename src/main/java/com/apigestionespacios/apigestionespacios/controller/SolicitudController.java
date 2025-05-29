package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }


    @GetMapping
    public ResponseEntity<List<Solicitud>> getTodas() {

        List<Solicitud> solicitudes = solicitudService.obtenerTodasOrdenadasPorFechaHoraSolicitudDesc();
        if(solicitudes.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> getPorId(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.obtenerPorId(id);
        if(solicitud == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(solicitud);
    }

    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestBody Solicitud solicitud) {
        Solicitud nuevaSolicitud = solicitudService.guardar(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!solicitudService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> modificar(@PathVariable Long id, @RequestBody Solicitud datosNuevos) {
        Solicitud actualizada = solicitudService.actualizar(id, datosNuevos);
        if (actualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Solicitud>> buscarSolicitudes(
            @RequestParam(required = false) Long idUsuario,
            @RequestParam(required = false) String estado) {

        List<Solicitud> resultado;

        if (idUsuario != null && estado != null) {
            resultado = solicitudService.obtenerPorEstadoYUsuario(estado, idUsuario);
        } else if (idUsuario != null) {
            resultado = solicitudService.obtenerPorUsuario(idUsuario);
        } else if (estado != null) {
            resultado = solicitudService.obtenerPorEstado(estado);
        } else {
            resultado = solicitudService.obtenerTodos();
        }

        if (resultado.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(resultado);
    }

}
