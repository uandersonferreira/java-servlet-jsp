package br.com.uanderson.sistemadelogin.controller;

import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        if (usuarioLogado != null) {
            session.invalidate();
            String nameUser = URLEncoder.encode(usuarioLogado.getNome(), "utf-8");
            response.sendRedirect("index.jsp?mensagem=success-logout&name=" + nameUser);
        } else {
            response.sendRedirect("index.jsp?mensagem=usuario-nao-autenticado");
        }
    }//method

}//class