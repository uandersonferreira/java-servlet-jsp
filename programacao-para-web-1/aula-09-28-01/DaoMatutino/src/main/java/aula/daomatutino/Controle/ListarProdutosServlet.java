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
import java.util.List;

@WebServlet(name = "ListarProdutosServlet", value = "/produtos")
public class ListarProdutosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            ProdutoDaoInterface dao = new ProdutoDaoClasse();
            List<Produto> produtos = dao.buscar();
            dao.sair();

            request.setAttribute("produtos", produtos);

            // Tratamento de mensagens
            String mensagem = request.getParameter("mensagem");
            if (mensagem != null) {
                request.setAttribute("mensagem", mensagem);
            }

            request.getRequestDispatcher("/WEB-INF/listar.jsp").forward(request, response);

        } catch (ErroDao e) {
            request.setAttribute("mensagem", "Erro ao acessar o banco de dados");
            request.getRequestDispatcher("/WEB-INF/listar.jsp").forward(request, response);
        }
    }
}