package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByAsignaturaId(Long asignaturaId);
    List<Inscripcion> findByProfesorId(Long profesorId);
    List<Inscripcion> findByComision(String comision);
    //Busca inscripciones activas
    List<Inscripcion> findByFechaFinInscripcionAfter(LocalDate fecha);
}
