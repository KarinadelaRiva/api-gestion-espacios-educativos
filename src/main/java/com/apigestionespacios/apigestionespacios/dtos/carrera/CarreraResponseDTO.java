package com.apigestionespacios.apigestionespacios.dtos.carrera;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CarreraResponseDTO {
    @Schema(description = "ID único de la carrera", example = "1")
    private Long id;

    @Schema(description = "Nombre de la carrera", example = "Ingeniería en Sistemas")
    private String nombre;

    @Schema(description = "Lista de nombres de asignaturas asociadas a la carrera", example = "[\"Matemática I\", \"Programación I\"]")
    private List<String> asignaturas;
}
