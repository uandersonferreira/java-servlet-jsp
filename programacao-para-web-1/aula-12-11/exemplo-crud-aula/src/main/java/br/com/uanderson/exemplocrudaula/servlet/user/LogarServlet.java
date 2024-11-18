package br.com.uanderson.exemplocrudaula.servlet.user;

import br.com.uanderson.exemplocrudaula.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "LogarServlet", value = "/logar")
public class LogarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try (PrintWriter out = response.getWriter()) {
            //get parameters (login, senha) for authentication user
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
                ServletContext aplicacao = getServletContext();

                List<Usuario> usuarios = (List<Usuario>) aplicacao.getAttribute("usuarios");

                boolean logou = false;
                for (Usuario user : usuarios) {
                    if (user.getLogin().equals(login) && user.getSenha().equals(senha)) {
                        HttpSession session = request.getSession(); // Cria ou recupera a sessão HTTP do usuário
                        session.setAttribute("usuario", user); // Armazena o usuário logado na sessão
                        out.print("<p> Logado com sucesso. </p>");
                        logou = true;
                        break; // Encerra o loop após encontrar o usuário
                    }
                }

                // Se nenhum usuário foi encontrado, exibe uma mensagem de erro
                if (!logou) {
                    out.print("<p>Usuario ou senha incorretos");
                }

            } else {
                // Mensagem de erro para caso os campos estejam vazios ou nulos
                out.println("<p>Falha ao logar. Você precisa informar o login e senha");
            }

        }//try
    }//method
}//class