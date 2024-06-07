package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.dao.TasksDao;
import com.gestionCorrespondencia.domain.Tasks;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TasksServiceImpl implements TasksService {
    
    @Autowired
    private TasksDao tasksDao;

    @Override
    public List<Tasks> listarTareas() {
        return (List<Tasks>)tasksDao.findAll();
    }

    @Override
    public void guardar(Tasks tasks) {
        tasksDao.save(tasks);
    }

    @Override
    public void eliminar(Tasks tasks) {
          tasksDao.delete(tasks);
    }
    @Override
    public Tasks encontrarTarea(Tasks tasks) {
        return tasksDao.findById(tasks.getId()).orElse(null);
    }
    
}
