package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.dtos.SolicitudCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.SolicitudResponseDTO;
import com.apigestionespacios.apigestionespacios.entities.*;
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
    private final UsuarioService usuarioService;
    private final EspacioService espacioService;
    private final ComisionService comisionService;

    @Autowired
    public SolicitudService(SolicitudRepository solicitudRepository, ReservaService reservaService, UsuarioService usuarioService, EspacioService espacioService, ComisionService comisionService) {
        this.solicitudRepository = solicitudRepository;
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.espacioService = espacioService;
        this.comisionService = comisionService;
    }

    /**
     * Convierte un DTO de creación de solicitud a una entidad Solicitud.
     *
     * @param dto DTO de creación de solicitud.
     * @return Entidad Solicitud.
     */
    public Solicitud solicitudDTOtoSolicitud(SolicitudCreateDTO dto) {
        Usuario usuario = usuarioService.obtenerPorId(dto.getUsuarioId());
        Espacio nuevoEspacio = espacioService.obtenerPorId(dto.getNuevoEspacioId());
        Comision comision = comisionService.obtenerComisionPorId(dto.getComisionId());
        Reserva reservaOriginal = null;

        if (dto.getReservaOriginalId() != null) {
            reservaOriginal = reservaService.obtenerReserva(dto.getReservaOriginalId());
        }

        return Solicitud.builder()
                .usuario(usuario)
                .nuevoEspacio(nuevoEspacio)
                .reservaOriginal(reservaOriginal)
                .estado(EstadoSolicitud.PENDIENTE)
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .diaSemana(dto.getDiaSemana())
                .horaInicio(dto.getHoraInicio())
                .horaFin(dto.getHoraFin())
                .comentarioProfesor(dto.getComentarioProfesor())
                .fechaHoraSolicitud(LocalDateTime.now())
                .comision(comision)
                .build();
    }

    /**
     * Convierte una entidad Solicitud a un DTO de respuesta SolicitudResponseDTO.
     *
     * @param solicitud la entidad Solicitud a convertir.
     * @return el DTO SolicitudResponseDTO con los datos mapeados de la entidad.
     */
    public SolicitudResponseDTO solicitudToSolicitudResponseDTO(Solicitud solicitud) {
        return SolicitudResponseDTO.builder()
                .id(solicitud.getId())
                .nombreUsuario(solicitud.getUsuario().getNombre())
                .reservaOriginalId(solicitud.getReservaOriginal().getId())
                .nombreNuevoEspacio(solicitud.getNuevoEspacio().getNombre())
                .estado(solicitud.getEstado())
                .fechaInicio(solicitud.getFechaInicio())
                .fechaFin(solicitud.getFechaFin())
                .diaSemana(solicitud.getDiaSemana())
                .horaInicio(solicitud.getHoraInicio())
                .horaFin(solicitud.getHoraFin())
                .comentarioEstado(solicitud.getComentarioEstado())
                .comentarioProfesor(solicitud.getComentarioProfesor())
                .fechaHoraSolicitud(solicitud.getFechaHoraSolicitud())
                .nombreComision(solicitud.getComision().getNombre())
                .nombreAsignatura(solicitud.getComision().getAsignatura().getNombre())
                .nombreDocente(solicitud.getComision().getProfesor().getNombre() + " " + solicitud.getComision().getProfesor().getApellido())
                .build();
    }

    /**
     * Listar todas las solicitudes en formato DTO.
     *
     * @return Lista de solicitudes en formato DTO.
     */
    public List<SolicitudResponseDTO> listarSolicitudesDTO() {
        return listaSolicitudesASolicitudesResponseDTO(solicitudRepository.findAll());
    }

    /**
     * Convierte una lista de solicitudes a una lista de SolicitudResponseDTO.
     *
     * @param solicitudes Lista de solicitudes a convertir.
     * @return Lista de SolicitudResponseDTO.
     */
    public List<SolicitudResponseDTO> listaSolicitudesASolicitudesResponseDTO(List<Solicitud> solicitudes) {
        return solicitudes.stream()
                .map(this::solicitudToSolicitudResponseDTO)
                .toList();
    }

    public Page<Solicitud> obtenerTodasPaginadas(Pageable pageable) {
        return solicitudRepository.findAll(pageable);
    }

    /**
     * Obtiene todas las solicitudes de forma paginada y las convierte en DTOs de respuesta.
     *
     * @param pageable objeto para configurar la paginación.
     * @return página de SolicitudResponseDTO.
     */
    public Page<SolicitudResponseDTO> obtenerTodasPaginadasDTO(Pageable pageable) {
        return solicitudRepository.findAll(pageable)
                .map(this::solicitudToSolicitudResponseDTO);
    }


    public Solicitud obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrado con id: " + id));
    }

    /**
     * Busca una solicitud por ID y la convierte en un DTO de respuesta.
     *
     * @param id el ID de la solicitud a buscar.
     * @return DTO SolicitudResponseDTO correspondiente a la entidad encontrada.
     */
    public SolicitudResponseDTO obtenerPorIdDTO(Long id) {
        Solicitud solicitud = obtenerPorId(id);
        return solicitudToSolicitudResponseDTO(solicitud);
    }

    public Solicitud guardar(Solicitud Solicitud) {
        return solicitudRepository.save(Solicitud);
    }

    /**
     * Guarda una nueva solicitud a partir de un DTO de creación y retorna su DTO de respuesta.
     *
     * @param dto el DTO con los datos para crear la solicitud.
     * @return DTO de respuesta con los datos de la solicitud guardada.
     */
    public SolicitudResponseDTO guardarDTO(SolicitudCreateDTO dto) {
        Solicitud solicitud = solicitudDTOtoSolicitud(dto);
        Solicitud guardada = solicitudRepository.save(solicitud);
        return solicitudToSolicitudResponseDTO(guardada);
    }

    public void eliminar(Long id) {
        solicitudRepository.deleteById(id);
    }

    /**
     * Actualiza una solicitud existente con los datos de un DTO de creación y devuelve su DTO de respuesta.
     *
     * @param id el ID de la solicitud a actualizar.
     * @param dto el DTO con los nuevos datos.
     * @return DTO de respuesta con los datos de la solicitud actualizada.
     */
    public SolicitudResponseDTO actualizarSolicitudDesdeDTO(Long id, SolicitudCreateDTO dto){
        Solicitud existente = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrado con id: " + id));

        Solicitud nuevaSolicitud = solicitudDTOtoSolicitud(dto);

        Solicitud actualizada = existente.toBuilder()
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

        return solicitudToSolicitudResponseDTO(solicitudRepository.save(actualizada));
    }

    public List<Solicitud> obtenerPorUsuario(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Obtiene todas las solicitudes de un usuario específico y las convierte en DTOs de respuesta.
     *
     * @param usuarioId el ID del usuario.
     * @return lista de SolicitudResponseDTO correspondientes al usuario.
     */
    public List<SolicitudResponseDTO> obtenerPorUsuarioDTO(Long usuarioId) {
        List<Solicitud> solicitudes = solicitudRepository.findByUsuarioId(usuarioId);
        return listaSolicitudesASolicitudesResponseDTO(solicitudes);
    }

    public Page<Solicitud> obtenerHistorialPorUsuario(Long usuarioId, Pageable pageable) {
        return solicitudRepository.findByUsuarioId(usuarioId, pageable);
    }

    /**
     * Obtiene el historial paginado de solicitudes de un usuario específico y las convierte en DTOs de respuesta.
     *
     * @param usuarioId el ID del usuario.
     * @param pageable objeto para configurar la paginación.
     * @return página de SolicitudResponseDTO del historial del usuario.
     */
    public Page<SolicitudResponseDTO> obtenerHistorialPorUsuarioDTO(Long usuarioId, Pageable pageable) {
        return solicitudRepository.findByUsuarioId(usuarioId, pageable)
                .map(this::solicitudToSolicitudResponseDTO);
    }


    public List<Solicitud> obtenerPorEstado(String estado) {
        return solicitudRepository.findByEstado(estado);
    }

    /**
     * Obtiene todas las solicitudes con un estado específico y las convierte en DTOs de respuesta.
     *
     * @param estado el estado de las solicitudes a buscar.
     * @return lista de SolicitudResponseDTO con ese estado.
     */
    public List<SolicitudResponseDTO> obtenerPorEstadoDTO(String estado) {
        return solicitudRepository.findByEstado(estado).stream()
                .map(this::solicitudToSolicitudResponseDTO)
                .toList();
    }

    public Page<Solicitud> obtenerSolicitudesPendientes(Pageable pageable) {
        return solicitudRepository.findByEstado("PENDIENTE", pageable);
    }

    /**
     * Obtiene todas las solicitudes pendientes de forma paginada y las convierte en DTOs de respuesta.
     *
     * @param pageable objeto para configurar la paginación.
     * @return página de SolicitudResponseDTO con solicitudes pendientes.
     */
    public Page<SolicitudResponseDTO> obtenerSolicitudesPendientesDTO(Pageable pageable) {
        return solicitudRepository.findByEstado("PENDIENTE", pageable)
                .map(this::solicitudToSolicitudResponseDTO);
    }

    public List<Solicitud> obtenerPorEstadoYUsuario(String estado, Long usuarioId) {
        return solicitudRepository.findByEstadoAndUsuarioId(estado, usuarioId);
    }

    /**
     * Obtiene todas las solicitudes de un usuario con un estado específico y las convierte en DTOs de respuesta.
     *
     * @param estado el estado de las solicitudes.
     * @param usuarioId el ID del usuario.
     * @return lista de SolicitudResponseDTO filtradas por estado y usuario.
     */
    public List<SolicitudResponseDTO> obtenerPorEstadoYUsuarioDTO(String estado, Long usuarioId) {
        return solicitudRepository.findByEstadoAndUsuarioId(estado, usuarioId).stream()
                .map(this::solicitudToSolicitudResponseDTO)
                .toList();
    }

    public List<Solicitud> obtenerTodasOrdenadasPorFechaHoraSolicitudDesc() {
        return solicitudRepository.findAllByOrderByFechaHoraSolicitudDesc();
    }

    /**
     * Obtiene todas las solicitudes ordenadas por fecha y hora descendente, y las convierte en DTOs de respuesta.
     *
     * @return lista de SolicitudResponseDTO ordenadas por fecha y hora de solicitud descendente.
     */
    public List<SolicitudResponseDTO> obtenerTodasOrdenadasPorFechaHoraSolicitudDescDTO() {
        return solicitudRepository.findAllByOrderByFechaHoraSolicitudDesc().stream()
                .map(this::solicitudToSolicitudResponseDTO)
                .toList();
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

    /**
     * Aprueba una solicitud por su ID si está en estado pendiente y devuelve su DTO de respuesta.
     *
     * @param id el ID de la solicitud a aprobar.
     * @return DTO de respuesta con los datos de la solicitud aprobada.
     * @throws EntityValidationException si la solicitud no está en estado PENDIENTE.
     */
    public SolicitudResponseDTO aprobarDTO(Long id) {
        Solicitud solicitud = obtenerPorId(id);

        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new EntityValidationException("Solo se pueden aprobar solicitudes pendientes.");
        }

        solicitud.setEstado(EstadoSolicitud.APROBADA);

        reservaService.generarDesdeSolicitud(solicitud);

        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);

        return solicitudToSolicitudResponseDTO(solicitudGuardada);
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

    /**
     * Rechaza una solicitud en estado PENDIENTE y guarda un comentario con el motivo.
     *
     * @param id        ID de la solicitud a rechazar
     * @param comentario Comentario que justifica el rechazo
     * @return DTO con los datos actualizados de la solicitud rechazada
     * @throws EntityValidationException si la solicitud no está en estado PENDIENTE
     */
    public SolicitudResponseDTO rechazarDTO(Long id, String comentario) {
        Solicitud solicitud = obtenerPorId(id);

        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new EntityValidationException("Solo se pueden rechazar solicitudes pendientes.");
        }

        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setComentarioEstado(comentario);

        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);

        return solicitudToSolicitudResponseDTO(solicitudGuardada);
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



    /**
     * Registra una nueva solicitud de reserva a partir de los datos recibidos.
     * La reserva original debe ser nula y el nuevo espacio no debe ser null.
     *
     * @param solicitud Objeto Solicitud con los datos de la nueva reserva
     * @return DTO con los datos de la solicitud creada
     * @throws IllegalArgumentException si no se especifica un nuevo espacio o si la reserva original no es nula
     */
    public SolicitudResponseDTO solicitarNuevaReservaDTO(Solicitud solicitud) {
        if (solicitud.getNuevoEspacio() == null) {
            throw new IllegalArgumentException("Debe seleccionar un espacio para la nueva reserva.");
        }

        if (solicitud.getReservaOriginal() != null) {
            throw new IllegalArgumentException("Para nuevas reservas, la reserva original debe ser nula.");
        }

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaHoraSolicitud(LocalDateTime.now());

        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);

        return solicitudToSolicitudResponseDTO(solicitudGuardada);
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

    /**
     * Crea una solicitud de modificación sobre una reserva existente.
     * Se utilizan los datos nuevos provistos para generar la solicitud.
     *
     * @param reservaId   ID de la reserva original a modificar
     * @param datosNuevos Objeto Solicitud con los nuevos datos propuestos para la modificación
     * @return DTO con los datos de la solicitud generada
     * @throws IllegalArgumentException si no se encuentra la reserva original
     */
    public SolicitudResponseDTO solicitarModificacionPorIdReservaDTO(Long reservaId, Solicitud datosNuevos) {
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

        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);

        return solicitudToSolicitudResponseDTO(solicitudGuardada);
    }

}
