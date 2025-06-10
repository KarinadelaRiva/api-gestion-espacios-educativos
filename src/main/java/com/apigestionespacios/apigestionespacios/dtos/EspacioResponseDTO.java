package com.apigestionespacios.apigestionespacios.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EspacioResponseDTO {
    private Long id;
    private String nombre;
    private Integer capacidad;
    private Boolean tieneProyector;
    private Boolean tieneTV;
    private String tipo; // "AULA" o "LABORATORIO"

    // Campo solo presente si es Laboratorio
    private Integer computadoras;
}
