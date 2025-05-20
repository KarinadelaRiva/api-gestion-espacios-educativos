package com.apigestionaulas.apigestionaulas.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laboratorio extends Aula{
    private Integer computadoras;
}
