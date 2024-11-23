package br.com.uanderson;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/*
 Também é possível definir parâmetros de inicialização para a Servlet
 através da anotação @WebServlet, mas desta forma, os parâmetros de inicialização
 ao sofre mudanças precisa compilar novamente o projeto, ao contrário dos parametros
 definidos no arquivo web.xml.

 ServletConfig: (Estamos falando de apenas um servlet, suas configurações no caso)
 - getServletConfig().getInitParameter(String name) ou
 - getServletConfig().getInitParameterNames() (Para mais de um parâmetro)

 ServletContext: (Estamos falando de toda a aplicação, suas configurações no caso)
 - getServletContext().getInitParameter(String name) ou
 - getServletContext().getInitParameterNames() (Para mais de um parâmetro)

 */
@WebServlet(name = "helloServlet", value = "/hello",
    initParams = {
        @WebInitParam(name = "nome", value = "Uanderson"),
        @WebInitParam(name = "idade", value = "36")
    }
)
public class HelloServletConfig extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World, ";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        // Recuperando os parâmetros de inicialização, definidos no arquivo web.xml
        String nome = getServletConfig().getInitParameter("nome"); // De um servlet específico
        String empresa = getServletContext().getInitParameter("empresa"); // Da aplicação toda

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + empresa + "</h1>");
        out.println("<h1>" + message + nome + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}