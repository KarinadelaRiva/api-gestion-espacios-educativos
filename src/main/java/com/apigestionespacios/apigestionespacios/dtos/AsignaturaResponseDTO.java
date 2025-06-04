package com.apigestionespacios.apigestionespacios.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AsignaturaResponseDTO {
    private Long id;
    private String nombre;
    private Integer codigo;
    private Boolean requiereLaboratorio;
    private Integer cantidadComisiones;
    private List<String> NombreCarreras;

}
