package com.apigestionespacios.apigestionespacios.dtos.reserva;


import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaResponseDTO {
    @Schema(description = "ID único de la reserva", example = "1001")
    private Long id;

    @Schema(description = "Fecha de inicio de la reserva", example = "2025-06-15")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin de la reserva", example = "2025-06-20")
    private LocalDate fechaFin;

    @Schema(description = "Día de la semana de la reserva", example = "LUNES")
    private DiaSemana dia;

    @Schema(description = "Hora de inicio de la reserva", example = "08:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora de fin de la reserva", example = "10:00")
    private LocalTime horaFin;

    @Schema(description = "Nombre del espacio reservado", example = "Aula 101")
    private String nombreEspacio;

    @Schema(description = "Nombre de la comisión asociada", example = "Comisión 8")
    private String nombreComision;

    @Schema(description = "Nombre de la asignatura asociada", example = "Matemática I")
    private String nombreAsignatura;

    @Schema(description = "Nombre del docente responsable", example = "Juan Pérez")
    private String nombreDocente;
}
