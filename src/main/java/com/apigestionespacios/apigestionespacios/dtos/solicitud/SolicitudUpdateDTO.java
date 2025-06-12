package com.apigestionespacios.apigestionespacios.dtos.solicitud;

import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SolicitudUpdateDTO {

    @NotNull(message = "El ID de la solicitud es obligatorio")
    private Long id;

    @NotNull(message = "Debe especificar un nuevo espacio")
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

    @NotNull(message = "Debe especificar una comisión")
    private Long comisionId;

}