package aula.daomatutino.Controle;

import aula.daomatutino.Dao.ErroDao;
import aula.daomatutino.Dao.ProdutoDaoClasse;
import aula.daomatutino.Dao.ProdutoDaoInterface;
import aula.daomatutino.Modelo.Produto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/produto")
public class MostraProduto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ProdutoDaoInterface dao=new ProdutoDaoClasse();
            List<Produto> produtos=dao.buscar();
            request.setAttribute("produtos",produtos);
            dao.sair();
            request.getRequestDispatcher("WEB-INF/produto.jsp").forward(request,response);
        } catch (ErroDao e) {
            request.getRequestDispatcher("WEB-INF/produto.jsp?mensagem=Erro ao tentar acessar os dados").forward(request,response);
        }



    }
}
