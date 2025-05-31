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
    private Boolean requiereLaboratorio = false;

    @ManyToMany(mappedBy = "asignaturas")
    private List<Carrera> carreras;

    @OneToMany(
            mappedBy = "asignatura", // Nombre de la propiedad en la clase Comision que hace referencia a Asignatura
            cascade = CascadeType.ALL, // Permite que se eliminen las comisiones asociadas a la asignatura
            fetch = FetchType.EAGER // Cuando se carga la comision, se cargan todas las comisiones asociadas a la asignatura
    )
    private List<Comision> comisiones;
}
