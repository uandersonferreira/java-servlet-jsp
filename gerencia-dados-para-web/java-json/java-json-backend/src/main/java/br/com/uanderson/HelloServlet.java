package br.com.uanderson;

import br.com.uanderson.jsonP.exemplo01.model.Pessoa;
import br.com.uanderson.jsonP.exemplo01.processor.JsonPessoaManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //get nome e idade parametros
        String nome = request.getParameter("nome");
        String idadeText = request.getParameter("idade");

        JsonPessoaManager jsonPessoaManager = new JsonPessoaManager();
        Pessoa pessoa = new Pessoa.PessoaBuilder().nome(nome).idade(Integer.parseInt(idadeText)).build();
        String pessoaToJson = jsonPessoaManager.pessoaToJson(pessoa);

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        System.out.println(pessoaToJson);
        out.println("<h1>" + pessoaToJson + "</h1>");

        out.println("</body></html>");

        response.sendRedirect("/?ok");
    }
}