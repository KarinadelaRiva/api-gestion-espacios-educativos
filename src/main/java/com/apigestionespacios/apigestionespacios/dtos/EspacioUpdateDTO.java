package com.apigestionespacios.apigestionespacios.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioUpdateDTO {

    @NotNull(message = "El ID del espacio no puede ser nulo")
    private Long id;

    @NotNull(message = "El nombre del espacio no puede ser nulo")
    private String nombre;

    @NotNull(message = "La capacidad del espacio no puede ser nula")
    private Integer capacidad;

    private Boolean tieneProyector;
    private Boolean tieneTV;

    // Solo para laboratorios (puede ser null para aulas)
    private Integer computadoras;

}
