package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Logar", value = "/logar")
public class Logar extends HttpServlet {
    //Servlet de login

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            HttpSession session = request.getSession(false);

            if (login != null && senha != null) {
                if (login.equals("uanderson") && senha.equals("123456")) {
                    //create session lado do servidor
                    session = request.getSession();
                    session.setMaxInactiveInterval(60);
                    session.setAttribute("nome_user", login);
                    out.println("Login realizado com sucesso!");
                }
            }

            if (session != null) {
                out.println("<h1>Seja Bem-vindo " + session.getAttribute("nome_user") + "!</h1>");
            }else {
                //message login erro and link for home page
                    out.println("<h1>Login ou senha incorretos!</h1> <br> ");
                    out.println("<a href='index.html'>Voltar para a Home</a>");

            }//else

            //out.println("<h1>Olá visitante!</h1>");
        }//try

    }//method

}//class
