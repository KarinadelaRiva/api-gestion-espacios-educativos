package com.apigestionespacios.apigestionespacios.entities;


import com.apigestionespacios.apigestionespacios.entities.enums.EstadoSolicitud;
import com.apigestionespacios.apigestionespacios.entities.enums.TipoSolicitud;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class SolicitudCambioAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Usuario que realizó la solicitud (conectado a Profesor)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "reserva_original_id", nullable = false)
    private Reserva reservaOriginal;

    @ManyToOne
    @JoinColumn(name = "nueva_aula_id", nullable = false)
    private Aula nuevaAula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_solicitud", nullable = false)
    private TipoSolicitud tipoSolicitud;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "comentario_estado", columnDefinition = "TEXT")
    private String comentarioEstado;

    @Column(name = "comentario_profesor", columnDefinition = "TEXT")
    private String comentarioProfesor;

    @Column(name = "fecha_hora_solicitud", nullable = false)
    private LocalDateTime fechaHoraSolicitud;

    // Días y bloques asociados a la solicitud
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudBloque> bloques;
}

