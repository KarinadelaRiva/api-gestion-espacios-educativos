package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.exceptions.ReservaSolapadaException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;


@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva con ID " + id + " no encontrada."));
    }

    public Page<Reserva> obtenerTodasPaginadas(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    public Reserva crearReserva(Reserva reserva) {
        boolean solapada = existeSolapamiento(reserva);
        if (solapada) {
            throw new ReservaSolapadaException("El espacio ya está reservado en ese horario");
        }
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

    public List<Reserva> obtenerReservasPorProfesor(Long usuarioId) {
        return reservaRepository.findReservasByProfesorId(usuarioId);
    }

    public List<Reserva> obtenerReservasVigentesPorProfesor(Long usuarioId) {
        return reservaRepository.findReservasActualesByProfesorId(usuarioId);
    }

    public boolean existeSolapamiento(Reserva nuevaReserva) {
        List<Reserva> reservasExistentes = reservaRepository
                .findByEspacioIdAndDia(nuevaReserva.getEspacio().getId(), nuevaReserva.getDia().toString());

        for (Reserva existente : reservasExistentes) {
            boolean fechasSeSolapan =
                    !(nuevaReserva.getFechaFin().isBefore(existente.getFechaInicio()) ||
                            nuevaReserva.getFechaInicio().isAfter(existente.getFechaFin()));

            boolean horasSeSolapan =
                    !(nuevaReserva.getHoraFin().isBefore(existente.getHoraInicio()) ||
                            nuevaReserva.getHoraInicio().isAfter(existente.getHoraFin()));

            if (fechasSeSolapan && horasSeSolapan) {
                return true; // conflicto detectado
            }
        }

        return false;
    }

    public Map<Espacio, List<Reserva>> obtenerReservasPorDiaAgrupadas(LocalDate fecha) {
        List<Reserva> reservas = reservaRepository.findByFechaInicioOrderByEspacioIdAscHoraInicioAsc(fecha);
        return reservas.stream().collect(Collectors.groupingBy(Reserva::getEspacio, LinkedHashMap::new, Collectors.toList()));
    }



}
