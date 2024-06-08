package com.gestionCorrespondencia.dao;

import com.gestionCorrespondencia.domain.Tasks;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksDao extends JpaRepository<Tasks, Long>{
     List<Tasks> findByEstado(String estado);
    

}
