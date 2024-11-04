package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EnviarErroServlet", value = "/enviar-erro")
public class EnviarErroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        String nome = request.getParameter("nome");
        if (nome == null || nome.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome é obrigatório");
            return;
        }

        //Enviando um erro de cliente 400: HTTP Status 400 – Bad Request
        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>" + nome + " </h1>");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}