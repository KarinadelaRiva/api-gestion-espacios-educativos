package com.apigestionespacios.apigestionespacios.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {
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
            length = 50
    )
    private String username;

    @Column(
            nullable = false,
            length = 50
    )
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToMany(
            mappedBy = "profesor", // Nombre de la propiedad en la clase Inscripcion que hace referencia a Usuario
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Inscripcion> inscripciones;
} 