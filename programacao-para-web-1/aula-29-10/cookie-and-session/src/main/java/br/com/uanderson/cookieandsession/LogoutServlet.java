package br.com.uanderson.cookieandsession;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                out.println("<h1>Volte sempre " + session.getAttribute("name_user") + "!</h1>");
                session.invalidate(); //destroy session
            }else {
                //message login erro and link for home page
                out.println("<h1>Realize o login para ter acesso!</h1> <br> ");
                out.println("<a href='index.html'>Voltar para a Home</a>");
            }
        }//try

    }//method

}//class
