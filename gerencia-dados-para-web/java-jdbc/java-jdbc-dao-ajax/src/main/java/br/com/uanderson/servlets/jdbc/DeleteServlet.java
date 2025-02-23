package br.com.uanderson.servlets.jdbc;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import br.com.uanderson.model.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "DeleteXmlServlet", value = "/DeleteXmlServlet")
public class DeleteServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

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
            Pessoa pessoa = pessoaDaoAjax.findById(id);

            if (pessoa == null) {
                session.setAttribute("mensagem", "Pessoa não encontrada");
                session.setAttribute("tipoMensagem", "erro");
                response.sendRedirect("/readServlet");
                return;
            }

            pessoaDaoAjax.deleteById(pessoa.getId());
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