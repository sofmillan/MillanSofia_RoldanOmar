package dh.backend.service;

import dh.backend.dao.IDao;
import dh.backend.model.Odontologo;

import java.util.List;

public class OdontologoService {
    private final IDao<Odontologo> dao;

    public OdontologoService(IDao dao) {
        this.dao = dao;
    }

    public Odontologo registrar(Odontologo odontologo){
        return dao.registrar(odontologo);
    }

    public List<Odontologo> buscarTodos(){
        return dao.buscarTodos();
    }
}
