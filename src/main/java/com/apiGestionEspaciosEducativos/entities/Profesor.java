package com.apiGestionEspaciosEducativos.entities;

import jakarta.persistence.*;

@Entity
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "profesor")
    private Usuario usuario;
} 