package com.gestionCorrespondencia.dao;

import com.gestionCorrespondencia.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{

    //Metodo
    Usuario finByUsername(String username);
    //Se crea una instancia de esta clase
}
