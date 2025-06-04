package com.apigestionespacios.apigestionespacios.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AsignaturaUpdateDTO {

    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Long id;

    @NotNull(message = "El nombre de la asignatura es obligatorio")
    private String nombre;

    private Boolean requiereLaboratorio;
}
