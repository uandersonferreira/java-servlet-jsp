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

@WebServlet(name = "EditarProdutoServlet", value = "/editarproduto")
public class EditarProdutoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            HttpSession session = request.getSession(false);
            if (session != null) {
                // Obtém o ID do produto a ser editado
                String tId = request.getParameter("id");
                if (tId == null || tId.isBlank()) {
                    out.println("<p>Você precisa informar o ID do produto.</p>");
                    return;
                }

                // Recupera a lista de produtos do contexto da aplicação
                ServletContext aplicacao = getServletContext();
                List<Produto> produtos = (List<Produto>) aplicacao.getAttribute("produtos");

                if (produtos == null) {
                    out.println("<p>Erro interno: lista de produtos não encontrada.</p>");
                    return;
                }

                // Converte o ID para inteiro e busca o produto
                int id;
                try {
                    id = Integer.parseInt(tId);
                } catch (NumberFormatException e) {
                    out.println("<p>ID inválido. Deve ser um número inteiro.</p>");
                    return;
                }

                Produto produtoEncontrado = null;
                for (Produto produto : produtos) {
                    if (produto.getId() == id) {
                        produtoEncontrado = produto;
                        break;
                    }
                }

                if (produtoEncontrado == null) {
                    out.println("<p>Produto com ID " + id + " não encontrado.</p>");
                    return;
                }

                // Gera o formulário com os dados do produto preenchidos
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Editar Produto</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Editar Produto</h1>");
                out.println("<form action='atualizarproduto' method='post'>");
                out.println("<label for='id'>ID:</label>");
                out.println("<input type='number' id='id' name='id' value='" + produtoEncontrado.getId() + "' readonly><br><br>");
                out.println("<label for='nome'>Nome:</label>");
                out.println("<input type='text' id='nome' name='nome' value='" + produtoEncontrado.getNome() + "' required><br><br>");
                out.println("<label for='preco'>Preço:</label>");
                out.println("<input type='number' id='preco' name='preco' step='0.01' value='" + produtoEncontrado.getPreco() + "' required><br><br>");
                out.println("<button type='submit'>Atualizar</button>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");

            } else {
                out.print("<p>Você precisa estar logado.");
            }

        }
    }
}
