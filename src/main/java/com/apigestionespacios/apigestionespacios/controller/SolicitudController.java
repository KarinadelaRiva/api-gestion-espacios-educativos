package com.apigestionespacios.apigestionespacios.controller;

import com.apigestionespacios.apigestionespacios.dtos.solicitud.SolicitudCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.solicitud.SolicitudResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.solicitud.SolicitudUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
@Tag(name = "Solicitudes", description = "Operaciones relacionadas con las solicitudes.")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }


    @Operation(summary = "Obtener todas las solicitudes", description = "Devuelve la lista de todas las solicitudes ordenadas por fecha y hora de solicitud descendente.")
    @GetMapping
    public ResponseEntity<List<Solicitud>> getTodas() {

        List<Solicitud> solicitudes = solicitudService.obtenerTodasOrdenadasPorFechaHoraSolicitudDesc();
        if(solicitudes.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(solicitudes);
    }

    @Operation(summary = "Obtener solicitud por ID", description = "Devuelve los detalles de una solicitud específica según su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> getPorId(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.obtenerPorId(id);
        if(solicitud == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(solicitud);
    }

    @Operation(summary = "Crear nueva solicitud", description = "Crea una nueva solicitud con los datos proporcionados en el cuerpo de la petición.")
    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestBody Solicitud solicitud) {
        Solicitud nuevaSolicitud = solicitudService.guardar(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    @Operation(summary = "Eliminar solicitud", description = "Elimina una solicitud existente según su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        if (!solicitudService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar solicitud", description = "Actualiza los datos de una solicitud existente según su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> modificarSolicitud(@PathVariable Long id, @RequestBody SolicitudCreateDTO datosNuevos) {
        SolicitudResponseDTO actualizada = solicitudService.actualizarSolicitudDesdeDTO(id, datosNuevos);
        if (actualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Buscar solicitudes", description = "Permite buscar solicitudes por ID de usuario y/o estado. Si no se especifican parámetros, devuelve todas las solicitudes.")
    @GetMapping("/buscar")
    public ResponseEntity<List<Solicitud>> buscarSolicitudes(
            @RequestParam(required = false) Long idUsuario,
            @RequestParam(required = false) String estado)
    {
        List<Solicitud> resultado;

        if (idUsuario != null && estado != null) {
            resultado = solicitudService.obtenerPorEstadoYUsuario(estado, idUsuario);
        } else if (idUsuario != null) {
            resultado = solicitudService.obtenerPorUsuario(idUsuario);
        } else if (estado != null) {
            resultado = solicitudService.obtenerPorEstado(estado);
        } else {
            Pageable pageable = PageRequest.of(0, 10, Sort.by("fechaHoraSolicitud").descending());
            resultado = solicitudService.obtenerTodasPaginadas(pageable).getContent();
        }

        if (resultado.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Aprobar solicitud", description = "Aprueba una solicitud existente según su ID.")
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Solicitud> aprobarSolicitud(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.aprobar(id);
        return ResponseEntity.ok(solicitud);
    }

    @Operation(summary = "Rechazar solicitud", description = "Rechaza una solicitud existente según su ID y guarda un comentario de rechazo.")
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Solicitud> rechazarSolicitud(@PathVariable Long id, @RequestBody String comentario) {
        Solicitud solicitud = solicitudService.rechazar(id, comentario);
        return ResponseEntity.ok(solicitud);
    }

    @Operation(summary = "Cancelar solicitud", description = "Cancela una solicitud existente según su ID e ID de usuario.")
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

    @Operation(summary = "Solicitar nueva reserva", description = "Permite crear una nueva solicitud de reserva con los datos proporcionados.")
    @PostMapping("/nueva")
    public ResponseEntity<Solicitud> solicitarNuevaReserva(@RequestBody Solicitud solicitud) {
        try {
            Solicitud nueva = solicitudService.solicitarNuevaReserva(solicitud);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Solicitar modificación de reserva", description = "Permite solicitar la modificación de una reserva existente proporcionando el ID de la reserva y los nuevos datos.")
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

    @Operation(summary = "Obtener solicitudes pendientes paginadas", description = "Devuelve una página de solicitudes pendientes, ordenadas por fecha y hora de solicitud descendente.")
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

    @Operation(summary = "Historial de solicitudes de un usuario paginado", description = "Devuelve el historial paginado de solicitudes de un usuario específico.")
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

    @Operation(summary = "Historial total de solicitudes paginado", description = "Devuelve el historial total paginado de todas las solicitudes registradas.")
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
