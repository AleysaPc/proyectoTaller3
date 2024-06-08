package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.dao.RegistroDao;
import com.gestionCorrespondencia.domain.Registro;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private RegistroDao registroDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Registro> listarRegistro() {
        return (List<Registro>)registroDao.findAll();
    }

    @Override
    public void guardar(Registro registro) {
        registroDao.save(registro);
    }

    @Override
    public void eliminar(Registro registro) {
        registroDao.delete(registro);
    }

    @Override
    public Registro encontrarRegistro(Registro registro) {
        return registroDao.findById(registro.getIdregistro()).orElse(null);
     }
    
}
