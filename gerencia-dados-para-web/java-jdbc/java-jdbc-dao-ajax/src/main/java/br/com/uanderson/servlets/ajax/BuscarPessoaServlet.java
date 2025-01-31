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

// BuscarPessoaServlet.java
@WebServlet("/api/pessoas/buscar")
@Log4j2
public class BuscarPessoaServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDao;
    private final ObjectMapper objectMapper;

    public BuscarPessoaServlet() {
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
            // Extrair ID da URL
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            int id = Integer.parseInt(pathParts[1]);

            Pessoa pessoa = pessoaDao.findById(id);

            if (pessoa != null) {
                ApiResponse<Pessoa> apiResponse = new ApiResponse<>(
                        true,
                        "Pessoa encontrada com sucesso",
                        pessoa
                );
                out.print(objectMapper.writeValueAsString(apiResponse));
                log.info("Pessoa com ID {} encontrada: {}", id, pessoa.getNome());
            } else {
                ApiResponse<Pessoa> apiResponse = new ApiResponse<>(
                        false,
                        "Pessoa não encontrada",
                        null
                );
                out.print(objectMapper.writeValueAsString(apiResponse));
                log.warn("Pessoa com ID {} não encontrada", id);
            }
        } catch (Exception e) {
            ApiResponse<Pessoa> apiResponse = new ApiResponse<>(
                    false,
                    "Erro ao buscar pessoa: " + e.getMessage(),
                    null
            );
            out.print(objectMapper.writeValueAsString(apiResponse));
            log.error("Erro ao buscar pessoa: {}", e.getMessage());
        }
    }
}
