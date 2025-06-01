package com.apigestionespacios.apigestionespacios.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_espacio", discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue("AULA")
public abstract class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String nombre;

    @Column(
            nullable = false
    )
    private Integer capacidad;

    @Column(
            nullable = false
    )
    private Boolean tieneProyector;

    @Column(
            nullable = false
    )
    private Boolean tieneTV;

    @OneToMany(
            mappedBy = "espacio", // Nombre de la propiedad en la clase Reserva que hace referencia a Aula
            cascade = CascadeType.ALL, // Permite que se eliminen las reservas asociadas a el aula si se elimina el aula
            fetch = FetchType.EAGER // Cuando se carga el aula, se cargan todas las reservas asociadas a el aula
    )
    @JsonManagedReference
    private List<Reserva> reservas;

}
