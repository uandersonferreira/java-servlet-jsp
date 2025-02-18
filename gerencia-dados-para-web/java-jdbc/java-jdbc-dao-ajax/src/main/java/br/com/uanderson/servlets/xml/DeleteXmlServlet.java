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
@WebServlet(name = "DeleteServlet", value = "/delete-xml")
public class DeleteServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/xml;charset=UTF-8");
        String xmlResponse;

        try {
            if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
                xmlResponse = "<response><status>error</status><message>ID inválido</message></response>";
                log.error("Tentativa de deleção com ID inválido");
                response.getWriter().write(xmlResponse);
                return;
            }

            int id = Integer.parseInt(request.getParameter("id"));
            Pessoa pessoa = pessoaDaoAjax.findById(id);

            if (pessoa == null) {
                xmlResponse = "<response><status>error</status><message>Pessoa não encontrada</message></response>";
                log.warn("Tentativa de deleção de pessoa inexistente com ID: {}", id);
            } else {
                pessoaDaoAjax.deleteById(pessoa.getId());
                xmlResponse = "<response><status>success</status><message>Pessoa deletada com sucesso</message></response>";
                log.info("Pessoa com ID {} deletada com sucesso", id);
            }

        } catch (NumberFormatException e) {
            xmlResponse = "<response><status>error</status><message>ID inválido</message></response>";
            log.error("Erro de formato no ID: {}", e.getMessage());
        } catch (Exception e) {
            xmlResponse = "<response><status>error</status><message>Erro ao deletar pessoa: " + e.getMessage() + "</message></response>";
            log.error("Erro ao deletar pessoa: {}", e.getMessage());
        }

        response.getWriter().write(xmlResponse);
    }
}
