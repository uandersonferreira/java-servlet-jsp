package br.com.uanderson.exemplocrudaula.servlet.product;

import br.com.uanderson.exemplocrudaula.model.Produto;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AtualizarProdutoServlet", value = "/atualizarproduto")
public class AtualizarProdutoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configura o tipo de resposta para HTML com encoding UTF-8
        response.setContentType("text/html;charset=UTF-8");

        // Obtém o escritor para enviar a resposta
        try (PrintWriter out = response.getWriter()) {
            // Recupera os parâmetros enviados no formulário
            String tId = request.getParameter("id");
            String nome = request.getParameter("nome");
            String tPreco = request.getParameter("preco");

            // Valida se os parâmetros necessários foram informados
            if (tId == null || tId.isBlank() || nome == null || nome.isBlank() || tPreco == null || tPreco.isBlank()) {
                out.println("<p>Todos os campos (ID, Nome e Preço) são obrigatórios.</p>");
                return;
            }

            // Recupera a lista de produtos do contexto da aplicação
            ServletContext aplicacao = getServletContext();
            List<Produto> produtos = (List<Produto>) aplicacao.getAttribute("produtos");

            if (produtos == null) {
                out.println("<p>Erro interno: lista de produtos não encontrada.</p>");
                return;
            }

            // Converte os parâmetros para os tipos adequados
            int id;
            double preco;
            try {
                id = Integer.parseInt(tId);
                preco = Double.parseDouble(tPreco);
            } catch (NumberFormatException e) {
                out.println("<p>ID e Preço devem ser valores numéricos válidos.</p>");
                return;
            }

            // Encontra o produto pelo ID e atualiza seus dados
            boolean produtoAtualizado = false;
            for (Produto produto : produtos) {
                if (produto.getId() == id) {
                    produto.setNome(nome);
                    produto.setPreco(preco);
                    produtoAtualizado = true;
                    break;
                }
            }

            // Envia a resposta ao cliente
            if (produtoAtualizado) {
                out.println("<p>Produto atualizado com sucesso!</p>");
            } else {
                out.println("<p>Produto com ID " + id + " não encontrado.</p>");
            }
        }
    }
}
