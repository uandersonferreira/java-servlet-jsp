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

@WebServlet(name = "DeletarProduto", value = "/deletar")
public class DeletarProduto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String textId = request.getParameter("id");

        if (textId == null && textId.isEmpty()) {
            response.sendRedirect("produto?mensagem=Erro ao tentar deletar");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(textId);
            ProdutoDaoInterface dao = new ProdutoDaoClasse();
            Produto produtoSalvo = dao.buscar(id);
            if (produtoSalvo == null) {
                response.sendRedirect("produto?mensagem=Produto não encontrado com ID [" + id + "]");
                return;
            }
            dao.deletar(id);
            dao.sair();
            response.sendRedirect("produto?mensagem=Produto deletado com sucesso.");
        } catch (ErroDao e) {
            System.out.println("erroDAO: " + e);
            request.setAttribute("mensagem", "Erro ao tentar excluir o produto");
            request.getRequestDispatcher("WEB-INF/produto.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("produto?mensagem= [ERRO] ID {" + id + "} Inválido!");
        }
    }

}