
package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.domain.Registro;
import java.util.List;


public interface RegistroService {
    
    //Cramos los metodos principales 
    
    public List<Registro> listarRegistro();
    public void guardar (Registro registro);
    public void eliminar (Registro registro);
    public Registro encontrarRegistro(Registro registro);
    
}
