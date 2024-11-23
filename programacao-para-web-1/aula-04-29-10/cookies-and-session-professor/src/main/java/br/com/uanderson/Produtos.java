package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Produtos", value = "/produtos")
public class Produtos extends HttpServlet {
    //Page home que mostra os produtos

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()){

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Shampoo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Produtos </h1>");
            out.println("<a href='shampoo'> Shampoo </a>");

            // get cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null){
                for (Cookie cookie: cookies){
                    // discount for product shampoo if cookie and value for cookie is shampoo
                    if (cookie.getName().equals("produto") && cookie.getValue().equals("shampoo")){
                        out.println("<h2> Desconto de 10% no shampoo </h2>");
                    }
                }
            }

            out.println("</body>");
            out.println("</html>");

        }

    }//method

}//class
