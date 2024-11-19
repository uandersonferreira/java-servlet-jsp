package br.com.uanderson.exemplocrudaula.servlet.user;

import br.com.uanderson.exemplocrudaula.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            HttpSession session = request.getSession(false); // Não cria uma nova sessão, apenas recupera uma existente
            if (session != null && session.getAttribute("usuario") != null) {
                // Se já existe um usuário logado
                out.print("<p>Já existe um usuário logado na sessão. Faça logout antes de tentar novamente.</p>");
                return; // Encerra o processamento
            }

            // Recupera parâmetros (login, senha) para autenticar o usuário
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
                ServletContext aplicacao = getServletContext();
                List<Usuario> usuarios = (List<Usuario>) aplicacao.getAttribute("usuarios");

                boolean logou = false;
                for (Usuario user : usuarios) {
                    if (user.getLogin().equals(login) && user.getSenha().equals(senha)) {
                        session = request.getSession(); // Cria uma nova sessão se não houver
                        session.setAttribute("usuario", user); // Armazena o usuário logado na sessão
                        out.print("<p>Logado com sucesso.</p>");
                        logou = true;
                        break;
                    }
                }

                if (!logou) {
                    out.print("<p>Usuário ou senha incorretos.</p>");
                }

            } else {
                out.println("<p>Falha ao logar. Você precisa informar o login e senha.</p>");
            }
        }
    }

}//class