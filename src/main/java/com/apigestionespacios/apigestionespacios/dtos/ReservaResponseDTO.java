package com.apigestionespacios.apigestionespacios.dtos;


import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaResponseDTO {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private DiaSemana dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String nombreEspacio;
    private String nombreComision;
    private String nombreAsignatura;
    private String nombreDocente;
}
