package br.com.uanderson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MostraXmlServlet", value = "/mostra-xml")
public class MostraXmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String xmlFilePath = getServletContext().getRealPath("/WEB-INF/nota.xml");
        
        XMLDocumentManager xmlManager = new XMLDocumentManager(xmlFilePath);

        // Reading examples
        out.println("Reading all elements:" + "<br>");
        out.println(xmlManager.readAllElements() + "<br> <hr>");

        out.println("\nReading specific element 'de':");
        out.println(xmlManager.readElementByName("de") + "<br> <hr>");

        // Modification examples
        out.println("\nAdding new elements:");
        xmlManager.addDateElement();
        xmlManager.addNewElement("assunto", "Importante");

        // Update example
        xmlManager.updateElement("corpo", "Texto atualizado da mensagem");

        // Show modified content
        out.println("\nAfter modifications:");
        out.println(xmlManager.readAllElements() + "<br> <hr>");

        // Save changes
        xmlManager.saveXMLFile(xmlFilePath + ".modified.xml");

        out.println(xmlManager.getAllAttributes() + "<br> <hr>");
        out.println(xmlManager.getAttributesByName("carta") + "<br> <hr>");

        xmlManager.addClasseAttribute("teste");

        out.println(xmlManager.getAllAttributes() + "<br> <hr>");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}