package br.com.uanderson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "segundoServlet", value = "/segundo") está sendo configurado no arquivo web.xml
public class SegundoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // Recuperando os parâmetros de inicialização, definidos no arquivo web.xml
        String nome = getServletConfig().getInitParameter("nome"); // De um servlet específico
        String empresa = getServletContext().getInitParameter("empresa"); // Da aplicação toda


        // Hello
        PrintWriter out = response.getWriter();
        String msg = "Pegando o atributo de um context, que foi inserido via Listener no context: ";
        out.println("<html><body>");
        out.println("<h1>" + empresa + "</h1>");
        out.println("<h1> Seja Bem-vindo, " + nome + "</h1>");
        out.println("<p>" + msg + getServletContext().getAttribute("conexao") + "</p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}