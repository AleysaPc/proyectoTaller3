package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.domain.Enviados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnviadosRepository  extends JpaRepository<Enviados, Long>{

}
