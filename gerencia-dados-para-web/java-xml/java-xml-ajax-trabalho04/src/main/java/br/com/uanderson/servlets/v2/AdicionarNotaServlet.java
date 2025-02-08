package br.com.uanderson.servlets.v2;

import br.com.uanderson.processor.v2.AlunoXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/v2/adicionarNota")
public class AdicionarNotaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        double nota = Double.parseDouble(request.getParameter("nota"));

        AlunoXmlProcessor alunoXmlProcessor = new AlunoXmlProcessor(getServletContext());
        if (alunoXmlProcessor.adicionarNota(nome, nota)) {
            response.getWriter().write("Nota adicionada com sucesso!");
        } else {
            response.getWriter().write("Aluno não encontrado.");
        }
    }
}
