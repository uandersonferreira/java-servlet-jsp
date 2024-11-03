package br.com.uanderson.cookieandsession;

import br.com.uanderson.cookieandsession.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final List<User> users = Arrays.asList(
            new User("uanderson", "123"),
            new User("jose", "123"),
            new User("maria", "123")
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean isValid = users.stream()
                .anyMatch(user -> user.getLogin().equals(login) &&
                        user.getPassword().equals(password));

        if (isValid) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", login);
            response.sendRedirect("computer");
        } else {
            response.sendRedirect("index.html?error=true");
        }
    }
}