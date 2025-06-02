package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.service.SolicitudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        if (!solicitudService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> modificarSolicitud(@PathVariable Long id, @RequestBody Solicitud datosNuevos) {
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

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Solicitud> aprobarSolicitud(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.aprobar(id);
        return ResponseEntity.ok(solicitud);
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Solicitud> rechazarSolicitud(@PathVariable Long id, @RequestBody String comentario) {
        Solicitud solicitud = solicitudService.rechazar(id, comentario);
        return ResponseEntity.ok(solicitud);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarSolicitud(
            @PathVariable Long id,
            @RequestParam Long idUsuario // esto lo puedes obtener también del token JWT si tenés auth
    ) {
        try {
            solicitudService.cancelarSolicitud(id, idUsuario);
            return ResponseEntity.ok("Solicitud cancelada correctamente.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PostMapping("/nueva")
    public ResponseEntity<Solicitud> solicitarNuevaReserva(@RequestBody Solicitud solicitud) {
        try {
            Solicitud nueva = solicitudService.solicitarNuevaReserva(solicitud);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/modificar/{reservaId}")
    public ResponseEntity<Solicitud> solicitarModificacionReservaPorId(
            @PathVariable Long reservaId,
            @RequestBody Solicitud datosNuevos
    ) {
        try {
            Solicitud solicitud = solicitudService.solicitarModificacionPorIdReserva(reservaId, datosNuevos);
            return ResponseEntity.status(HttpStatus.CREATED).body(solicitud);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<Page<Solicitud>> obtenerPendientesPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaHoraSolicitud").descending());
        Page<Solicitud> pendientes = solicitudService.obtenerSolicitudesPendientes(pageable);

        if (pendientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pendientes);
    }

    @GetMapping("/usuario/{usuarioId}/historial")
    public ResponseEntity<Page<Solicitud>> historialSolicitudesUsuarioPaginado(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaHoraSolicitud").descending());
        Page<Solicitud> historial = solicitudService.obtenerHistorialPorUsuario(usuarioId, pageable);

        if (historial.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/historial")
    public ResponseEntity<Page<Solicitud>> historialTotal(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaHoraSolicitud").descending());
        Page<Solicitud> historial = solicitudService.obtenerTodasPaginadas(pageable);

        if (historial.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(historial);
    }







}
