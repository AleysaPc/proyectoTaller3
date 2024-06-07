package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.domain.Tasks;
import java.util.List;

public interface TasksService {

    public List<Tasks> listarTareas();
    public void guardar (Tasks tasks);
    public void eliminar (Tasks tasks);
    public Tasks encontrarTarea(Tasks tasks);
}
