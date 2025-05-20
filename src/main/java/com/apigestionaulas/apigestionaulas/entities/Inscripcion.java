package com.apigestionaulas.apigestionaulas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false
    )
    private Integer cantidadAlumnos;
    private Integer margenAlumnos;
    private LocalDate fechaFinInscripcion;

    @Column(
            nullable = false
    )
    private String comision;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "profesor_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Profesor profesor;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "asignatura_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Asignatura asignatura;
}
