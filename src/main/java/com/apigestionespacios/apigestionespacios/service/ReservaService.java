package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.dtos.CronogramaEspaciosDTO;
import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.exceptions.ReservaSolapadaException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;


@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    /**
     * Listar todas las reservas.
     *
     * @return Lista de reservas.
     */
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    /**
     * Obtener una reserva por su ID.
     *
     * @param id ID de la reserva.
     * @return Reserva encontrada.
     * @throws ResourceNotFoundException si no se encuentra la reserva.
     */
    public Reserva obtenerReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva con ID " + id + " no encontrada."));
    }

    /**
     * Obtener todas las reservas paginadas.
     *
     * @param pageable Información de paginación.
     * @return Página de reservas.
     */
    public Page<Reserva> obtenerTodasPaginadas(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    /**
     * Crear una nueva reserva.
     *
     * @param reserva Reserva a crear.
     * @return Reserva creada.
     * @throws ReservaSolapadaException si la reserva se solapa con otra existente.
     */
    public Reserva crearReserva(Reserva reserva) {
        boolean solapada = existeSolapamiento(reserva);
        if (solapada) {
            throw new ReservaSolapadaException("El espacio ya está reservado en ese horario");
        }
        return reservaRepository.save(reserva);
    }

    /**
     * Actualizar una reserva existente.
     *
     * @param reserva Reserva a actualizar.
     * @return Reserva actualizada.
     * @throws ResourceNotFoundException si no se encuentra la reserva.
     * @throws ReservaSolapadaException si la reserva se solapa con otra existente.
     */
    public Reserva actualizarReserva(Reserva reserva) {
        if (reservaRepository.existsById(reserva.getId())) {
            boolean solapada = existeSolapamiento(reserva);
            if (solapada) {
                throw new ReservaSolapadaException("El espacio ya está reservado en ese horario");
            }
            return reservaRepository.save(reserva);
        } else {
            throw new ResourceNotFoundException("Reserva con ID " + reserva.getId() + " no encontrada.");
        }
    }

    /**
     * Eliminar una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar.
     * @throws ResourceNotFoundException si no se encuentra la reserva.
     */
    public void eliminarReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Reserva con ID " + id + " no encontrada.");
        }
    }

    /**
     * Generar una reserva a partir de una solicitud.
     *
     * @param solicitud Solicitud que contiene los datos para la reserva.
     */
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

    /**
     * Listar reservas por espacio.
     *
     * @param espacioId ID del espacio.
     * @return Lista de reservas asociadas al espacio.
     */
    public List<Reserva> listarReservasPorEspacio(Long espacioId) {
        return reservaRepository.findByEspacioId(espacioId);
    }

    public List<Reserva> obtenerTodasOrdenadas() {
        return reservaRepository.findAll(Sort.by("fechaInicio").ascending().and(Sort.by("horaInicio").ascending()));
    }

    public List<Reserva> obtenerReservasPorFecha(LocalDate fecha) {
        return reservaRepository.findByFechaInicio(fecha, Sort.by("horaInicio").ascending());
    }


    /**
     * Obtener reservas vigentes por Profesor asignado a la comision.
     * @param usuarioId
     * @return Lista de reservas asociadas al profesor.
     */
    public List<Reserva> obtenerReservasPorProfesor(Long usuarioId) {
        return reservaRepository.findReservasByProfesorId(usuarioId);
    }

    /**
     * Obtener reservas vigentes por Profesor asignado a la comision.
     * @param usuarioId
     * @return Lista de reservas vigentes asociadas al profesor.
     */
    public List<Reserva> obtenerReservasVigentesPorProfesor(Long usuarioId) {
        return reservaRepository.findReservasActualesByProfesorId(usuarioId);
    }

    /**
     * Verifica si una nueva reserva se solapa con reservas existentes.
     *
     * @param nuevaReserva Reserva a verificar.
     * @return true si hay solapamiento, false en caso contrario.
     */
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


    /**
     * Obtiene un cronograma diario de reservas agrupadas por espacio.
     *
     * @return Mapa donde la clave es el nombre del espacio y el valor es una lista de reservas.
     */
    public Map<Espacio, List<Reserva>> obtenerCronogramaDiario(LocalDate fecha) {

        List<Reserva> reservas = reservaRepository.findReservasPorFecha(fecha);

        return reservas.stream()
                .collect(Collectors.groupingBy(Reserva::getEspacio, LinkedHashMap::new, Collectors.toList()));
    }

    public List<CronogramaEspaciosDTO> obtenerCronogramaDTOParaFecha(LocalDate fecha) {

        List<Reserva> reservas = reservaRepository.findReservasPorFecha(fecha);

        return reservas.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getEspacio().getId(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .entrySet().stream()
                .map(entry -> {
                    Reserva primeraReserva = entry.getValue().get(0); // Para obtener el nombre del espacio
                    return CronogramaEspaciosDTO.builder()
                            .espacioId(entry.getKey())
                            .nombre(primeraReserva.getEspacio().getNombre())
                            .reservas(entry.getValue())
                            .build();
                })
                .collect(Collectors.toList());
    }


}
