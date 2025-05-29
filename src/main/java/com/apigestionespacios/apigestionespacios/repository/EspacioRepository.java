package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    Optional<Espacio> findByNombre(String nombre);
    List<Espacio> findByCapacidadGreaterThanEqual(Integer capacidad);
    List<Espacio> findByTieneProyectorTrue();
    List<Espacio> findByTieneTVTrue();


    /* Buscar espacios con capacidad mayor o igual a :capacidad
    @Query("SELECT e FROM Espacio e WHERE e.capacidad >= :capacidad")
    List<Espacio> findByCapacidadMayorOIgual(@Param("capacidad") Integer capacidad);

    // Buscar espacios que tienen proyector (tieneProyector = true)
    @Query("SELECT e FROM Espacio e WHERE e.tieneProyector = true")
    List<Espacio> buscarConProyector();

    // Buscar espacios que tienen TV (tieneTV = true)
    @Query("SELECT e FROM Espacio e WHERE e.tieneTV = true")
    List<Espacio> buscarConTV(); */

}
