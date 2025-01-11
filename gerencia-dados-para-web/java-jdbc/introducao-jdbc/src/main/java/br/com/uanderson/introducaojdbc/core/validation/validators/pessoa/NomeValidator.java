package br.com.uanderson.introducaojdbc;

import java.sql.*;

public class NomeValidator implements FieldValidator {
    @Override
    public ValidationResult validate(String fieldName, String value) {
        ValidationResult result = new ValidationResult();
        if (value == null || value.trim().isEmpty()) {
            result.addError(fieldName, "Campo não pode ser nulo ou vazio");
        }
        //check if the name contains only letters
        if (value != null && !value.matches("[a-zA-Z]+")) {
            result.addError(fieldName, "Campo deve conter apenas letras");
        }
        //check if the name is between
        if (value != null && (value.length() < 3 || value.length() > 50)) {
            result.addError(fieldName, "Campo deve conter entre 3 e 50 caracteres");
        }
        //check if the name is not duplicate
        if (value != null && isDuplicate(value)) {
            result.addError(fieldName, "Nome já existe, tente outro.");
        }

        return result;
    }

    private boolean isDuplicate(String value) {
        // Implement a method to check if the name is already in the database
        final String URL = "jdbc:postgresql://localhost:5432/db_jdbc_example";
        final String USUARIO = "test";
        final String SENHA = "test123";


        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA);

            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM pessoa WHERE nome = ?");

            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            statement.close();

            connection.close();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
