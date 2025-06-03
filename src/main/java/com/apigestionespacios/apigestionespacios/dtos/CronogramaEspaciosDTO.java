package com.apigestionespacios.apigestionespacios.dtos;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CronogramaEspaciosDTO {
    private String nombreEspacio;
    private List<ReservaResponseDTO> reservas;
}
