package br.com.uanderson.repository.exception;

public class DatabaseConnectionException extends Exception{

    public DatabaseConnectionException() {
        super("Erro ao conectar ao banco de dados");
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }


}//class
