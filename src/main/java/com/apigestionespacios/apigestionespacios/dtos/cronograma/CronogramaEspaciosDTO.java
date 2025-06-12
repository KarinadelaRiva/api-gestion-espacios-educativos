package com.apigestionespacios.apigestionespacios.dtos.cronograma;

import com.apigestionespacios.apigestionespacios.dtos.reserva.ReservaResponseDTO;
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
