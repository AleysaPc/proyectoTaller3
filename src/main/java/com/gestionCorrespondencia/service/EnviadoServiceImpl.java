package com.gestionCorrespondencia.service;

import com.gestionCorrespondencia.dao.EnvioDao;
import com.gestionCorrespondencia.dao.RegistroDao;
import com.gestionCorrespondencia.domain.Enviados;
import com.gestionCorrespondencia.domain.Registro;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnviadoServiceImpl implements EnviadoService {

    @Autowired
    private EnvioDao envioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Enviados> listarEnvios() {
        return (List<Enviados>)envioDao.findAll();
    }

    @Override
    public void guardar(Enviados enviados) {
        envioDao.save(enviados);
    }

    @Override
    public void eliminar(Enviados enviados) {
        envioDao.delete(enviados);
    }

    public Enviados encontrarEnvio(Enviados enviados) {
        return envioDao.findById(enviados.getIddocumento()).orElse(null);
    }

    @Override
    public Registro encontrarRegistro(Enviados enviados) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
