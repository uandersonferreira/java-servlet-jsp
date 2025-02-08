package br.com.uanderson.servlets.v2;

import br.com.uanderson.model.Aluno;
import br.com.uanderson.processor.v2.AlunoXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/v2/cadastrarAluno")
public class CadastrarAlunoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));

        Aluno novoAluno = new Aluno(nome, idade);
        AlunoXmlProcessor alunoXmlProcessor = new AlunoXmlProcessor(getServletContext());

        alunoXmlProcessor.adicionarAluno(novoAluno);

        response.getWriter().write("Aluno cadastrado com sucesso!");
    }
}

