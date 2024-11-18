package br.com.uanderson.exemplocrudaula.servlet.user;

import br.com.uanderson.exemplocrudaula.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/sair")
public class LogoutServlet extends HttpServlet {
    /**
     * O fluxo para fazer logout consiste em:
     * 1. Obter o Object da sessão
     * 2. Obter a sessão do usuário com esse objeto
     * 3. Invalida a sessão
     * 4. Exibe uma mensagem de despedida ou  Redireciona para a página inicial
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            session.invalidate();
            response.getWriter().println("<p> Volte sempre, " + usuario.getNome() + " :) </p>");
        } else {
            response.getWriter().println("<p> Você não está logado </p>");
        }
    }//method
}//class

