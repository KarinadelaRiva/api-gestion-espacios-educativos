package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public List<Reserva> listarReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public Reserva obtenerReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva con ID " + id + " no encontrada."));
    }

    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Reserva actualizarReserva(Reserva reserva) {
        if (reservaRepository.existsById(reserva.getId())) {
            return reservaRepository.save(reserva);
        } else {
            throw new ResourceNotFoundException("Reserva con ID " + reserva.getId() + " no encontrada.");
        }
    }

    public void eliminarReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Reserva con ID " + id + " no encontrada.");
        }
    }

    public void generarDesdeSolicitud(Solicitud solicitud) {
        Reserva reserva = new Reserva();

        reserva.setFechaInicio(solicitud.getFechaInicio());
        reserva.setFechaFin(solicitud.getFechaFin());
        reserva.setDia(solicitud.getDiaSemana());
        reserva.setHoraInicio(solicitud.getHoraInicio());
        reserva.setHoraFin(solicitud.getHoraFin());
        reserva.setEspacio(solicitud.getNuevoEspacio());
        reserva.setComision(solicitud.getComision());

        reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservasPorEspacio(Long espacioId) {
        return reservaRepository.findByEspacioId(espacioId);
    }


}
