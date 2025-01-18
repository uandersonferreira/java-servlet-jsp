package br.com.uanderson.servlets;

import br.com.uanderson.dao.PessoaDAO;
import br.com.uanderson.dao.impl.PessoaDAOImpl;
import br.com.uanderson.model.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/readServlet")
public class ReadServlet extends HttpServlet {
    private final PessoaDAO pessoaDAO = new PessoaDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Pessoa> pessoas = pessoaDAO.listAll();

        if (pessoas.isEmpty()) {
            request.setAttribute("mensagem", "Não há pessoas cadastradas");
        } else {
            request.setAttribute("pessoas", pessoas);
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
