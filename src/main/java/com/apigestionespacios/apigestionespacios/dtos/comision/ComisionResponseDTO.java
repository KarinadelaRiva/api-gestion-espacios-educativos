package com.apigestionespacios.apigestionespacios.dtos.comision;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ComisionResponseDTO {
    private Long id;
    private String nombre;
    private String asignaturaNombre;
    private String carreraNombre;
    private String profesorNombre;
    private Integer cantidadAlumnos;
}
