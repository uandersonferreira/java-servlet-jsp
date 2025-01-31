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

@WebServlet("/inserir")
public class InserirProduto extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String nome=request.getParameter("nome");
        String descricao=request.getParameter("descricao");
        String tpreco=request.getParameter("preco");
        if(nome!=null && !nome.isBlank() && descricao!=null &&
                !descricao.isBlank() && tpreco!=null && !tpreco.isBlank())
        {
            Produto p = new Produto(nome,descricao,Float.parseFloat(tpreco));//é preciso tratar o erro de converter a string em float
            try {
                ProdutoDaoInterface dao = new ProdutoDaoClasse();
                dao.inserir(p);
                dao.sair();
                response.sendRedirect("produto?mensagem=Inserido com sucesso");
            } catch (ErroDao e) {
                response.sendRedirect("produto?mensagem=Erro ao tentar inserir");
            }
        }else{
            response.sendRedirect("produto?mensagem=Preencha todos os campos");
        }
    }
}
