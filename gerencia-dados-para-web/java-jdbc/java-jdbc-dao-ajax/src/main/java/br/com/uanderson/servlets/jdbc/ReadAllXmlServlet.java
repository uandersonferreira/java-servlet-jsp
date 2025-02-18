package br.com.uanderson.servlets;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.impl.PessoaDaoAjaxImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ReadAllXmlServlet", value = "/pessoas-xml")
public class ReadAllXmlServlet extends HttpServlet {
    private final PessoaDaoAjax pessoaDaoAjax = new PessoaDaoAjaxImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/xml;charset=UTF-8");
        String xml = ((PessoaDaoAjaxImpl) pessoaDaoAjax).convertToXml();
        response.getWriter().write(xml);
    }
}