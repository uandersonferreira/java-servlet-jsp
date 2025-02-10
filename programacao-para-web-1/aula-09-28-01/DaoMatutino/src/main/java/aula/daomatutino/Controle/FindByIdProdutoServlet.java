package aula.daomatutino.Controle;

import aula.daomatutino.Dao.ErroDao;
import aula.daomatutino.Dao.ProdutoDaoClasse;
import aula.daomatutino.Modelo.Produto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "FindByIdProdutoServlet", value = "/find-by-id")
public class FindByIdProdutoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String idParam = request.getParameter("id");
        Produto produto = null;
        String mensagem = null;

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                ProdutoDaoClasse produtoDao = new ProdutoDaoClasse();
                produto = produtoDao.buscar(id);
                if (produto == null) {
                    mensagem = "Produto não encontrado.";
                }
            } catch (NumberFormatException | ErroDao e) {
                e.printStackTrace();
                mensagem = "ID inválido.";
            }
        } else {
            mensagem = "Informe um ID válido.";
        }

        request.setAttribute("produto", produto);
        request.setAttribute("mensagem", mensagem);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);

    }
}
