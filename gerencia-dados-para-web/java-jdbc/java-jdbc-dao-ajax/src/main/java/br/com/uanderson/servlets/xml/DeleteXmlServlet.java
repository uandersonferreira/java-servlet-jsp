package br.com.uanderson.servlets.xml;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import br.com.uanderson.model.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@WebServlet(name = "DeleteXmlServlet", value = "/delete-xml")
public class DeleteXmlServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Define o tipo de resposta para texto simples (AJAX)
        response.setContentType("text/plain; charset=UTF-8");

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID inválido");
                log.error("Tentativa de deleção com ID inválido");
                return;
            }

            int id = Integer.parseInt(idParam);
            Pessoa pessoa = pessoaDaoAjax.findById(id);

            if (pessoa == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Pessoa não encontrada");
                log.warn("Tentativa de deleção de pessoa inexistente com ID: {}", id);
            } else {
                pessoaDaoAjax.deleteById(pessoa.getId());
                response.getWriter().write("Pessoa deletada com sucesso");
                log.info("Pessoa com ID {} deletada com sucesso", id);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID inválido");
            log.error("Erro de formato no ID: {}", e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao deletar pessoa: " + e.getMessage());
            log.error("Erro ao deletar pessoa: {}", e.getMessage());
        }
    }
}
