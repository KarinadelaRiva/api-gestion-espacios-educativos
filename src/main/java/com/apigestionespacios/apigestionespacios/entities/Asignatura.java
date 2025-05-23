package com.apigestionespacios.apigestionespacios.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"codigo", "id"})
@Builder
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "nombre_asignatura",
            nullable = false,
            length = 50
    )
    private String nombre;

    @Column(
            nullable = false,
            unique = true
    )
    private Integer codigo;

    @Column(
            nullable = false
    )
    private Boolean requiereLaboratorio;

    @OneToMany(
            mappedBy = "asignatura", // Nombre de la propiedad en la clase Inscripcion que hace referencia a Asignatura
            cascade = CascadeType.ALL, // Permite que se eliminen las inscripciones asociadas a la asignatura
            fetch = FetchType.EAGER // Cuando se carga la asignatura, se cargan todas las inscripciones asociadas a la asignatura
    )
    private List<Inscripcion> inscripciones;
}
