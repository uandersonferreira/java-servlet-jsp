package br.com.uanderson.servlets;

import br.com.uanderson.xml.LivrariaXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LivrariaXmlServlet", value = "/livraria-xml")
public class LivrariaXmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml;  charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String titulo = request.getParameter("titulo");

        try (PrintWriter out = response.getWriter()) {
            String xmlPathFile = getServletContext().getRealPath("/WEB-INF/livraria.xml");
            try {
                LivrariaXmlProcessor processor = new LivrariaXmlProcessor(xmlPathFile);
                out.println(processor.getBooksByTitle(titulo));
            } catch (Exception ex) {
                out.println("<erro>" + ex + "</erro>");
                ex.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}