package com.apigestionespacios.apigestionespacios.JWT.entity;

import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicesImplementation implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return new usuarioJwt(usuario.getUsername(), usuario.getPassword(), usuario.getRoles());
    }
}
