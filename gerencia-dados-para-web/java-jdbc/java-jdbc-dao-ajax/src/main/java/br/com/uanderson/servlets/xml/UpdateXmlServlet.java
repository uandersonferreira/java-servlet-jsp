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

@WebServlet(name = "UpdateServlet", value = "/update-xml")
public class UpdateServlet extends HttpServlet {

    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Pessoa pessoa = pessoaDaoAjax.findById(id);
        if (pessoa != null) {
            req.setAttribute("pessoa", pessoa);
            req.getRequestDispatcher("update.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("readServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String idadeStr = request.getParameter("idade");

        boolean hasError = false;
        String mensagemErro = null;
        String campoErro = null;

        // Validação dos campos
        if (nome == null || nome.trim().isEmpty()) {
            mensagemErro = "O campo 'Nome' é obrigatório.";
            campoErro = "nome";
            hasError = true;
        } else if (idadeStr == null || idadeStr.isEmpty() || Integer.parseInt(idadeStr) <= 0) {
            mensagemErro = "O campo 'Idade' deve ser maior que 0.";
            campoErro = "idade";
            hasError = true;
        }

        if (hasError) {
            // Redirecionar de volta para o formulário com a mensagem de erro
            Pessoa pessoa = new Pessoa(pessoaDaoAjax.findById(id).getId(), nome, Integer.parseInt(idadeStr));
            request.setAttribute("pessoa", pessoa);
            request.setAttribute("mensagemErro", mensagemErro);
            request.setAttribute("campoErro", campoErro);
            request.getRequestDispatcher("update.jsp").forward(request, response);
        } else {
            // Atualizar a pessoa
            Pessoa pessoa = pessoaDaoAjax.findById(id);
            pessoa.setNome(nome);
            pessoa.setIdade(Integer.parseInt(idadeStr));

            try {
                pessoaDaoAjax.update(pessoa);
                // Redirecionar com mensagem de sucesso
                request.getSession().setAttribute("mensagem", "Atualização realizada com sucesso!");
                request.getSession().setAttribute("tipoMensagem", "sucesso");
            } catch (Exception e) {
                // Redirecionar com mensagem de erro
                request.getSession().setAttribute("mensagem", "Erro ao atualizar os dados: " + e.getMessage());
                request.getSession().setAttribute("tipoMensagem", "erro");
            }
            response.sendRedirect("readServlet");
        }
    }
}
