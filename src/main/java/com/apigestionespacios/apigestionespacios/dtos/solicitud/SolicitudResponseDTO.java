package com.apigestionespacios.apigestionespacios.dtos.solicitud;

import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import com.apigestionespacios.apigestionespacios.entities.enums.EstadoSolicitud;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SolicitudResponseDTO {
    @Schema(description = "ID único de la solicitud", example = "2001")
    private Long id;

    @Schema(description = "Nombre del usuario que realiza la solicitud", example = "Ana Gómez")
    private String nombreUsuario;

    @Schema(description = "ID de la reserva original asociada (si aplica)", example = "1001")
    private Long reservaOriginalId;

    @Schema(description = "Nombre del nuevo espacio solicitado (si aplica)", example = "Aula 102")
    private String nombreNuevoEspacio;

    @Schema(description = "Estado actual de la solicitud", example = "PENDIENTE")
    private EstadoSolicitud estado;

    @Schema(description = "Fecha de inicio solicitada", example = "2025-06-15")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin solicitada", example = "2025-06-20")
    private LocalDate fechaFin;

    @Schema(description = "Día de la semana solicitado", example = "LUNES")
    private DiaSemana diaSemana;

    @Schema(description = "Hora de inicio solicitada", example = "08:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora de fin solicitada", example = "10:00")
    private LocalTime horaFin;

    @Schema(description = "Comentario sobre el estado de la solicitud", example = "Aprobada por el administrador")
    private String comentarioEstado;

    @Schema(description = "Comentario del profesor solicitante", example = "Necesito un aula con proyector")
    private String comentarioProfesor;

    @Schema(description = "Fecha y hora en que se realizó la solicitud", example = "2025-06-10T14:30:00")
    private LocalDateTime fechaHoraSolicitud;

    @Schema(description = "Nombre de la comisión asociada", example = "Comisión 8")
    private String nombreComision;

    @Schema(description = "Nombre de la asignatura asociada", example = "Matemática I")
    private String nombreAsignatura;

    @Schema(description = "Nombre del docente responsable", example = "Juan Pérez")
    private String nombreDocente;

}