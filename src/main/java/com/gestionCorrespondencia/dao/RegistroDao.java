package com.gestionCorrespondencia.dao;

import com.gestionCorrespondencia.domain.Registro;
import org.springframework.data.repository.CrudRepository;


public interface RegistroDao  extends CrudRepository<Registro, Long>{

}
