package com.apiGestionEspaciosEducativos.entities;

import com.apiGestionEspaciosEducativos.entities.enums.BloqueHorario;
import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class SolicitudBloque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudCambioAula solicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DayOfWeek diaSemana;

    @ManyToOne
    @JoinColumn(name = "bloque_horario_id", nullable = false)
    private BloqueHorario bloqueHorario;
}

