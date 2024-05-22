package dh.backend.test;

import dh.backend.dao.impl.OdontologoDaoEnMemoria;
import dh.backend.dao.impl.OdontologoDaoH2;
import dh.backend.model.Odontologo;
import dh.backend.service.OdontologoService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OdontologoServiceTest {
    private final Logger LOGGER = Logger.getLogger(OdontologoServiceTest.class);
    @BeforeEach
    void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/parcial;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @Test
    void testRegistrarOdontologo(){
        Odontologo odontologo = new Odontologo("56","Camila", "López");
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologoRegistrado = odontologoService.registrar(odontologo);

        assertNotNull(odontologoRegistrado);
    }

    @Test
    void testBuscarTodos(){
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
        List<Odontologo> odontologosEncontrados = odontologoService.buscarTodos();

        assertEquals(2, odontologosEncontrados.size());
    }
}
