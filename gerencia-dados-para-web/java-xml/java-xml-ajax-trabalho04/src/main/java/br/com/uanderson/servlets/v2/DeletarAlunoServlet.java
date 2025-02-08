package br.com.uanderson.servlets.v2;

import br.com.uanderson.processor.v2.AlunoXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/v2/deletarAluno")
public class DeletarAlunoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        AlunoXmlProcessor alunoXmlProcessor = new AlunoXmlProcessor(getServletContext());

        if (alunoXmlProcessor.deletarAluno(nome)) {
            response.getWriter().write("Aluno deletado com sucesso!");
        } else {
            response.getWriter().write("Aluno não encontrado.");
        }
    }
}
