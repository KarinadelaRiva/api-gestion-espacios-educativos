package com.apiGestionEspaciosEducativos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "matricula"})
@Builder
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
            nullable = false,
            length = 50
    )
    private String nombre;
    @Column(
            nullable = false,
            length = 50
    )
    private String apellido;
    @Column(
            nullable = false,
            unique = true
    )
    private String matricula;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "usuario_id",
            referencedColumnName = "id",
            unique = true,
            nullable = false
    )
    private Usuario usuario;

    @OneToMany(
            mappedBy = "profesor", // Nombre de la propiedad en la clase Inscripcion que hace referencia a Profesor
            cascade = CascadeType.ALL, // Permite que se eliminen las inscripciones asociadas a el profesor si se elimina el profesor
            fetch = FetchType.EAGER // Cuando se carga el profesor, se cargan todas las inscripciones asociadas a el profesor
    )
    @JoinColumn(
            name = "profesor_id",
            referencedColumnName = "id"
    )
    private List<Inscripcion> inscripciones;

    @OneToMany(
            mappedBy = "profesor", // Nombre de la propiedad en la clase SolicitudCambioAula que hace referencia a Profesor
            cascade = CascadeType.ALL, // Permite que se eliminen las reservas asociadas a el profesor si se elimina el profesor
            fetch = FetchType.EAGER // Cuando se carga el profesor, se cargan todas las solicitudes asociadas a el profesor
    )
    @JoinColumn(
            name = "profesor_id",
            referencedColumnName = "id"
    )
    private List<SolicitudCambioAula> solicitudesCambioAula;


}
