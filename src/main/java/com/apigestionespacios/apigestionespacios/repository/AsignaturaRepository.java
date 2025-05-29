package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    Optional<Asignatura> findByNombre(String nombre);
    Optional<Asignatura> findByCodigo(Integer codigo);
    List<Asignatura> findAllByOrderByNombreAsc();
    List<Asignatura> findByRequiereLaboratorioTrue();
}