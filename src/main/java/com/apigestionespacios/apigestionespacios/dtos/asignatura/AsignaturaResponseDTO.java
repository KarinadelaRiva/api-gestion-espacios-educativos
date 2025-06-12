package com.apigestionespacios.apigestionespacios.dtos.asignatura;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AsignaturaResponseDTO {
    @Schema(description = "ID único de la asignatura", example = "1")
    private Long id;

    @Schema(description = "Nombre de la asignatura", example = "Matemática I")
    private String nombre;

    @Schema(description = "Código numérico de la asignatura", example = "101")
    private Integer codigo;

    @Schema(description = "Indica si la asignatura requiere laboratorio", example = "true")
    private Boolean requiereLaboratorio;

    @Schema(description = "Cantidad de comisiones asociadas a la asignatura", example = "3")
    private Integer cantidadComisiones;

    @Schema(description = "Lista de nombres de carreras asociadas a la asignatura", example = "[\"Ingeniería en Sistemas\", \"Licenciatura en Matemática\"]")
    private List<String> NombreCarreras;

}
