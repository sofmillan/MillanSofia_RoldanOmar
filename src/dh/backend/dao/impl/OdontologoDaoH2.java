package dh.backend.dao.impl;

import dh.backend.dao.IDao;
import dh.backend.db.H2Connection;
import dh.backend.model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    private static String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT,?,?,?)";
    private static String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    @Override
    public Odontologo registrar(Odontologo odontologo) {
            Connection connection = null;
            Odontologo odontologoRegistrado = null;
            try{
                connection = H2Connection.getConnection();
                connection.setAutoCommit(false);

                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, odontologo.getNroMatricula());
                preparedStatement.setString(2, odontologo.getNombre());
                preparedStatement.setString(3, odontologo.getApellido());
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                while (resultSet.next()){
                    Integer id= resultSet.getInt(1);
                    odontologoRegistrado = new Odontologo(id, odontologo.getNroMatricula(), odontologo.getNombre(),
                            odontologo.getApellido());
                }
                LOGGER.info("Odontólogo registrado: "+ odontologoRegistrado);

                connection.commit();
                connection.setAutoCommit(true);

            }catch (Exception e){
                if(connection!=null){
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        LOGGER.info(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }
            }

            return odontologoRegistrado;
        }


    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologo = null;
        try{
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()){
                odontologo = new Odontologo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));

                LOGGER.info("Odontologo encontrado: "+ odontologo);
                odontologos.add(odontologo);
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologos;
    }
}
