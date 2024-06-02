package com.gestionCorrespondencia.dao;

import com.gestionCorrespondencia.domain.Enviados;
import com.gestionCorrespondencia.domain.Registro;
import org.springframework.data.repository.CrudRepository;

public interface EnvioDao extends CrudRepository<Enviados, Long> {

}
