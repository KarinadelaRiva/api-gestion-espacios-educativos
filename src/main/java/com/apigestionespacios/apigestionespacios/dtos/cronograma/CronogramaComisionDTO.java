package com.apigestionespacios.apigestionespacios.dtos.cronograma;

import com.apigestionespacios.apigestionespacios.dtos.reserva.ReservaResponseDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CronogramaComisionDTO {
    private Long id;
    private String nombre;
    private String asignaturaNombre;
    private String carreraNombre;
    private String profesorNombre;
    private Integer cantidadAlumnos;
    private List<ReservaResponseDTO> reservasVigentes;
}
