package com.apigestionespacios.apigestionespacios.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ComisionUpdateDTO {
    @NotNull(message = "El ID de la comisión no puede ser nulo")
    private Long id;

    @NotNull(message = "El nombre de la comisión no puede ser nulo")
    private String nombre;

    @NotNull(message = "El ID de la carrera no puede ser nulo")
    private Long carreraId;

    @NotNull(message = "El ID del profesor no puede ser nulo")
    private Long profesorId;

    @NotNull(message = "La cantidad de alumnos no puede ser nula")
    @Min(value = 1, message = "La cantidad de alumnos debe ser al menos 1")
    @Max(value = 100, message = "La cantidad de alumnos no puede ser mayor a 100")
    private Integer cantidadAlumnos;
}
