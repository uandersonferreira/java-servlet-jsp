package br.com.uanderson.sistemadelogin.controller;

import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CadastrarServlet", value = "/cadastrarusuario")
public class CadastrarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (nome != null && !nome.isBlank() && login != null && !login.isBlank() &&
                senha != null && !senha.isBlank()) {

            ServletContext servletContext = getServletContext();

            List<Usuario> usersContext = (List<Usuario>) servletContext.getAttribute("usersContext");

            Usuario usuario = new Usuario(pegaProximoId(servletContext), nome, login, senha);

            usersContext.add(usuario);

            response.sendRedirect("index.jsp?mensagem=success-create");

        } else {
            response.sendRedirect("index.jsp?mensagem=alerta-campos");
        }

    }//method

    private Long pegaProximoId(ServletContext aplication) {
        Long serial = (Long) aplication.getAttribute("serial");
        serial++;
        aplication.setAttribute("serial", serial);
        return serial;
    }

}//class