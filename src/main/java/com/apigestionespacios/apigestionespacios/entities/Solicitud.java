package com.apigestionespacios.apigestionespacios.entities;


import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import com.apigestionespacios.apigestionespacios.entities.enums.EstadoSolicitud;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "reserva_original_id", nullable = true)
    private Reserva reservaOriginal;

    @ManyToOne
    @JoinColumn(name = "nuevo_espacio_id", nullable = false)
    @JsonBackReference
    private Espacio nuevoEspacio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @Column(name = "comentario_estado", columnDefinition = "TEXT")
    private String comentarioEstado;

    @Column(name = "comentario_profesor", columnDefinition = "TEXT")
    private String comentarioProfesor;

    @Column(name = "fecha_hora_solicitud", nullable = false)
    private LocalDateTime fechaHoraSolicitud;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "comision_id", nullable = false)
    private Comision comision;

}

