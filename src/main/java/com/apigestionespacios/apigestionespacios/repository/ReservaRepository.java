package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByComisionId(Long inscripcionId);
    List<Reserva> findByEspacioId(Long espacioId);
}
