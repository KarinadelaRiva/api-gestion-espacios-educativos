package com.apigestionespacios.apigestionespacios.dtos;

import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaUpdateDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long id;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser en el futuro")
    private LocalDate fechaFin;

    private DiaSemana dia;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "Debe especificar un espacio")
    private Long espacioId;


    // Setter que actualiza el día automáticamente si se modifica la fechaInicio
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        if (fechaInicio != null) {
            this.dia = DiaSemana.desdeDayOfWeek(fechaInicio.getDayOfWeek());
        }
    }
}
