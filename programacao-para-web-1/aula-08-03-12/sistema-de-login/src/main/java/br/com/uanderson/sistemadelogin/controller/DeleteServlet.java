package br.com.uanderson.sistemadelogin.controller;

import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteServlet", value = "/deletarusuario")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String tid = request.getParameter("id");

        if (tid != null && !tid.isBlank()) {
            HttpSession session = request.getSession();
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

            if (usuarioLogado == null) {
                response.sendRedirect("index.jsp?mensagem=alerta-nao-autenticado");
                return;
            }

            int id = Integer.parseInt(tid);

            if (usuarioLogado.getId() != id) {
                response.sendRedirect("relatorio.jsp?mensagem=alerta-permissao");
                return;
            }

            ServletContext application = getServletContext();
            List<Usuario> usuarios = (List<Usuario>) application.getAttribute("usersContext");

            if (usuarios != null) {
                usuarios.removeIf(u -> u.getId() == id);
                session.invalidate();
                response.sendRedirect("index.jsp?mensagem=success-delete-user");
                return;
            }
        }

        response.sendRedirect("index.jsp?mensagem=erro-delete-user"); // Erro ao deletar usuário pois usuário não encontrado
    }
}
