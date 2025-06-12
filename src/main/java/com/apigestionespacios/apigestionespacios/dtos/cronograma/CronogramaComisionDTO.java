package com.apigestionespacios.apigestionespacios.dtos.cronograma;

import com.apigestionespacios.apigestionespacios.dtos.reserva.ReservaResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CronogramaComisionDTO {
    @Schema(description = "ID único de la comisión", example = "1")
    private Long id;

    @Schema(description = "Nombre de la comisión", example = "Comisión A")
    private String nombre;

    @Schema(description = "Nombre de la asignatura asociada", example = "Matemática I")
    private String asignaturaNombre;

    @Schema(description = "Nombre de la carrera asociada", example = "Ingeniería en Sistemas")
    private String carreraNombre;

    @Schema(description = "Nombre del profesor responsable", example = "Juan Pérez")
    private String profesorNombre;

    @Schema(description = "Cantidad de alumnos en la comisión", example = "30")
    private Integer cantidadAlumnos;

    @Schema(description = "Lista de reservas vigentes asociadas a la comisión")
    private List<ReservaResponseDTO> reservasVigentes;
}
