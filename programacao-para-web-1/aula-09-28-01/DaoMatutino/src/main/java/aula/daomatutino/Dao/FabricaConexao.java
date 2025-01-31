package aula.daomatutino.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/db_produtos";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "admin";


    public static Connection pegaConexao() throws ErroDao {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ErroDao(e);
        }
    }
}
