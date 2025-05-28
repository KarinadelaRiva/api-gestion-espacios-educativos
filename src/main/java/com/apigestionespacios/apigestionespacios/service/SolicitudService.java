package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.repository.SolicitudRepository;
import com.apigestionespacios.apigestionespacios.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    @Autowired
    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public List<Solicitud> obtenerTodos() {
        return solicitudRepository.findAll();
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
                .orElseThrow(() -> new NotFoundException("Solicitud no encontrado con id: " + id));

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

    public List<Solicitud> obtenerPorEstado(String estado) {
        return solicitudRepository.findByEstado(estado);
    }

    public List<Solicitud> obtenerPorEstadoYUsuario(String estado, Long usuarioId) {
        return solicitudRepository.findByEstadoAndUsuarioId(estado, usuarioId);
    }

    public List<Solicitud> obtenerTodasOrdenadasPorFechaDesc() {
        return solicitudRepository.findAllByOrderByFechaDesc();
    }
}
