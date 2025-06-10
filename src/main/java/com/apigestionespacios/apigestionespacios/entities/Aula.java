package com.apigestionespacios.apigestionespacios.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@DiscriminatorValue("AULA")
public class Aula extends Espacio{
}
