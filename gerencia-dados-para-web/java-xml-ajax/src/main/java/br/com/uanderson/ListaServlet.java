package br.com.uanderson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ListaServlet", value = "/ListaServlet")
public class ListaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String listaId = request.getParameter("id");

        try (PrintWriter out = response.getWriter()) {

            if (listaId != null) {
                int id = Integer.parseInt(listaId);
                String xmlPathFile = getServletContext().getRealPath("/WEB-INF/listas.xml");
                ListaXmlProcessor listaXmlProcessor = new ListaXmlProcessor(xmlPathFile);
                String lista = listaXmlProcessor.getListById(id);
                out.print(lista);
            } else {
                out.print("Erro: Lista não encontrada.");
            }
        }

    }//method

}//class