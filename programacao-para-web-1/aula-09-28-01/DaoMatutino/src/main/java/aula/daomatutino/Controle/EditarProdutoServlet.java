package aula.daomatutino.Controle;

import aula.daomatutino.Dao.ErroDao;
import aula.daomatutino.Dao.ProdutoDaoClasse;
import aula.daomatutino.Dao.ProdutoDaoInterface;
import aula.daomatutino.Modelo.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "EditarProdutoServlet", value = "/editar")
public class EditarProdutoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("produtos?mensagem=ID+inválido");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            ProdutoDaoInterface dao = new ProdutoDaoClasse();
            Produto produto = dao.buscar(id);
            dao.sair();

            if (produto == null) {
                response.sendRedirect("produtos?mensagem=Produto+não+encontrado");
                return;
            }

            request.setAttribute("produto", produto);
            request.getRequestDispatcher("/WEB-INF/formulario.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("produtos?mensagem=ID+inválido+" + idParam);
        } catch (ErroDao e) {
            response.sendRedirect("produtos?mensagem=Erro+no+banco+de+dados");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String tpreco = request.getParameter("preco");

        // Validações
        if (idParam == null || idParam.isEmpty() ||
                nome == null || nome.isBlank() ||
                descricao == null || descricao.isBlank() ||
                tpreco == null || tpreco.isBlank()) {

            response.sendRedirect("produtos?mensagem=Preencha+todos+os+campos");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            float preco = Float.parseFloat(tpreco);

            if (preco <= 0) {
                response.sendRedirect("produtos?mensagem=Preço+inválido");
                return;
            }

            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);

            ProdutoDaoInterface dao = new ProdutoDaoClasse();
            dao.editar(produto);
            dao.sair();

            response.sendRedirect("produtos?mensagem=Produto+atualizado+com+sucesso");

        } catch (NumberFormatException e) {
            response.sendRedirect("produtos?mensagem=Dados+numéricos+inválidos");
        } catch (ErroDao e) {
            response.sendRedirect("produtos?mensagem=Erro+ao+atualizar+produto");
        }
    }
}