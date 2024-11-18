package br.com.uanderson.exemplocrudaula.servlet.product;

import br.com.uanderson.exemplocrudaula.model.Produto;
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

@WebServlet(name = "DeletarProdutoServlet", value = "/deletarproduto")
public class DeletarProdutoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                String tId = request.getParameter("id");

                if (tId != null && !tId.isBlank()) {

                    ServletContext aplicacao = getServletContext();

                    List<Produto> produtos = (List<Produto>) aplicacao.getAttribute("produtos");

                    int id = Integer.parseInt(tId);

                    // Crie um objeto Produto com o ID a ser removido, pois os produtos são iguais apenas pelo ID
                    Produto produtoParaRemover = new Produto(id, "", 0);

                    if (produtos.remove(produtoParaRemover)) { //são iguais pelo ID
                        out.println("<p>Removido com sucesso.");
                    } else {
                        out.println("<p>Falha ao remover. Produto não encontrado");
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
