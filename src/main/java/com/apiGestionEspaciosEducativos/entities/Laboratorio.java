package com.apiGestionEspaciosEducativos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("LABORATORIO")
public class Laboratorio extends Aula {
    @Column(
            name = "cantidad_computadoras"
    )
    private Integer computadoras;
}
