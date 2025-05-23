package com.apiGestionEspaciosEducativos.entities;

import com.apiGestionEspaciosEducativos.entities.enums.BloqueHorario;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "reservas_horarios")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "dia", "bloque", "reserva"})
public class ReservaHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dia;

    @Enumerated(EnumType.STRING)
    private BloqueHorario bloque;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
}

