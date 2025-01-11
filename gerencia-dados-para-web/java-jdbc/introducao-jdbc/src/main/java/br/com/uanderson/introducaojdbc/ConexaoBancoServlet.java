package br.com.uanderson.introducaojdbc;

import br.com.uanderson.introducaojdbc.core.validation.core.InputValidator;
import br.com.uanderson.introducaojdbc.core.validation.core.ValidationResult;
import br.com.uanderson.introducaojdbc.core.validation.validators.pessoa.IdadeValidator;
import br.com.uanderson.introducaojdbc.core.validation.validators.pessoa.NomeValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "helloServlet", value = "/conexao-banco")
public class ConexaoBancoServlet extends HttpServlet {

    private static final String URL = "jdbc:postgresql://localhost:5432/db_jdbc_example";
    private static final String USUARIO = "test";
    private static final String SENHA = "test123";


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        String nome = request.getParameter("nome");

        String idade = request.getParameter("idade");

        InputValidator validator = new InputValidator()
                .addValidator("nome", new NomeValidator())
                .addValidator("idade", new IdadeValidator());

        Map<String, String> campos = new HashMap<>();
        campos.put("nome", request.getParameter("nome"));
        campos.put("idade", request.getParameter("idade"));

        ValidationResult validationResult = validator.validate(campos);

        if (validationResult.hasErrors()) {
            out.println("<h1>Erros de validação:</h1>");
            validationResult.getErrors().forEach((field, error) ->
                    out.println("<p>" + error + "</p>")
            );
            return;
        }

        out.println("<html><body>");
        out.println("<h1>Teste conexão Banco de dados PostgreSQL</h1>");

        try {
            Class.forName("org.postgresql.Driver");

            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            saveData(conexao, out, nome, idade);

            listaTodasPessoas(conexao, out);

            out.println("Conexão bem-sucedida!");

        } catch (ClassNotFoundException e) {

            out.println("Erro ao carregar o driver JDBC: " + e);

        } catch (SQLException e) {

            out.println("Erro ao conectar ao banco de dados: " + e);
        }
        out.println("</body></html>");
    }



    private static void listaTodasPessoas(Connection conexao, PrintWriter out) {
        ResultSet rs;
        try (Statement stmt = conexao.createStatement()) {
            rs = stmt.executeQuery("SELECT * FROM pessoa");

            out.println("<h2>Lista de Pessoas:</h2> <br>");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade + "<br>");
            }

        } catch (SQLException e) {

            out.println("Erro ao listar pessoas: " + e);
        }


    }

    // method to save data to database pessoa
    public void saveData(Connection conexao, PrintWriter out, String nome, String idade) {
        String sql = "INSERT INTO pessoa (nome, idade) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);

            preparedStatement.setInt(2, Integer.parseInt(idade));

            preparedStatement.executeUpdate();

            out.println("<h2>Dados salvos com sucesso!</h2> <br>");

        } catch (SQLException e) {

            out.println("Erro ao salvar dados: " + e);
        }
    }//method to save data to database pessoa

}//class
