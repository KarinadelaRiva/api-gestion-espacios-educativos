package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Solicitud;
import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.entities.enums.EstadoSolicitud;
import com.apigestionespacios.apigestionespacios.entities.enums.TipoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByUsuarioId(Long usuarioId);
    List<Solicitud> findByEstado(EstadoSolicitud estado);
    List<Solicitud> findByTipoSolicitud(TipoSolicitud tipoSolicitud);
    List<Solicitud> findByFechaHoraSolicitudBetween(LocalDateTime inicio, LocalDateTime fin);

    /* Buscar por rango utilizando query
    @Query("SELECT s FROM SolicitudCambioAula s WHERE s.fechaHoraSolicitud BETWEEN :inicio AND :fin")
    List<SolicitudCambioAula> findByFechaHoraSolicitudRango(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    */
}
