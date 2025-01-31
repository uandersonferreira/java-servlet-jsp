package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "ConnectionDatabaseServlet", value = "/ConnectionDatabaseServlet")
public class ConnectionDatabaseServlet extends HttpServlet {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/db_jdbc_example_aula";
    private static final String DATABASE_USER = "test";
    private static final String DATABASE_PASSWORD = "test123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String preco = request.getParameter("preco");


        PrintWriter out = response.getWriter();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            out.println("<h1>Conexão com o banco de dados estabelecida com sucesso</h1> <br><hr>");

            if (nome != null && descricao != null && preco != null) {

                String sql_insert = String.format("INSERT INTO PRODUTO (nome, descricao, preco) VALUES ('%s', '%s', %s)", nome, descricao, preco);

                out.println(sql_insert + "<br><hr>");

                PreparedStatement preparedStatement = connection.prepareStatement(sql_insert);

                preparedStatement.executeUpdate();

                listarProdutos(connection, out);

            }else {
                listarProdutos(connection, out);
            }

        } catch (SQLException e) {
            out.println("Erro ao conectar ao banco de dados");
        } catch (ClassNotFoundException e) {
            out.println("Driver JDBC não encontrado");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                out.println("Erro ao fechar conexão");
            }
        }

    }

    private static void listarProdutos(Connection connection, PrintWriter out) throws SQLException {
        String sql_select = "SELECT * FROM PRODUTO";

        ResultSet resultSet = connection.prepareStatement(sql_select).executeQuery();
        //TODA CHAMADA DO NEXT É EXECUTADA NO BANCO DE DADOS E NÃO ZERA, ENTÃO USE SOMENTE PARA FAZER AS LEITURAS DOS
        //DADOS. NÃO USE PARA FAZER CONTAGEM DE LINHAS OU QUALQUER OUTRA COISA.
//        if (!resultSet.next()) {
//            out.println("<h1>Produtos ainda não cadastrados</h1>");
//            return;
//        }

        while (resultSet.next()) {
            out.println(resultSet.getInt("id"));
            out.println(resultSet.getString("nome"));
            out.println(resultSet.getString("descricao"));
            out.println(resultSet.getDouble("preco"));
            out.println("<br><hr>");
        }
    }


}