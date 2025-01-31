package br.com.uanderson.servlets.ajax;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import br.com.uanderson.model.ApiResponse;
import br.com.uanderson.model.Pessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

// SalvarPessoaServlet.java
@WebServlet("/api/pessoas/salvar")
@Log4j2
public class SalvarPessoaServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDao;
    private final ObjectMapper objectMapper;

    public SalvarPessoaServlet() {
        this.pessoaDao = new PessoaDaoAjaxImpl();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Ler o JSON do corpo da requisição
            StringBuilder jsonBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // Converter JSON para objeto Pessoa
            Pessoa novaPessoa = objectMapper.readValue(jsonBuilder.toString(), Pessoa.class);

            // Salvar no banco
            pessoaDao.save(novaPessoa);

            ApiResponse<Pessoa> apiResponse = new ApiResponse<>(
                    true,
                    "Pessoa salva com sucesso",
                    novaPessoa
            );
            out.print(objectMapper.writeValueAsString(apiResponse));
            log.info("Pessoa '{}' salva com sucesso", novaPessoa.getNome());
        } catch (Exception e) {
            ApiResponse<Pessoa> apiResponse = new ApiResponse<>(
                    false,
                    "Erro ao salvar pessoa: " + e.getMessage(),
                    null
            );
            out.print(objectMapper.writeValueAsString(apiResponse));
            log.error("Erro ao salvar pessoa: {}", e.getMessage());
        }
    }
}
