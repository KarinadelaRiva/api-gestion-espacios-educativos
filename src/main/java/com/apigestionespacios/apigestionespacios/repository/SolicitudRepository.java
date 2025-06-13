package com.apigestionespacios.apigestionespacios.repository;


import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.entities.enums.EstadoSolicitud;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByUsuarioId(Long usuarioId);
    Page<Solicitud> findByUsuarioId(Long usuarioId, Pageable pageable);


    List<Solicitud> findByEstado(String estado);
    Page<Solicitud> findByEstado(EstadoSolicitud estado, Pageable pageable);

    List<Solicitud> findByEstadoAndUsuarioId(String estado, Long usuarioId);

    List<Solicitud> findAllByOrderByFechaHoraSolicitudDesc();
}