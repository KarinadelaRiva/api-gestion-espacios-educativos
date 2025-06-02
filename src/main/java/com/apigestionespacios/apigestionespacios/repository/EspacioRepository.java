package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Espacio;
import com.apigestionespacios.apigestionespacios.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    Optional<Espacio> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Espacio> findByCapacidadGreaterThanEqual(Integer capacidad);
    List<Espacio> findByTieneProyectorTrue();
    List<Espacio> findByTieneTVTrue();

}
