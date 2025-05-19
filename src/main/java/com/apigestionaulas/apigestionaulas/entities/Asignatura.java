package com.apigestionaulas.apigestionaulas.entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asignatura {
    private Integer id;
    private String nombre;
    private int codigo;
    boolean requiereLaboratorio;
}
