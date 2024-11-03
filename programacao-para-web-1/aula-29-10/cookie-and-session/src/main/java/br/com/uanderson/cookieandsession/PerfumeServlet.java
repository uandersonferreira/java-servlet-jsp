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

@WebServlet("/perfume")
public class PerfumeServlet extends HttpServlet {
    private static final Product PERFUME = new Product(
            "Channel No. 5",
            899.99,
            "Perfume feminino clássico com notas florais"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            Cookie cookie = new Cookie("lastVisited", "perfume");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Perfume</title>");
            out.println("<link rel='stylesheet' href='css/styles.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>Detalhes do Perfume</h1>");
            out.println("<div class='product-card'>");
            out.println("<h2>" + PERFUME.getName() + "</h2>");
            out.println("<p class='price'>R$ " + String.format("%.2f", PERFUME.getPrice()) + "</p>");
            out.println("<p class='description'>" + PERFUME.getDescription() + "</p>");
            out.println("</div>");
            out.println("<nav class='menu'>");
            out.println("<a href='computer'>Ver Computadores</a>");
            out.println("<a href='promotion'>Ver Promoções</a>");
            if (session != null && session.getAttribute("user") != null) {
                out.println("<a href='logout'>Sair</a>");
            }
            out.println("</nav>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}