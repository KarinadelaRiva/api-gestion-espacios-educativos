package com.apigestionespacios.apigestionespacios.dtos.carrera;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CarreraUpdateDTO {

    @NotNull(message = "El ID de la carrera no puede ser nulo")
    private Long id;

    @NotNull(message = "El nombre de la carrera es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;
}
