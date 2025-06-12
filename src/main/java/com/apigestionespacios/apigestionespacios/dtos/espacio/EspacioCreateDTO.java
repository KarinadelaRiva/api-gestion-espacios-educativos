package com.apigestionespacios.apigestionespacios.dtos.espacio;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioCreateDTO {

    @NotNull(message = "El nombre del espacio no puede ser nulo")
    private String nombre;

    @NotNull(message = "La capacidad del espacio no puede ser nula")
    private Integer capacidad;

    private Boolean tieneProyector;
    private Boolean tieneTV;

    // Este campo es clave para decidir qué tipo de espacio crear
    @NotNull(message = "El tipo de espacio no puede ser nulo")
    private String tipo; // "AULA" o "LABORATORIO"

    // Solo para laboratorios (puede ser null para aulas)
    private Integer computadoras;
}
