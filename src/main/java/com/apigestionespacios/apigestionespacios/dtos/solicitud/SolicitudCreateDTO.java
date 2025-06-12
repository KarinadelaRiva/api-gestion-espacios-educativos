package com.apigestionespacios.apigestionespacios.dtos.solicitud;

import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SolicitudCreateDTO {

    @NotNull(message = "Debe especificar el ID del usuario")
    private Long usuarioId;

    private Long reservaOriginalId; // puede ser null

    @NotNull(message = "Debe especificar el ID del nuevo espacio")
    private Long nuevoEspacioId;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser en el futuro")
    private LocalDate fechaFin;

    @NotNull(message = "Debe especificar el día de la semana")
    private DiaSemana diaSemana;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    private String comentarioProfesor;

    @NotNull(message = "Debe especificar la comisión")
    private Long comisionId;

}