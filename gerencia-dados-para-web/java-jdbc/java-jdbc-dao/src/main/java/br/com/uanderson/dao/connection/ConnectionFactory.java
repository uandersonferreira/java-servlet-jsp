package br.com.uanderson.dao.connection;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class ConnectionFactory {
    // Constantes de configuração do banco de dados
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/db_jdbc_example";
    private static final String DATABASE_USER = "test";
    private static final String DATABASE_PASSWORD = "test123";

    // Singleton: instância única da ConnectionFactory
    private static ConnectionFactory instance;

    // Construtor privado para evitar instanciações diretas
    private ConnectionFactory() {
    }

    /**
     * Método para obter a instância única da ConnectionFactory (Singleton).
     * Será usada quando instanciarmos a classe ConnectionFactory. (Injeção de Dependência)
     *
     * @return A instância única de ConnectionFactory.
     */
    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
        //OBS: lembrar da implementação para cenários de multithreading
    }

    /**
     * Método responsável por criar e retornar uma nova conexão com o banco de dados.
     *
     * @return Uma instância de Connection.
     * @throws RuntimeException Caso ocorra um erro ao conectar.
     */
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Retorna a conexão com os parâmetros configurados
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            log.info("Erro ao conectar ao banco de dados", e);
        } catch (ClassNotFoundException e) {
            log.info("Driver JDBC não encontrado: " + e.getMessage(), e);
        }
        return connection;
    }


}//class
