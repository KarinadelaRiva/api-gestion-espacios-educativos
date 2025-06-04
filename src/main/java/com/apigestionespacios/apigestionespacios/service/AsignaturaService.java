package com.apigestionespacios.apigestionespacios.service;

import com.apigestionespacios.apigestionespacios.dtos.AsignaturaCreateDTO;
import com.apigestionespacios.apigestionespacios.dtos.AsignaturaResponseDTO;
import com.apigestionespacios.apigestionespacios.dtos.AsignaturaUpdateDTO;
import com.apigestionespacios.apigestionespacios.entities.Asignatura;
import com.apigestionespacios.apigestionespacios.exceptions.EntityValidationException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceConflictException;
import com.apigestionespacios.apigestionespacios.exceptions.ResourceNotFoundException;
import com.apigestionespacios.apigestionespacios.repository.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignaturaService {
    private final AsignaturaRepository asignaturaRepository;

    @Autowired
    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    /**
     * Convierte un DTO de creación de asignatura a una entidad Asignatura.
     *
     * @param dto DTO de creación de asignatura.
     * @return Entidad Asignatura.
     */
    public static Asignatura AsignaturaDTOToAsignatura(AsignaturaCreateDTO dto) {
        return Asignatura.builder()
                .nombre(dto.getNombre())
                .codigo(dto.getCodigo())
                .requiereLaboratorio(dto.getRequiereLaboratorio())
                .build();
    }

    /**
     * Convierte una entidad Asignatura a un DTO de respuesta.
     *
     * @param asignatura Entidad Asignatura.
     * @return DTO de respuesta de asignatura.
     */
    public static AsignaturaResponseDTO AsignaturaToAsignaturaResponseDTO(Asignatura asignatura) {
        return AsignaturaResponseDTO.builder()
                .id(asignatura.getId())
                .nombre(asignatura.getNombre())
                .codigo(asignatura.getCodigo())
                .requiereLaboratorio(asignatura.getRequiereLaboratorio())
                .build();
    }

    /**
     * Obtiene una lista de todas las asignaturas en formato DTO.
     *
     * @return Lista de DTOs de asignaturas.
     */
    public List<AsignaturaResponseDTO> obtenerTodasAsignaturasDTO() {
        return asignaturaRepository.findAll().stream()
                .map(AsignaturaService::AsignaturaToAsignaturaResponseDTO)
                .toList();
    }

    /**
     * Obtiene una asignatura por su ID.
     *
     * @param id ID de la asignatura.
     * @return Asignatura encontrada.
     * @throws ResourceNotFoundException si no se encuentra la asignatura.
     */
    public Asignatura obtenerPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada con ID: " + id));
    }

    public AsignaturaResponseDTO obtenerDTOPorId(Long id) {
        return AsignaturaToAsignaturaResponseDTO(asignaturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada con ID: " + id)));
    }

    /**
     * Obtiene una asignatura por su código.
     *
     * @param codigo Código de la asignatura.
     * @return Asignatura encontrada en formato DTO.
     * @throws ResourceNotFoundException si no se encuentra la asignatura.
     */
    public AsignaturaResponseDTO obtenerPorCodigo(Integer codigo) {
        return AsignaturaToAsignaturaResponseDTO(asignaturaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada con código: " + codigo)));
    }

    /**
     * Crea una nueva asignatura a partir de un DTO.
     *
     * @param dto DTO de creación de asignatura.
     * @return Asignatura creada.
     * @throws ResourceConflictException si ya existe una asignatura con el mismo código.
     */
    public Asignatura crearAsignaturaDesdeDTO(AsignaturaCreateDTO dto) {
        Asignatura asignatura = AsignaturaDTOToAsignatura(dto);
        if (asignaturaRepository.existsByCodigo(asignatura.getCodigo())) {
            throw new ResourceConflictException("Ya existe una asignatura con ese código");
        }
        return asignaturaRepository.save(asignatura);
    }

    /**
     * Actualiza una asignatura existente.
     *
     * @param id ID de la asignatura a actualizar.
     * @param nueva DTO con los nuevos datos de la asignatura.
     * @return Asignatura actualizada.
     * @throws ResourceNotFoundException si no se encuentra la asignatura.
     */
    public Asignatura actualizarAsignatura(Long id, AsignaturaUpdateDTO nueva) {
        Asignatura existente = obtenerPorId(id);

        existente.setNombre(nueva.getNombre());
        existente.setRequiereLaboratorio(nueva.getRequiereLaboratorio());

        return asignaturaRepository.save(existente);
    }

    public void eliminarAsignatura(Long id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asignatura no encontrada");
        }
        asignaturaRepository.deleteById(id);
    }
}
