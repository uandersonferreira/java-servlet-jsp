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

@WebServlet(name = "UpdateXmlServlet", value = "/update-xml")
public class UpdateXmlServlet extends HttpServlet {

    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define o tipo de resposta para texto simples
        response.setContentType("text/plain; charset=UTF-8");

        try {
            // Obtém e converte os parâmetros enviados
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String idadeStr = request.getParameter("idade");

            // Valida o campo 'nome'
            if (nome == null || nome.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("O campo 'Nome' é obrigatório.");
                return;
            }

            // Valida e converte o campo 'idade'
            int idade;
            try {
                idade = Integer.parseInt(idadeStr);
                if (idade <= 0) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("O campo 'Idade' deve ser maior que 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Idade inválida.");
                return;
            }

            // Busca a pessoa no banco de dados
            Pessoa pessoa = pessoaDaoAjax.findById(id);
            if (pessoa == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Pessoa não encontrada.");
                return;
            }

            // Atualiza os dados da pessoa
            pessoa.setNome(nome);
            pessoa.setIdade(idade);

            // Realiza a atualização
            pessoaDaoAjax.update(pessoa);

            // Retorna mensagem de sucesso
            response.getWriter().write("Atualização realizada com sucesso!");
        } catch (Exception e) {
            // Em caso de exceção, retorna status 500 e a mensagem de erro
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao atualizar os dados: " + e.getMessage());
        }
    }
}
