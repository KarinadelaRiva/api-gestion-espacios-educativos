package com.apigestionespacios.apigestionespacios.dtos.comision;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "ID único de la comisión", example = "1")
    @NotNull(message = "El ID de la comisión no puede ser nulo")
    private Long id;


    @Schema(description = "Nombre de la comisión", example = "Comisión A")
    @NotNull(message = "El nombre de la comisión no puede ser nulo")
    private String nombre;

    @Schema(description = "ID de la carrera asociada", example = "5", required = true)
    @NotNull(message = "El ID de la carrera no puede ser nulo")
    private Long carreraId;

    @Schema(description = "ID del profesor responsable", example = "3", required = true)
    @NotNull(message = "El ID del profesor no puede ser nulo")
    private Long profesorId;

    @Schema(description = "Cantidad de alumnos en la comisión", example = "30")
    @NotNull(message = "La cantidad de alumnos no puede ser nula")
    @Min(value = 1, message = "La cantidad de alumnos debe ser al menos 1")
    @Max(value = 100, message = "La cantidad de alumnos no puede ser mayor a 100")
    private Integer cantidadAlumnos;
}
