package com.apigestionespacios.apigestionespacios.entities;

import com.apigestionespacios.apigestionespacios.entities.enums.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


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

    @Enumerated(EnumType.STRING) // Usar el enum directamente y guardarlo como texto
    @Column(nullable = false)
    private Rol rol;

    @OneToMany(
            mappedBy = "profesor", // Nombre de la propiedad en la clase Comision que hace referencia a Usuario
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Comision> comisiones;

    @Column(
            nullable = false,
            length = 20
    )
   /* @Enumerated(EnumType.STRING)
    private Rol rol; */

    public Set<? extends GrantedAuthority> getRoles() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + rol.name()));

    /*public Set<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(rol); */

    }
} 