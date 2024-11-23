package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AreaRestrita", value = "/area-restrita")
public class AreaRestrita extends HttpServlet {
    //Servlet de Area restrita

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                out.println("<h1>Seja Bem-vindo " + session.getAttribute("nome_user") + "!</h1>");
                out.println("<p> Você pode acessa a Área Restrita :) </p>");
                //link for logout servlet
                out.println("<a href='logout'>Sair</a>");
            }else {
                //message login erro and link for home page
                out.println("<h1>Realize o login para ter acesso!</h1> <br> ");
                out.println("<a href='index.html'>Voltar para a Home</a>");
            }
        }//try

    }//method

}//class
