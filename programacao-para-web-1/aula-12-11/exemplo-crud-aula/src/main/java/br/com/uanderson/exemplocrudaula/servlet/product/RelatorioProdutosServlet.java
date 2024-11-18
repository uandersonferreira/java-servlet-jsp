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

@WebServlet(name = "RelatorioProdutosServlet", value = "/relatorioprodutos")
public class RelatorioProdutosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configura o tipo de resposta para HTML com encoding UTF-8
        response.setContentType("text/html;charset=UTF-8");

        // Recupera a lista de produtos do contexto da aplicação
        ServletContext aplicacao = getServletContext();
        List<Produto> produtos = (List<Produto>) aplicacao.getAttribute("produtos");

        // Usa StringBuilder para construir o HTML
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
                .append("<html lang=\"en\">")
                .append("<head>")
                .append("<meta charset=\"UTF-8\">")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .append("<title>Relatório de Produtos</title>")
                .append("<style>")
                .append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }")
                .append("th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }")
                .append("th { background-color: #f2f2f2; }")
                .append("a { color: red; text-decoration: none; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h1>Relatório de Produtos</h1>")
                .append("<table>")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Id</th>")
                .append("<th>Nome</th>")
                .append("<th>Preço</th>")
                .append("<th>Delete (Link)</th>")
                .append("<th>Atualização (Link)</th>")
                .append("<th>Delete (Formulário)</th>")
                .append("<th>Update (Formulário)</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        // Gera dinamicamente as linhas da tabela
        if (produtos != null && !produtos.isEmpty()) {
            for (Produto p : produtos) {
                html.append("<tr>")
                        .append("<td>").append(p.getId()).append("</td>")
                        .append("<td>").append(p.getNome()).append("</td>")
                        .append("<td>").append(p.getPreco()).append("</td>")
                        .append("<td><a href='deletarproduto?id=").append(p.getId()).append("'>Deletar</a></td>")
                        .append("<td><a href='editarproduto?id=").append(p.getId()).append("'>Editar</a></td>")
                        .append("<td>")
                        .append("<form action=\"deletarproduto\" method=\"get\">")
                        .append("<input type=\"hidden\" name=\"id\" value='").append(p.getId()).append("'>")
                        .append("<input type=\"submit\" value=\"Deletar\">")
                        .append("</form>")
                        .append("</td>")
                        .append("<td>")
                        .append("<form action=\"editarproduto\" method=\"get\">")
                        .append("<input type=\"hidden\" name=\"id\" value='").append(p.getId()).append("'>")
                        .append("<input type=\"submit\" value=\"Editar\">")
                        .append("</form>")
                        .append("</td>")
                        .append("</tr>");
            }
        } else {
            html.append("<tr><td colspan=\"5\">Nenhum produto disponível.</td></tr>");
        }

        html.append("</tbody>")
                .append("</table>")
                .append("</body>")
                .append("</html>");

        // Envia o conteúdo HTML ao cliente
        try (PrintWriter out = response.getWriter()) {
            out.print(html.toString());
        }
    }
}
