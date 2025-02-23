package br.com.uanderson.servlets.jdbc;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoXmlImpl;
import br.com.uanderson.model.Pessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/pessoa"})
public class PessoaServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDao;
    private final ObjectMapper objectMapper;

    public PessoaServlet() {
        this.pessoaDao = new PessoaDaoXmlImpl();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Pessoa> pessoas = pessoaDao.listAll();
        String jsonPessoas = objectMapper.writeValueAsString(pessoas);

        response.getWriter().write(jsonPessoas);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Ler o JSON do corpo da requisição
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        Pessoa novaPessoa = objectMapper.readValue(body, Pessoa.class);
        pessoaDao.save(novaPessoa);

        // Retornar a pessoa cadastrada como JSON
        response.getWriter().write(objectMapper.writeValueAsString(novaPessoa));
    }
}