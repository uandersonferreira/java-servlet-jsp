package br.com.uanderson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MostrarXmlPuroServlet", value = "/mostra-xml-puro")
public class MostrarXmlPuroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");

        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String xmlFilePath = getServletContext().getRealPath("/WEB-INF/nota.xml");

        XMLDocumentManager xmlManager = new XMLDocumentManager(xmlFilePath);

        xmlManager.serializarXML(out);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}