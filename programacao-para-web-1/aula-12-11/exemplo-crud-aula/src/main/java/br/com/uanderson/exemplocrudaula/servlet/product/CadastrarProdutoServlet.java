package br.com.uanderson.exemplocrudaula.servlet.product;

import br.com.uanderson.exemplocrudaula.Util;
import br.com.uanderson.exemplocrudaula.model.Produto;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CadastrarProdutoServlet", value = "/cadastrarproduto")
public class CadastrarProdutoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String nome = request.getParameter("nome");
            String tpreco = request.getParameter("preco");

            if (nome != null && !nome.isBlank() && tpreco != null && !tpreco.isBlank()) {
                ServletContext aplicacao = getServletContext();
                List<Produto> produtos = (List<Produto>) aplicacao.getAttribute("produtos");
                double preco = Double.parseDouble(tpreco);
                Produto p = new Produto(Util.proximoSerial(aplicacao), nome, preco);
                produtos.add(p);
                out.println("<p>Cadastrado com sucesso.");
            } else {
                out.println("<p>Falha ao cadastrar. Você precisa informar o nome e preço");
            }
        } else {
            out.print("<p>Você precisa estar logado.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("formularioproduto.html");
    }


}
