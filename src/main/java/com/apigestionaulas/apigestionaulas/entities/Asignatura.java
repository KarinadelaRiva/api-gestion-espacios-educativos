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
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private int codigo;

    @Column(
            nullable = false
    )
    private boolean requiereLaboratorio;

    @OneToMany(
            mappedBy = "asignatura", // Nombre de la propiedad en la clase Inscripcion que hace referencia a Asignatura
            cascade = CascadeType.ALL, // Permite que se eliminen las inscripciones asociadas a la asignatura
            fetch = FetchType.LAZY // Carga perezosa para evitar cargar todas las inscripciones al cargar la asignatura
    )
    private List<Inscripcion> inscripciones;
}
