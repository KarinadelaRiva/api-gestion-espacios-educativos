package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<Reserva> findByEspacioIdAndDia(Long espacioId, String dia);
    ;
    List<Reserva> findByFechaInicioOrderByEspacioIdAscHoraInicioAsc(LocalDate fechaInicio);


    @Query("SELECT r FROM Reserva r WHERE r.comision.profesor.id = :usuarioId")
    List<Reserva> findReservasByProfesorId(@Param("usuarioId") Long usuarioId);

    @Query("""
    SELECT r FROM Reserva r
    WHERE r.comision.profesor.id = :usuarioId
    AND (
        r.fechaFin > CURRENT_DATE
        OR (r.fechaFin = CURRENT_DATE AND r.horaFin > CURRENT_TIME)
    )
    """)
    List<Reserva> findReservasActualesByProfesorId(@Param("usuarioId") Long usuarioId);


}
