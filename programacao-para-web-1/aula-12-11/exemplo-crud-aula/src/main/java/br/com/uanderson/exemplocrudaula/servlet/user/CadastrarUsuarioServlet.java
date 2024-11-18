package br.com.uanderson.exemplocrudaula.servlet.user;

import br.com.uanderson.exemplocrudaula.Util;
import br.com.uanderson.exemplocrudaula.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CadastrarUsuarioServlet", value = "/cadastrarusuario")
public class CadastrarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {

            request.setCharacterEncoding("utf-8");

            //get parameters (nome, login, senha)
            String nome = request.getParameter("nome");
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            //validate parameters
            if(nome != null && !nome.isEmpty() && login != null && !login.isEmpty() && senha != null && !senha.isEmpty()){
                ServletContext application = getServletContext();

                List<Usuario> usuarios = (List<Usuario>) application.getAttribute("usuarios");

                Usuario user = new Usuario(Util.proximoSerial(application), nome, login, senha);

                usuarios.add(user);

                out.println("<p>Cadastrado com sucesso.");

            } else {
                out.println("<p>Falha ao cadastrar. Você precisa informar o nome, login e senha");
            }

        }//try
    }//method



}//class