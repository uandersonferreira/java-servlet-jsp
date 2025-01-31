package br.com.uanderson.dao.connection;

import org.postgresql.core.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionFactory {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/db_jdbc_example";
    private static final String DATABASE_USER = "test";
    private static final String DATABASE_PASSWORD = "test123";

    private static ConnectionFactory instance;
    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Retorna a conexão com os parâmetros configurados
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado: " + e);
        }
        return connection;
    }

}
