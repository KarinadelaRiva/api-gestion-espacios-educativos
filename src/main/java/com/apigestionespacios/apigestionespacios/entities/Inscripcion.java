package com.apigestionespacios.apigestionespacios.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(exclude = {"profesor", "asignatura"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "asignatura", "comision", "profesor"})
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false
    )
    private Integer cantidadAlumnos;

    @Column(
            nullable = false
    )
    private Integer margenAlumnos;

    @Column(
            nullable = false
    )
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
