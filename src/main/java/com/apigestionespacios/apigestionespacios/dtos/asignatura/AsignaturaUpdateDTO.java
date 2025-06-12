package com.apigestionespacios.apigestionespacios.dtos.asignatura;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AsignaturaUpdateDTO {

    @Schema(description = "ID único de la asignatura", example = "1")
    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Long id;

    @Schema(description = "Nombre de la asignatura", example = "Matemática I")
    @NotNull(message = "El nombre de la asignatura es obligatorio")
    private String nombre;

    @Schema(description = "Indica si la asignatura requiere laboratorio", example = "true")
    private Boolean requiereLaboratorio;
}
