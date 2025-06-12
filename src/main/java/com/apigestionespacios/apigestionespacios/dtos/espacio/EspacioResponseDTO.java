package com.apigestionespacios.apigestionespacios.dtos.espacio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EspacioResponseDTO {
    @Schema(description = "ID único del espacio", example = "1")
    private Long id;

    @Schema(description = "Nombre del espacio físico (aula, laboratorio, etc.)", example = "Aula 101")
    private String nombre;

    @Schema(description = "Capacidad máxima de personas en el espacio", example = "40")
    private Integer capacidad;

    @Schema(description = "Indica si el espacio tiene proyector", example = "true")
    private Boolean tieneProyector;

    @Schema(description = "Indica si el espacio tiene TV", example = "false")
    private Boolean tieneTV;

    @Schema(description = "Tipo de espacio: 'AULA' o 'LABORATORIO'", example = "AULA")
    private String tipo;

    @Schema(description = "Cantidad de computadoras (solo para laboratorios, null para aulas)", example = "20")
    private Integer computadoras;
}
