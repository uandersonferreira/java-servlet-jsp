package br.com.uanderson.sistemadelogin.controller;

import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        // Se login e senha forem nulos ou vazios, redireciona para a página de login com uma mensagem de erro e nem executa o código abaixo
        if (login == null || login.isBlank() || senha == null || senha.isBlank()) {
            response.sendRedirect("index.jsp?mensagem=alerta-campos");
            return; // Para a execução do código
        }

        //Obtém a lista de usuários do contexto da aplicação
        List<Usuario> usersContext = (List<Usuario>) getServletContext().getAttribute("usersContext");
        if (usersContext == null) {
            response.sendRedirect("index.jsp?mensagem=info-vazio");
            return; // Para a execução do código e nem executa o código abaixo
        }

        // Verificar se o login e senha são válidos
        for (Usuario usuario : usersContext) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                HttpSession session = request.getSession();

                if (session != null && session.getAttribute("usuario") != null) {
                    response.sendRedirect("index.jsp?mensagem=erro-login-session");
                    return;
                }

                // Adicionar usuário à sessão e redirecionar para a página de relatório
                session.setAttribute("usuario", usuario);
                response.sendRedirect("relatorio.jsp");
                return;
            }
        }

        response.sendRedirect("index.jsp?mensagem=error-login-authentication");

    }//method

}//class