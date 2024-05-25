package com.gestionCorrespondencia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name="usuario")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;
    
    @NotEmpty
    private String username;
    
    @NotEmpty
    private String password;
    
    //Unos a muchos
    @OneToMany
    //Columna que relaciona nuestras tablas
    @JoinColumn(name="idusuario")
    //Atribuo lista de objetos, para recuperar roles aosicados a un usuario
    private List<Rol> roles;
    
    
}
