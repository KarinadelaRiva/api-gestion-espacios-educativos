package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Integer> {

    /**
     * Método para buscar una asignatura por su código
     * @param codigo el código de la asignatura
     * @return un Optional que contiene la asignatura si se encuentra, o vacío si no
     */
    Optional<Asignatura> findByCodigo(int codigo);
}
