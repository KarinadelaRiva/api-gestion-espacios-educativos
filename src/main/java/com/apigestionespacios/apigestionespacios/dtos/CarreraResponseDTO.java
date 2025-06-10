package com.apigestionespacios.apigestionespacios.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CarreraResponseDTO {
    private Long id;
    private String nombre;
    private List<String> asignaturas;
}
