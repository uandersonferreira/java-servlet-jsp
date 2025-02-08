package br.com.uanderson.servlets.v2;


import br.com.uanderson.model.Aluno;
import br.com.uanderson.processor.v2.AlunoXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/v2/buscarAluno")
public class BuscarAlunoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");

        AlunoXmlProcessor alunoXmlProcessor = new AlunoXmlProcessor(getServletContext());

        Aluno aluno = alunoXmlProcessor.buscarAlunoPorNome(nome);

        if (aluno != null) {
            response.getWriter().write(aluno.toString());
        } else {
            response.getWriter().write("Aluno não encontrado.");
        }
    }
}
