package com.apigestionespacios.apigestionespacios.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"profesor", "asignatura"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Comision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false
    )
    private Integer cantidadAlumnos;

    /**
     * * Relación con la entidad Usuario
     * validar que sea del tipo profesor al dar del alta una inscripción
     */
    @ManyToOne(
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
            mappedBy = "comision", // Nombre de la propiedad en la clase Reserva que hace referencia a Comision
            cascade = CascadeType.ALL, // Permite que se eliminen las reservas asociadas a la inscripción si se elimina la inscripción
            fetch = FetchType.EAGER // Cuando se carga la inscripción, se cargan todas las reservas asociadas a la inscripción
    )
    private List<Solicitud> solicitudes;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false // No se permite que la carrera sea nula, por lo tanto, no es opcional
    )
    @JoinColumn(
            name = "carrera_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Carrera carrera;
}


