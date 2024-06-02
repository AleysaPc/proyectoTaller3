package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.domain.DocEnviados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnviadosRepository  extends JpaRepository<DocEnviados, Long>{

}
