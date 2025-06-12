package com.apigestionespacios.apigestionespacios.dtos.asignatura;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AsignaturaCreateDTO {

    @Schema(description = "Nombre de la asignatura", example = "Matemática I")
    @NotNull(message = "El nombre de la asignatura es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @Schema(description = "Código numérico de la asignatura", example = "101")
    @NotNull(message = "El código de la asignatura es obligatorio")
    private Integer codigo;

    @Schema(description = "Indica si la asignatura requiere laboratorio", example = "true")
    private Boolean requiereLaboratorio;
}
