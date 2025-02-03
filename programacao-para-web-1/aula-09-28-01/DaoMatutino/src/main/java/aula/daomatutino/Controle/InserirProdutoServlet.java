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

@WebServlet(name = "InserirProdutoServlet", value = "/inserir")
public class InserirProdutoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/formulario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String tpreco = request.getParameter("preco");

        if (nome == null || nome.isBlank() ||
                descricao == null || descricao.isBlank() ||
                tpreco == null || tpreco.isBlank()) {

            response.sendRedirect("produtos?mensagem=Preencha+todos+os+campos");
            return;
        }

        try {
            float preco = Float.parseFloat(tpreco);
            if (preco <= 0) {
                response.sendRedirect("produtos?mensagem=Preço+deve+ser+maior+que+zero");
                return;
            }

            Produto p = new Produto(nome, descricao, preco);
            ProdutoDaoInterface dao = new ProdutoDaoClasse();
            dao.inserir(p);
            dao.sair();
            response.sendRedirect("produtos?mensagem=Produto+inserido+com+sucesso");

        } catch (NumberFormatException e) {
            response.sendRedirect("produtos?mensagem=Preço+inválido");
        } catch (ErroDao e) {
            response.sendRedirect("produtos?mensagem=Erro+ao+salvar+no+banco+de+dados");
        }
    }
}