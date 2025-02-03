package br.com.uanderson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MostrarTodosServlet", value = "/MostrarTodosServlet")
public class MostrarTodosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String xmlPathFile = getServletContext().getRealPath("/WEB-INF/listas.xml");
            ListaXmlProcessor listaXmlProcessor = new ListaXmlProcessor(xmlPathFile);
            String allListas = listaXmlProcessor.getAllListas();
            out.print(allListas);
        }
    }

}