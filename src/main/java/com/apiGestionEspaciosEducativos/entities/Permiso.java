package com.apiGestionEspaciosEducativos.entities;

import com.apiGestionEspaciosEducativos.entities.enums.Permisos;
import jakarta.persistence.*;

@Entity
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Permisos nombre; // tu enum
}

