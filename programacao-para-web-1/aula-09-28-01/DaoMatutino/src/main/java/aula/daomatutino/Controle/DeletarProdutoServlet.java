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

@WebServlet(name = "DeletarProdutoServlet", value = "/deletar")
public class DeletarProdutoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String textId = request.getParameter("id");

        if (textId == null || textId.isEmpty()) {
            response.sendRedirect("produtos?mensagem=ID+inválido+para+exclusão");
            return;
        }

        try {
            int id = Integer.parseInt(textId);
            ProdutoDaoInterface dao = new ProdutoDaoClasse();
            Produto produtoSalvo = dao.buscar(id);

            if (produtoSalvo == null) {
                response.sendRedirect("produtos?mensagem=Produto+não+encontrado+ID+" + id);
                return;
            }

            dao.deletar(id);
            dao.sair();
            response.sendRedirect("produtos?mensagem=Produto+excluído+com+sucesso");

        } catch (NumberFormatException e) {
            response.sendRedirect("produtos?mensagem=ID+inválido+" + textId);
        } catch (ErroDao e) {
            response.sendRedirect("produtos?mensagem=Erro+no+banco+de+dados");
        }
    }
}