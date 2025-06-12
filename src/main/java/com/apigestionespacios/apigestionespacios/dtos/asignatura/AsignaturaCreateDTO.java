package com.apigestionespacios.apigestionespacios.dtos.asignatura;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AsignaturaCreateDTO {

    @NotNull(message = "El nombre de la asignatura es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotNull(message = "El código de la asignatura es obligatorio")
    private Integer codigo;

    private Boolean requiereLaboratorio;
}
