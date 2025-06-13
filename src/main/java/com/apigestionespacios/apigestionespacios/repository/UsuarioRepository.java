package com.apigestionespacios.apigestionespacios.repository;

import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.entities.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByRol(Rol rol);
    boolean existsByUsername(String username);

}
