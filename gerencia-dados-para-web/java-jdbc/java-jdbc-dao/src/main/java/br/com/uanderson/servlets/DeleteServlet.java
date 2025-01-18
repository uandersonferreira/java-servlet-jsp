package br.com.uanderson.servlets;

import br.com.uanderson.dao.PessoaDAO;
import br.com.uanderson.dao.impl.PessoaDAOImpl;
import br.com.uanderson.model.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "DeleteServlet", value = "/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    private final PessoaDAO pessoaDAO = new PessoaDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
                session.setAttribute("mensagem", "ID inválido");
                session.setAttribute("tipoMensagem", "erro");
                response.sendRedirect("/readServlet");
                return;
            }

            int id = Integer.parseInt(request.getParameter("id"));
            Pessoa pessoa = pessoaDAO.findById(id);

            if (pessoa == null) {
                session.setAttribute("mensagem", "Pessoa não encontrada");
                session.setAttribute("tipoMensagem", "erro");
                response.sendRedirect("/readServlet");
                return;
            }

            pessoaDAO.deleteById(pessoa.getId());
            session.setAttribute("mensagem", "Pessoa deletada com sucesso");
            session.setAttribute("tipoMensagem", "sucesso");

        } catch (NumberFormatException e) {
            session.setAttribute("mensagem", "ID inválido");
            session.setAttribute("tipoMensagem", "erro");
        } catch (Exception e) {
            session.setAttribute("mensagem", "Erro ao deletar pessoa: " + e.getMessage());
            session.setAttribute("tipoMensagem", "erro");
        }

        response.sendRedirect("/readServlet");
    }
}