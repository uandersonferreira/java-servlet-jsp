package br.com.uanderson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GeraErroServlet", value = "/gerar-erro")
public class GeraErroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // Gerando um erro de servidor 500: HTTP Status 500 – Internal Server Error
        //java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "nome" is null
        String nome = null;
        nome.equals("jgdjfghs");

        PrintWriter out = response.getWriter();
        out.println("<h1> Página de erro</h1>");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}