package br.com.uanderson.cookieandsession;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PromocaoServlet", value = "/promocao")
public class PromocaoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            HttpSession session = request.getSession(false);

            if (session != null){
                String lastVisited = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("lastVisited")) {
                            lastVisited = cookie.getValue();
                            break;
                        }
                    }
                }

                double originalPrice = 0;
                String productName = "";
                String description = "";

                if ("computador".equals(lastVisited)) {
                    originalPrice = 5999.99;
                    productName = "Notebook Dell XPS 13";
                    description = "Notebook premium com Intel Core i7, 16GB RAM, 512GB SSD";
                } else if ("perfume".equals(lastVisited)) {
                    originalPrice = 899.99;
                    productName = "Channel No. 5";
                    description = "Perfume feminino clássico com notas florais";
                }

                double discountedPrice = originalPrice * 0.9;


                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Promoção</title>");
                out.println("<link rel='stylesheet' href='css/styles.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>Promoção Especial</h1>");
                if (lastVisited != null) {
                    out.println("<div class='promocao-card'>");
                    out.println("<h2>" + productName + "</h2>");
                    out.println("<p class='description'>" + description + "</p>");
                    out.println("<p class='original-price'>Preço original: R$ " +
                            String.format("%.2f", originalPrice) + "</p>");
                    out.println("<p class='discount'>Desconto: 10%</p>");
                    out.println("<p class='final-price'>Preço final: R$ " +
                            String.format("%.2f", discountedPrice) + "</p>");
                    out.println("</div>");
                } else {
                    out.println("<p>Visite nossos produtos para ver promoções especiais!</p>");
                }
                out.println("<nav class='menu'>");
                out.println("<a href='computador'>Ver Computadores</a>");
                out.println("<a href='perfume'>Ver Perfumes</a>");
                out.println("<a href='logout'>Sair</a>");
                out.println("</nav>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }else {
                out.println("<h1>Realize o login para ter acesso!</h1> <br> ");
                out.println("<a href='index.html'>Voltar para a Home</a>");
            }

        }//try-with-resources
    }//method
}//class