package br.com.uanderson.exemplocrudaula.servlet.product;

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
import java.util.ArrayList;
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

        // Recupera os parâmetros de filtragem
        String precoMin = request.getParameter("precoMin");
        String precoMax = request.getParameter("precoMax");

        Double precoMinimo = null;
        Double precoMaximo = null;

        if (precoMin != null && !precoMin.isBlank()) {
            precoMinimo = Double.parseDouble(precoMin);
        }
        if (precoMax != null && !precoMax.isBlank()) {
            precoMaximo = Double.parseDouble(precoMax);
        }

        // Filtra os produtos com base nos parâmetros
        List<Produto> produtosFiltrados = new ArrayList<>();
        if (produtos != null) {
            for (Produto produto : produtos) {
                boolean dentroDoFiltro = true;

                if (precoMinimo != null && produto.getPreco() < precoMinimo) {
                    dentroDoFiltro = false;
                }
                if (precoMaximo != null && produto.getPreco() > precoMaximo) {
                    dentroDoFiltro = false;
                }

                if (dentroDoFiltro) {
                    produtosFiltrados.add(produto);
                }
            }
        }

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
                .append("<form action=\"relatorioprodutos\" method=\"get\">")
                .append("<label for=\"precoMin\">Preço Mínimo:</label>")
                .append("<input type=\"number\" name=\"precoMin\" id=\"precoMin\" placeholder=\"Digite o preço mínimo\" min=\"0\" step=\"0.01\">")
                .append("<label for=\"precoMax\">Preço Máximo:</label>")
                .append("<input type=\"number\" name=\"precoMax\" id=\"precoMax\" placeholder=\"Digite o preço máximo\" min=\"0\" step=\"0.01\">")
                .append("<input type=\"submit\" value=\"Filtrar\">")
                .append("</form>")
                .append("<table>")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Id</th>")
                .append("<th>Nome</th>")
                .append("<th>Preço</th>")
                .append("<th>Delete (Link)</th>")
                .append("<th>Atualização (Link)</th>")
                .append("<th>Cadastrar Produto</th>")
                .append("<th>Delete (Formulário)</th>")
                .append("<th>Update (Formulário)</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        // Gera dinamicamente as linhas da tabela
        if (produtosFiltrados != null && !produtosFiltrados.isEmpty()) {
            for (Produto p : produtosFiltrados) {
                html.append("<tr>")
                        .append("<td>").append(p.getId()).append("</td>")
                        .append("<td>").append(p.getNome()).append("</td>")
                        .append("<td>").append(p.getPreco()).append("</td>")
                        .append("<td><a href='deletarproduto?id=").append(p.getId()).append("'>Deletar</a></td>")
                        .append("<td><a href='editarproduto?id=").append(p.getId()).append("'>Editar</a></td>")
                        .append("<td><a href='cadastrarproduto'>Cadastrar Produto</a></td>")
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
            html.append("<tr><td colspan=\"8\">Nenhum produto disponível.</td></tr>");
        }

        html.append("</tbody>")
                .append("</table>");

        // Exibe informações do usuário logado, se disponível
        HttpSession session = request.getSession(false);
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            html.append("<a href='deleteuser?id=").append(usuario.getId()).append("'>Deletar Usuário</a>");
            html.append("<br>");
            html.append(usuario);
        }

        html.append("</body>")
                .append("</html>");

        // Envia o conteúdo HTML ao cliente
        try (PrintWriter out = response.getWriter()) {
            out.print(html.toString());
        }
    }
}
