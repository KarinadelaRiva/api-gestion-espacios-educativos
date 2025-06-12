package com.apigestionespacios.apigestionespacios.dtos.carrera;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CarreraCreateDTO {

    @Schema(description = "Nombre de la carrera", example = "Ingeniería en Sistemas")
    @NotNull(message = "El nombre de la carrera es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

}