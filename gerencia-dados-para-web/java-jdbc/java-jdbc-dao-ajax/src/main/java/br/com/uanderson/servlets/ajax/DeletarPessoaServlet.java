package br.com.uanderson.servlets.ajax;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import br.com.uanderson.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.PrintWriter;

// DeletarPessoaServlet.java
@WebServlet("/api/pessoas/deletar")
@Log4j2
public class DeletarPessoaServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDao;
    private final ObjectMapper objectMapper;

    public DeletarPessoaServlet() {
        this.pessoaDao = new PessoaDaoAjaxImpl();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Extrair ID da URL
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            int id = Integer.parseInt(pathParts[1]);

            pessoaDao.deleteById(id);

            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    true,
                    "Pessoa deletada com sucesso",
                    null
            );
            out.print(objectMapper.writeValueAsString(apiResponse));
            log.info("Pessoa com ID {} deletada com sucesso", id);
        } catch (Exception e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                    false,
                    "Erro ao deletar pessoa: " + e.getMessage(),
                    null
            );
            out.print(objectMapper.writeValueAsString(apiResponse));
            log.error("Erro ao deletar pessoa: {}", e.getMessage());
        }
    }
}
