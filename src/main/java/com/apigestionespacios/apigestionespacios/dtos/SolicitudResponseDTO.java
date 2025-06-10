package com.apigestionespacios.apigestionespacios.dtos;

import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import com.apigestionespacios.apigestionespacios.entities.enums.EstadoSolicitud;
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
    private Long id;
    private String nombreUsuario;
    private Long reservaOriginalId;
    private String nombreNuevoEspacio;
    private EstadoSolicitud estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String comentarioEstado;
    private String comentarioProfesor;
    private LocalDateTime fechaHoraSolicitud;
    private String nombreComision;
    private String nombreAsignatura;
    private String nombreDocente;

}