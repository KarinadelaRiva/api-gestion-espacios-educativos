package com.apigestionespacios.apigestionespacios.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@DiscriminatorValue("AULA")
public class Aula extends Espacio{
}
