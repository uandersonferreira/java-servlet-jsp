package br.com.uanderson.servlets;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import br.com.uanderson.model.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/createServlet")
public class CreateServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String idadeStr = request.getParameter("idade");

        String mensagemErro;
        String campoErro;

        // Validação do campo 'Nome'
        if (nome == null || nome.trim().isEmpty()) {
            mensagemErro = "O campo 'Nome' é obrigatório.";
            campoErro = "nome";
            errors(request, response, nome, idadeStr, mensagemErro, campoErro);
            return;  // Retorna imediatamente, evitando que continue a execução
        }

        // Validação do campo 'Idade'
        int idade = 0;
        try {
            if (idadeStr == null || idadeStr.trim().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Idade' não pode ser vazio.");
            }
            idade = Integer.parseInt(idadeStr);

            if (idade <= 0) {
                throw new IllegalArgumentException("O campo 'Idade' deve ser maior que 0.");
            }
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
            campoErro = "idade";
            errors(request, response, nome, idadeStr, mensagemErro, campoErro);
            return;  // Retorna imediatamente, evitando continuar a execução
        }

        try {
            pessoaDaoAjax.save(new Pessoa(nome, idade));
            // Redirecionar com mensagem de sucesso
            request.getSession().setAttribute("mensagem", "Cadastro realizado com sucesso!");
            request.getSession().setAttribute("tipoMensagem", "sucesso");
            response.sendRedirect("readServlet");
        } catch (Exception e) {
            // Redirecionar com mensagem de erro
            request.getSession().setAttribute("mensagem", "Erro ao cadastrar os dados: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "erro");
            response.sendRedirect("createServlet");
        }
    }

    private void errors(HttpServletRequest request, HttpServletResponse response, String nome, String idadeStr, String mensagemErro, String campoErro) throws ServletException, IOException {
        // Criar o objeto Pessoa com os dados já preenchidos no formulário
        Pessoa pessoa = new Pessoa(nome, idadeStr != null && !idadeStr.trim().isEmpty() ? Integer.parseInt(idadeStr) : 0);
        request.setAttribute("pessoa", pessoa);
        request.setAttribute("mensagemErro", mensagemErro);
        request.setAttribute("campoErro", campoErro);
        request.getRequestDispatcher("create.jsp").forward(request, response);
    }
}
