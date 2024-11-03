package br.com.uanderson.cookieandsession;

import br.com.uanderson.cookieandsession.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final List<User> users = Arrays.asList(
            new User("uanderson", "123"),
            new User("jose", "123"),
            new User("maria", "123")
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            HttpSession session = request.getSession(false);

            if (login != null && senha != null) {
                boolean isValidUser = users.stream().anyMatch
                        (user -> user.getLogin().equals(login) && user.getPassword().equals(senha));

                if (isValidUser) {
                    session = request.getSession();
                    session.setMaxInactiveInterval(3600); // 1 hora
                    session.setAttribute("name_user", login);
                }
            }//if check null

            if (session != null) {
                out.println("<h1>Seja Bem-vindo " + session.getAttribute("name_user") + "!</h1>");
                out.println("<a href='/promocao'>Ver Produtos </a>");

            }else {
                //message login erro and link for home page
                out.println("<h1>Login ou senha incorretos!</h1> <br> ");
                out.println("<a href='index.html'>Voltar para a Home</a>");
            }

        }//try

    }//method
}//class