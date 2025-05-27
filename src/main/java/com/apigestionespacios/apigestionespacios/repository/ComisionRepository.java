package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Comision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComisionRepository extends JpaRepository<Comision, Long> {
    List<Comision> findByAsignaturaId(Long asignaturaId);
    List<Comision> findByProfesorId(Long profesorId);
    List<Comision> findByComision(String comision);
    //Busca inscripciones activas
    List<Comision> findByFechaFinInscripcionAfter(LocalDate fecha);
}
