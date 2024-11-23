package br.com.uanderson.cookieandsession;

import br.com.uanderson.cookieandsession.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ComputadorServlet", value = "/computador")
public class ComputadorServlet extends HttpServlet {

    private static final Product COMPUTER = new Product(
            "Notebook Dell XPS 13",
            5999.99,
            "Notebook premium com Intel Core i7, 16GB RAM, 512GB SSD"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("name_user") == null) {
            Cookie cookie = new Cookie("lastVisited", "computador");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Computador</title>");
            out.println("<link rel='stylesheet' href='css/styles.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>Detalhes do Computador</h1>");
            out.println("<div class='product-card'>");
            out.println("<h2>" + COMPUTER.getName() + "</h2>");
            out.println("<p class='price'>R$ " + String.format("%.2f", COMPUTER.getPrice()) + "</p>");
            out.println("<p class='description'>" + COMPUTER.getDescription() + "</p>");
            out.println("</div>");
            out.println("<nav class='menu'>");
            out.println("<a href='perfume'>Ver Perfumes</a>");
            out.println("<a href='promocao'>Ver Promoções</a>");
            if (session != null && session.getAttribute("name_user") != null) {
                out.println("<a href='logout'>Sair</a>");
            }
            out.println("</nav>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}