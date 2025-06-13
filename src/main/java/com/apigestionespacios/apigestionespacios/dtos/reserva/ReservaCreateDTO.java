package com.apigestionespacios.apigestionespacios.dtos.reserva;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReservaCreateDTO {

    @Schema(description = "Fecha de inicio de la reserva", example = "2025-06-15")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin de la reserva", example = "2025-06-20")
    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser en el futuro")
    private LocalDate fechaFin;

    @Schema(description = "Hora de inicio de la reserva", example = "08:00")
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @Schema(description = "Hora de fin de la reserva", example = "10:00")
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @Schema(description = "Id del espacio asociado", example = "15")
    @NotNull(message = "Debe especificar un espacio")
    private Long espacioId;

    @Schema(description = "Id de la comisión asociada", example = "8")
    @NotNull(message = "Debe especificar una comisión")
    private Long comisionId;
}

