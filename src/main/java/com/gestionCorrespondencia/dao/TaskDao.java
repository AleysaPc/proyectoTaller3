package com.gestionCorrespondencia.dao;

import com.gestionCorrespondencia.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDao extends JpaRepository<Task, Long>{

}
