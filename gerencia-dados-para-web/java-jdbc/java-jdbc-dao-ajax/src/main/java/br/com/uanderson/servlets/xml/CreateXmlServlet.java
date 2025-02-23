package br.com.uanderson.servlets.xml;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import br.com.uanderson.model.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateXmlServlet", value = "/create-xml")
public class CreateXmlServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define o tipo de resposta para texto simples, adequado para AJAX
        response.setContentType("text/plain; charset=UTF-8");

        try {
            String nome = request.getParameter("nome");
            String idadeStr = request.getParameter("idade");

            // Validação do campo 'Nome'
            if (nome == null || nome.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Erro ao cadastrar pessoa: Nome é obrigatório.");
                return;
            }

            // Validação do campo 'Idade'
            int idade;
            if (idadeStr == null || idadeStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("O campo 'Idade' não pode ser vazio.");
                return;
            }
            try {
                idade = Integer.parseInt(idadeStr);
                if (idade <= 0) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("O campo 'Idade' deve ser maior que 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Erro ao cadastrar pessoa: Idade inválida.");
                return;
            }

            // Criação da nova pessoa e salvando no banco de dados
            Pessoa pessoa = new Pessoa(nome, idade);
            pessoaDaoAjax.save(pessoa);

            // Retorna mensagem de sucesso
            response.getWriter().write("Pessoa cadastrada com sucesso!");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao cadastrar pessoa JDBC: " + e.getMessage());
        }
    }
}
