package com.gestionCorrespondencia.dao;

import com.gestionCorrespondencia.domain.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksDao extends JpaRepository<Tasks, Long>{

}
