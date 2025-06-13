package com.apigestionespacios.apigestionespacios.entities;

import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "espacio", "comision"})
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name= "dia", nullable = false)
    private DiaSemana dia;

    @Column
    private LocalTime horaInicio;

    @Column
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aula_id", nullable = false)
    @JsonBackReference
    private Espacio espacio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comision_id", nullable = false)
    private Comision comision;

}