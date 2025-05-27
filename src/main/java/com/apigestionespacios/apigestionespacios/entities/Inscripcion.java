package com.apigestionespacios.apigestionespacios.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private LocalDate fechaFinInscripcion;

    @Column(
            nullable = false
    )
    private String comision;

    /**
     * * Relación con la entidad Usuario
     * validar que sea del tipo profesor al dar del alta una inscripción
     */
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "usuario_profesor_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Usuario profesor;

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

    @OneToMany
    (
            mappedBy = "inscripcion", // Nombre de la propiedad en la clase Reserva que hace referencia a Inscripcion
            cascade = CascadeType.ALL, // Permite que se eliminen las reservas asociadas a la inscripción si se elimina la inscripción
            fetch = FetchType.EAGER // Cuando se carga la inscripción, se cargan todas las reservas asociadas a la inscripción
    )
    private List<Solicitud> solicitudes;
}
