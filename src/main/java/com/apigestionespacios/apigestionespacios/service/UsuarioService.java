package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.dtos.usuario.UsuarioCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.usuario.UsuarioResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.usuario.UsuarioUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Usuario;
import com.apigestionespacios.apigestionespacios.entities.enums.Rol;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceConflictException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /* @Autowired
    private PasswordEncoder passwordEncoder; */

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }

    private Usuario usuarioCreateDTOtoUsuario(UsuarioCreateDTO dto) {
        return Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(dto.getRol())
                .build();
    }

    private UsuarioResponseDTO usuarioToUsuarioResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .build();
    }

    /*public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }*/

    /**
     * Obtiene todos los usuarios y los convierte a DTOs de respuesta.
     *
     * @return lista de UsuarioResponseDTO con todos los usuarios.
     */
    public List<UsuarioResponseDTO> obtenerTodosDTO() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::usuarioToUsuarioResponseDTO)
                .toList();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Obtiene un usuario por su ID y lo convierte a DTO de respuesta.
     *
     * @param id el ID del usuario a buscar.
     * @return UsuarioResponseDTO correspondiente al usuario.
     * @throws ResourceNotFoundException si no existe el usuario con ese ID.
     */
    public UsuarioResponseDTO obtenerPorIdDTO(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return usuarioToUsuarioResponseDTO(usuario);
    }

    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + username));
    }

    /**
     * Obtiene un usuario por su username y lo convierte a DTO de respuesta.
     *
     * @param username el username del usuario a buscar.
     * @return UsuarioResponseDTO correspondiente al usuario.
     * @throws ResourceNotFoundException si no existe el usuario con ese username.
     */
    public UsuarioResponseDTO obtenerPorUsernameDTO(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + username));
        return usuarioToUsuarioResponseDTO(usuario);
    }

    public Usuario guardar(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new ResourceConflictException("Ya existe un usuario con el mismo username");
        }

//        if (!rolRepository.existsById(usuario.getRol().getId())) {
//            throw new RuntimeException("El rol especificado no existe");
//        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Guarda un nuevo usuario a partir de un DTO de creación.
     *
     * @param dto DTO con los datos para crear un usuario.
     * @return UsuarioResponseDTO con los datos del usuario guardado.
     * @throws ResourceConflictException si ya existe un usuario con el mismo username.
     */
    public UsuarioResponseDTO guardarDTO(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new ResourceConflictException("Ya existe un usuario con el mismo username");
        }

        Usuario usuario = usuarioCreateDTOtoUsuario(dto);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioToUsuarioResponseDTO(usuarioGuardado);
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

    /**
     * Actualiza un usuario existente a partir de un DTO de actualización.
     *
     * @param dto DTO con los datos para actualizar un usuario.
     * @return UsuarioResponseDTO con los datos del usuario actualizado.
     * @throws ResourceConflictException si el nuevo username ya está en uso por otro usuario.
     * @throws ResourceNotFoundException si no existe el usuario con el ID especificado.
     */
    public UsuarioResponseDTO actualizarDTO(UsuarioUpdateDTO dto) {
        Usuario existente = obtenerPorId(dto.getId());

        if (!existente.getUsername().equals(dto.getUsername()) &&
                usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new ResourceConflictException("El username ya está en uso por otro usuario");
        }

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setUsername(dto.getUsername());

        // Si la contraseña viene y no está vacía, la codificamos y actualizamos
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        existente.setRol(dto.getRol());

        Usuario actualizado = usuarioRepository.save(existente);

        return usuarioToUsuarioResponseDTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    /**
     * Obtiene una lista de usuarios filtrados por rol y los convierte a DTOs de respuesta.
     *
     * @param rol el rol por el cual filtrar los usuarios.
     * @return lista de UsuarioResponseDTO con usuarios que tengan el rol especificado.
     */
    public List<UsuarioResponseDTO> obtenerPorRolDTO(Rol rol) {
        List<Usuario> usuarios = usuarioRepository.findByRol(rol);
        return usuarios.stream()
                .map(this::usuarioToUsuarioResponseDTO)
                .toList();
    }

}

