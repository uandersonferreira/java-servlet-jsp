package br.com.uanderson.repository.connection;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

@Component
public class ConnectionFactory {
    // Constantes de configuração do banco de dados
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/db_site_noticias";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "admin@2025";

    private Logger log = Logger.getLogger(String.valueOf(ConnectionFactory.class));

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
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            log.info("Erro ao conectar ao banco de dados" + e);
        } catch (ClassNotFoundException e) {
            log.info("Driver JDBC não encontrado: " + e);
        }
        return connection;
    }


}//class
