package br.com.uanderson.sistemadelogin.controller;

import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteTelefoneServlet", value = "/deletartelefone")
public class DeleteTelefoneServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String idTelefoneText = request.getParameter("idTelefone");
        String idUsuarioText = request.getParameter("idUsuario");

        if (idTelefoneText != null && !idUsuarioText.isBlank() && idUsuarioText != null && !idUsuarioText.isBlank()) {
            HttpSession session = request.getSession();
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

            if (usuarioLogado == null) {
                response.sendRedirect("index.jsp?mensagem=alerta-nao-autenticado");
                return;
            }

            Long idUsuario = Long.parseLong(idUsuarioText);
            Long idTelefone = Long.parseLong(idTelefoneText);

            if (usuarioLogado.getId() != idUsuario ) {
                response.sendRedirect("relatorio.jsp?mensagem=alerta-permissao");
                return;
            }

            ServletContext application = getServletContext();
            List<Usuario> usuarios = (List<Usuario>) application.getAttribute("usersContext");

            if (usuarios != null) {
                for (Usuario usuario : usuarios) {
                    if (usuario.getTelefones() != null){
                        usuario.getTelefones().removeIf(t -> t.getId().equals(idTelefone));
                        break;
                    }
                }
                response.sendRedirect("relatorio.jsp?mensagem=success-delete-telefone");
                return;
            }
        }

        response.sendRedirect("relatorio.jsp?mensagem=erro-delete-telefone");
    }
}
