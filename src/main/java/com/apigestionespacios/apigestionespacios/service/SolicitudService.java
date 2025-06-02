package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.entities.enums.EstadoSolicitud;
import com.apigestionespacios.apigestionespacios.exceptions.EntityValidationException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ReservaService reservaService;

    @Autowired
    public SolicitudService(SolicitudRepository solicitudRepository, ReservaService reservaService) {
        this.solicitudRepository = solicitudRepository;
        this.reservaService = reservaService;
    }

    public List<Solicitud> obtenerTodos() {
        return solicitudRepository.findAll();
    }

    public Page<Solicitud> obtenerTodasPaginadas(Pageable pageable) {
        return solicitudRepository.findAll(pageable);
    }


    public Solicitud obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrado con id: " + id));
    }

    public Solicitud guardar(Solicitud Solicitud) {
        return solicitudRepository.save(Solicitud);
    }

    public void eliminar(Long id) {
        solicitudRepository.deleteById(id);
    }

    public Solicitud actualizar(Long id, Solicitud nuevaSolicitud){
        Solicitud existente = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrado con id: " + id));

        Solicitud actualizada = existente.toBuilder()
                .id(nuevaSolicitud.getId())
                .usuario(nuevaSolicitud.getUsuario())
                .reservaOriginal(nuevaSolicitud.getReservaOriginal())
                .nuevoEspacio(nuevaSolicitud.getNuevoEspacio())
                .estado(nuevaSolicitud.getEstado())
                .fechaInicio(nuevaSolicitud.getFechaInicio())
                .fechaFin(nuevaSolicitud.getFechaFin())
                .diaSemana(nuevaSolicitud.getDiaSemana())
                .horaInicio(nuevaSolicitud.getHoraInicio())
                .horaFin(nuevaSolicitud.getHoraFin())
                .comentarioEstado(nuevaSolicitud.getComentarioEstado())
                .comentarioProfesor(nuevaSolicitud.getComentarioProfesor())
                .fechaHoraSolicitud(nuevaSolicitud.getFechaHoraSolicitud())
                .comision(nuevaSolicitud.getComision())
                .build();

        return solicitudRepository.save(actualizada);
    }

    public List<Solicitud> obtenerPorUsuario(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }

    public Page<Solicitud> obtenerHistorialPorUsuario(Long usuarioId, Pageable pageable) {
        return solicitudRepository.findByUsuarioId(usuarioId, pageable);
    }


    public List<Solicitud> obtenerPorEstado(String estado) {
        return solicitudRepository.findByEstado(estado);
    }

    public Page<Solicitud> obtenerSolicitudesPendientes(Pageable pageable) {
        return solicitudRepository.findByEstado("PENDIENTE", pageable);
    }

    public List<Solicitud> obtenerPorEstadoYUsuario(String estado, Long usuarioId) {
        return solicitudRepository.findByEstadoAndUsuarioId(estado, usuarioId);
    }

    public List<Solicitud> obtenerTodasOrdenadasPorFechaHoraSolicitudDesc() {
        return solicitudRepository.findAllByOrderByFechaHoraSolicitudDesc();
    }

    public boolean existePorId(Long id) {
        return solicitudRepository.existsById(id);
    }

    public Solicitud aprobar(Long id) {
        Solicitud solicitud = obtenerPorId(id);

        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new EntityValidationException("Solo se pueden aprobar solicitudes pendientes.");
        }

        solicitud.setEstado(EstadoSolicitud.APROBADA);

        reservaService.generarDesdeSolicitud(solicitud);

        return solicitudRepository.save(solicitud);
    }

    public Solicitud rechazar(Long id, String comentario) {
        Solicitud solicitud = obtenerPorId(id);

        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new EntityValidationException("Solo se pueden rechazar solicitudes pendientes.");
        }

        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setComentarioEstado(comentario);

        return solicitudRepository.save(solicitud);
    }

    public void cancelarSolicitud(Long idSolicitud, Long idUsuarioSolicitante) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con ID: " + idSolicitud));

        if (!solicitud.getUsuario().getId().equals(idUsuarioSolicitante)) {
            throw new EntityValidationException("No tiene permiso para cancelar esta solicitud.");
        }

        if (!"PENDIENTE".equalsIgnoreCase(solicitud.getEstado().toString())) {
            throw new EntityValidationException("Solo se pueden cancelar solicitudes en estado PENDIENTE.");
        }

        solicitud.setEstado(EstadoSolicitud.CANCELADA);
        solicitud.setComentarioEstado("Cancelada por el solicitante.");
        solicitudRepository.save(solicitud);
    }

    public Solicitud solicitarNuevaReserva(Solicitud solicitud) {
        if (solicitud.getNuevoEspacio() == null) {
            throw new IllegalArgumentException("Debe seleccionar un espacio para la nueva reserva.");
        }

        if (solicitud.getReservaOriginal() != null) {
            throw new IllegalArgumentException("Para nuevas reservas, la reserva original debe ser nula.");
        }

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaHoraSolicitud(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }

    public Solicitud solicitarModificacionPorIdReserva(Long reservaId, Solicitud datosNuevos) {
        Reserva reservaOriginal = reservaService.obtenerReserva(reservaId);
        if (reservaOriginal == null) {
            throw new IllegalArgumentException("Reserva original no encontrada con ID: " + reservaId);
        }

        Solicitud solicitud = new Solicitud();
        solicitud.setReservaOriginal(reservaOriginal);
        solicitud.setNuevoEspacio(datosNuevos.getNuevoEspacio());
        solicitud.setFechaInicio(datosNuevos.getFechaInicio());
        solicitud.setFechaFin(datosNuevos.getFechaFin());
        solicitud.setHoraInicio(datosNuevos.getHoraInicio());
        solicitud.setHoraFin(datosNuevos.getHoraFin());
        solicitud.setDiaSemana(datosNuevos.getDiaSemana());
        solicitud.setUsuario(datosNuevos.getUsuario());
        solicitud.setComision(datosNuevos.getComision());
        solicitud.setComentarioProfesor(datosNuevos.getComentarioProfesor());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaHoraSolicitud(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }



}
