package br.com.uanderson.exemplocrudaula.servlet.user;

import br.com.uanderson.exemplocrudaula.model.Produto;
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

@WebServlet(name = "DeletarUsuarioServlet", value = "/deleteuser")
public class DeletarUsuarioServlet extends HttpServlet {
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

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                String tId = request.getParameter("id");

                if (tId != null && !tId.isBlank()) {

                    ServletContext aplicacao = getServletContext();

                    List<Usuario> usuarios = (List<Usuario>) aplicacao.getAttribute("usuarios");

                    int id = Integer.parseInt(tId);

                    // Crie um objeto Produto com o ID a ser removido, pois os produtos são iguais apenas pelo ID
                    Usuario usuarioParaRemover = new Usuario(id, "", "", "");

                    if (usuarios.remove(usuarioParaRemover)) { //são iguais pelo ID
                        session.invalidate(); //invalido a session após remover do context da aplicação
                        out.println("<p>Removido com sucesso.");
                        for (Usuario usuario : usuarios) {
                            out.println("<p> Usuário: " +  usuario + " </p>");
                        }

                    } else {
                        out.println("<p>Falha ao remover. Usuario não encontrado");
                    }

                } else {
                    out.println("<p>Falha ao Remover. Você precisa informar o Id");
                }
            } else {
                out.print("<p>Você precisa estar logado.");
            }

        } catch (NumberFormatException e) {
            response.getWriter().println("<p>ID inválido. Deve ser um número inteiro.</p>");
        }
    }//method
}//class

