package com.apigestionespacios.apigestionespacios.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 50
    )
    private String nombre;

    @Column(
            nullable = false,
            length = 50
    )
    private String apellido;

    @Column(
            nullable = false,
            length = 50
    )
    private String username;

    @Column(
            nullable = false,
            length = 100
    )
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToMany(
            mappedBy = "profesor", // Nombre de la propiedad en la clase Comision que hace referencia a Usuario
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Comision> comisiones;

    public Set<? extends GrantedAuthority> getRoles() {
        return Collections.singleton(rol);
    }
} 