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
@EqualsAndHashCode(of = {"id", "nombre"})
@Builder
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "nombre_asignatura",
            nullable = false,
            length = 50
    )
    private String nombre;

    @OneToMany(
            mappedBy = "carrera",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Comision> comisiones;
}
