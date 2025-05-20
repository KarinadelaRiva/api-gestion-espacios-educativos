package com.apigestionaulas.apigestionaulas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private Integer numero;

    @Column(
            nullable = false
    )
    private Integer capacidad;
    private Boolean tieneProyector;
    private Boolean tieneTV;

    @OneToMany(
            mappedBy = "aula", // Nombre de la propiedad en la clase Reserva que hace referencia a Aula
            cascade = CascadeType.ALL, // Permite que se eliminen las reservas asociadas a el aula si se elimina el aula
            fetch = FetchType.EAGER // Cuando se carga el aula, se cargan todas las reservas asociadas a el aula
    )
    private List<Reserva> reservas;


}
