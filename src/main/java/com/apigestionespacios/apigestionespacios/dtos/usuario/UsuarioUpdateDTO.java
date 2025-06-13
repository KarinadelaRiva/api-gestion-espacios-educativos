package com.apigestionespacios.apigestionespacios.dtos.usuario;

import com.apigestionespacios.apigestionespacios.entities.enums.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioUpdateDTO {

    @Schema(description = "Nombre de usuario actual para confirmar update", example = "daniel123")
    @NotBlank(message = "Debe ingresar su username actual")
    private String currentUsername;

    @Schema(description = "Contraseña de usuario actual para confirmar update", example = "1234Abcd!")
    @NotBlank(message = "Debe ingresar su contraseña actual")
    private String currentPassword;

    @Schema(description = "Nombre del usuario", example = "Daniel")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener máximo 50 caracteres")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Diaz")
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido debe tener máximo 50 caracteres")
    private String apellido;

    @Schema(description = "Nombre de usuario para login", example = "daniel123")
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 50, message = "El username debe tener máximo 50 caracteres")
    private String username;

    @Schema(description = "Contraseña para el usuario", example = "1234Abcd!")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;  // puede ser opcional para update
}
