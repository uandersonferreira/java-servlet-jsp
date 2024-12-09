package br.com.uanderson.sistemadelogin.controller;

import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UpdateServlet", value = "/editarusuario")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Verifica se o usuário está logado
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (Usuario) (session != null ? session.getAttribute("usuario") : null);

        if (usuarioLogado == null) {
            response.sendRedirect("index.jsp?mensagem=usuario-nao-autenticado");
            return;
        }

        String tid = request.getParameter("id");
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (tid != null && !tid.isBlank() && nome != null && !nome.isBlank() &&
                login != null && !login.isBlank() && senha != null && !senha.isBlank()) {

            ServletContext application = getServletContext();
            List<Usuario> usuarios = (List<Usuario>) application.getAttribute("usersContext");

            int id = Integer.parseInt(tid);


            for (Usuario u : usuarios) {
                if (u.getId() == id) {
                    u.setNome(nome);
                    u.setLogin(login);
                    u.setSenha(senha);
                    response.sendRedirect("relatorio.jsp?mensagem=success-update-user");
                    return;
                }
            }
            response.sendRedirect("relatorio.jsp?mensagem=erro-update-user");
        } else {
            // Preserva os dados no request
            request.setAttribute("id", tid);
            request.setAttribute("nome", nome);
            request.setAttribute("login", login);
            request.setAttribute("senha", senha);

            // Redireciona de volta para o JSP de edição usando RequestDispatcher
            RequestDispatcher dispatcher = request.getRequestDispatcher("editar.jsp?mensagem=alerta-campos");
            dispatcher.forward(request, response);
        }
    }
}
