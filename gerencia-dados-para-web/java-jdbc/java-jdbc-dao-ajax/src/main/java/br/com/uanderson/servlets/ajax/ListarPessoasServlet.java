package br.com.uanderson.servlets.ajax;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import br.com.uanderson.dao.impl.PessoaDaoXmlImpl;
import br.com.uanderson.model.ApiResponse;
import br.com.uanderson.model.Pessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// ListarPessoasServlet.java
@WebServlet("/api/pessoas/listar")
@Log4j2
public class ListarPessoasServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDao;
    private final ObjectMapper objectMapper;

    public ListarPessoasServlet() {
        this.pessoaDao = new PessoaDaoXmlImpl();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<Pessoa> pessoas = pessoaDao.listAll();
            ApiResponse<List<Pessoa>> apiResponse = new ApiResponse<>(
                    true,
                    "Pessoas listadas com sucesso",
                    pessoas
            );
            out.print(objectMapper.writeValueAsString(apiResponse));
            log.info("Listagem de pessoas realizada com sucesso. Total: {}", pessoas.size());
        } catch (Exception e) {
            ApiResponse<List<Pessoa>> apiResponse = new ApiResponse<>(
                    false,
                    "Erro ao listar pessoas: " + e.getMessage(),
                    null
            );
            out.print(objectMapper.writeValueAsString(apiResponse));
            log.error("Erro ao listar pessoas: {}", e.getMessage());
        }
    }
}
