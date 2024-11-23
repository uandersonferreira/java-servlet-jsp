package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Shampoo", value = "/shampoo")
public class Shampoo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()){
            //create cookie
            Cookie cookie =  new Cookie("produto", "shampoo");
            cookie.setMaxAge(60*60*24); // 1 day
            response.addCookie(cookie); // add cookie to response

            //create schema html for the page product shampoo
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Shampoo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Shampoo por R$ 10,00 </h1>");
            out.println("</body>");
            out.println("</html>");

        }

    }//method

}//class
