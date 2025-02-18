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

@WebServlet(name = "CreateServlet", value = "/create-xml")
public class CreateServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String idadeStr = request.getParameter("idade");


        // Validação do campo 'Nome'
        if (nome == null || nome.trim().isEmpty()) {
            response.sendRedirect("create.jsp?error=Erro ao cadastrar pessoa: Nome é obrigatório.");
            return;  // Retorna imediatamente, evitando que continue a execução
        }

        // Validação do campo 'Idade'
        int idade = 0;
        try {
            if (idadeStr == null || idadeStr.trim().isEmpty()) {
                response.sendRedirect("create.jsp?error=O campo 'Idade' não pode ser vazio.");
            }
            idade = Integer.parseInt(idadeStr);

            if (idade <= 0) {
                response.sendRedirect("create.jsp?error=O campo 'Idade' deve ser maior que 0.");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect("create.jsp?error=Erro ao cadastrar pessoa Verifique os campos");
            e.printStackTrace();
            return;  // Retorna imediatamente, evitando continuar a execução
        }

        try {
            pessoaDaoAjax.save(new Pessoa(nome, idade));
            // Redirecionar com mensagem de sucesso para /pessoas-xml
            response.sendRedirect("pessoas-xml?success=Pessoa cadastrada com sucesso!");

        } catch (Exception e) {
            // Redirecionar com mensagem de erro /pessoas-xml
            response.sendRedirect("create.jsp?error=Erro ao cadastrar pessoa JDBC");
        }
    }

}
