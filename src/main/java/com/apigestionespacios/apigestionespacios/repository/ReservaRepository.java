package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import com.apigestionespacios.apigestionespacios.entities.enums.DiaSemana;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByComisionId(Long inscripcionId);
    List<Reserva> findByEspacioId(Long espacioId);
    Page<Reserva> findAll(Pageable pageable);

    List<Reserva> findByFechaInicio(LocalDate fecha, Sort sort);

    List<Reserva> findByEspacioIdAndDia(Long espacioId, DiaSemana dia);

    /**
     * Busca reservas por fecha.
     *
     * @param fechaConsulta Fecha a consultar.
     * @return Lista de reservas que coinciden con la fecha
     */
    @Query("""
    SELECT r FROM Reserva r
    WHERE :fechaConsulta BETWEEN r.fechaInicio AND r.fechaFin
    ORDER BY r.espacio.id ASC, r.horaInicio ASC
    """)
    List<Reserva> findReservasPorFecha(
            @Param("fechaConsulta") LocalDate fechaConsulta);

    /**
     * Busca reservas por el ID del profesor asociado a la comisión.
     *
     * @param usuarioId ID del profesor.
     * @return Lista de reservas asociadas al profesor.
     */
    @Query("SELECT r FROM Reserva r WHERE r.comision.profesor.id = :usuarioId")
    List<Reserva> findReservasByProfesorId(@Param("usuarioId") Long usuarioId);

    /**
     * Busca reservas actuales (no finalizadas) por el ID del profesor asociado a la comisión.
     *
     * @param usuarioId ID del profesor.
     * @return Lista de reservas actuales asociadas al profesor.
     */
    @Query("""
    SELECT r FROM Reserva r
    WHERE r.comision.profesor.id = :usuarioId
    AND (
        r.fechaFin > CURRENT_DATE
        OR (r.fechaFin = CURRENT_DATE AND r.horaFin > CURRENT_TIME)
    )
    """)
    List<Reserva> findReservasActualesByProfesorId(@Param("usuarioId") Long usuarioId);

    /**
     * Obtiene las reservas activas de una comisión, es decir, aquellas cuya fecha de inicio
     * es anterior o igual a la fecha actual y cuya fecha de fin es posterior o igual a la actual.
     *
     * Las reservas se ordenan primero por día de la semana (de lunes a domingo), y luego por hora de inicio y fin.
     *
     * @param comisionId ID de la comisión para la cual se buscan las reservas.
     * @return Lista de reservas activas ordenadas cronológicamente por día y horario.
     */
    @Query(value = """
    SELECT *
    FROM reserva
    WHERE comision_id = :comisionId
      AND fecha_inicio <= CURRENT_DATE
      AND fecha_fin >= CURRENT_DATE
    ORDER BY 
      CASE dia
        WHEN 'LUNES' THEN 1
        WHEN 'MARTES' THEN 2
        WHEN 'MIERCOLES' THEN 3
        WHEN 'JUEVES' THEN 4
        WHEN 'VIERNES' THEN 5
        WHEN 'SABADO' THEN 6
        WHEN 'DOMINGO' THEN 7
      END,
      hora_inicio ASC,
      hora_fin ASC
    """, nativeQuery = true)
    List<Reserva> findReservasActivasPorComisionOrdenadas(
            @Param("comisionId") Long comisionId);

}
