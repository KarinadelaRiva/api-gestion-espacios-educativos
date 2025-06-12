package com.apigestionespacios.apigestionespacios.dtos.reserva;

import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaUpdateDTO {

    @Schema(description = "ID único de la reserva", example = "1001")
    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long id;

    @Schema(description = "Fecha de inicio de la reserva", example = "2025-06-15")
    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin de la reserva", example = "2025-06-20")
    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser en el futuro")
    private LocalDate fechaFin;

    @Schema(description = "Día de la semana de la reserva", example = "LUNES")
    private DiaSemana dia;

    @Schema(description = "Hora de inicio de la reserva", example = "08:00")
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @Schema(description = "Hora de fin de la reserva", example = "10:00")
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @Schema(description = "Id del espacio asociado", example = "15")
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
