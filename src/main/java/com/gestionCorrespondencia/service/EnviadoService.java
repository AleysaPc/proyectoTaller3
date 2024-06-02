/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.domain.Enviados;
import com.gestionCorrespondencia.domain.Registro;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface EnviadoService {
    
    public List<Enviados> listarEnvios();
    public void guardar (Enviados enviados);
    public void eliminar (Enviados enviados);
    public Registro encontrarRegistro(Enviados enviados);
    
}
