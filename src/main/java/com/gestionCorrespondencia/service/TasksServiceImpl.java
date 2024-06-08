package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.dao.TasksDao;
import com.gestionCorrespondencia.domain.Tasks;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TasksServiceImpl implements TasksService {

    @Autowired
    private TasksDao tasksDao;

    @Override
    public List<Tasks> listarTareas() {
        return (List<Tasks>) tasksDao.findAll();
    }

    @Override
    public void guardar(Tasks tasks) {
        tasksDao.save(tasks);
    }

    @Override
    public void eliminar(Long idtasks) {
        tasksDao.deleteById(idtasks);
    }

    @Override
    public Tasks encontrarTarea(Long id) {
        return tasksDao.findById(id).orElse(null);
    }

    @Override
    public List<Tasks> listarTareasPendientes() {
        return tasksDao.findByEstado("Pendiente");
    }
    
}
