package com.apigestionespacios.apigestionespacios.repository;


import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByUsuarioId(Long usuarioId);

    List<Solicitud> findByEstado(String estado);

    List<Solicitud> findByEstadoAndUsuarioId(String estado, Long usuarioId);

    List<Solicitud> findAllByOrderByFechaHoraSolicitudDesc();
}