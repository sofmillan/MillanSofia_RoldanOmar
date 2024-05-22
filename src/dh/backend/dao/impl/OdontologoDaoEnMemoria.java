package dh.backend.dao.impl;

import dh.backend.dao.IDao;
import dh.backend.model.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoEnMemoria implements IDao<Odontologo> {
    private final List<Odontologo> odontologos = new ArrayList<>();
    private static int contador = 1;
    private final Logger LOGGER = Logger.getLogger(OdontologoDaoEnMemoria.class);

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Odontologo odontologoRegistrado = new Odontologo(contador, odontologo.getNroMatricula(), odontologo.getNombre(), odontologo.getApellido());
        odontologos.add(odontologoRegistrado);
        contador++;
        LOGGER.info("Odontologo registrado "+odontologoRegistrado);
        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        LOGGER.info("Odontologos encontrados "+odontologos);
        return odontologos;
    }
}
