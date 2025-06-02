package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceConflictException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.UsuarioRepository;
import com.apigestionespacios.apigestionespacios.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + username));
    }

    public Usuario guardar(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new ResourceConflictException("Ya existe un usuario con el mismo username");
        }

//        if (!rolRepository.existsById(usuario.getRol().getId())) {
//            throw new RuntimeException("El rol especificado no existe");
//        }

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario actualizado) {
        Usuario existente = obtenerPorId(id);

        if (!existente.getUsername().equals(actualizado.getUsername()) &&
                usuarioRepository.existsByUsername(actualizado.getUsername())) {
            throw new ResourceConflictException("El username ya está en uso por otro usuario");
        }

        existente.setNombre(actualizado.getNombre());
        existente.setApellido(actualizado.getApellido());
        existente.setUsername(actualizado.getUsername());
        existente.setPassword(actualizado.getPassword());

//        if (!rolRepository.existsById(actualizado.getRol().getId())) {
//            throw new RuntimeException("El rol especificado no existe");
//        }

        existente.setRol(actualizado.getRol());

        return usuarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerPorRol(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol);
    }
}
