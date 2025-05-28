package com.apigestionespacios.apigestionespacios.entities;

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
public class Laboratorio extends Espacio {
    @Column(
            name = "cantidad_computadoras"
    )
    private Integer computadoras;
}
