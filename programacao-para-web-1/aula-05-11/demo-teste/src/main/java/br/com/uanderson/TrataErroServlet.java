package br.com.uanderson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TrataErroServlet", value = "/trata-erro")
public class TrataErroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        Object statusCodeErro = request.getAttribute("jakarta.servlet.error.status_code");
        Object typeErro = request.getAttribute("jakarta.servlet.error.exception_type");
        Object messageErro = request.getAttribute("jakarta.servlet.error.message");
        Object requestUri = request.getAttribute("jakarta.servlet.error.request_uri");
        Object servletName = request.getAttribute("jakarta.servlet.error.servlet_name");
        Object throwable = request.getAttribute("jakarta.servlet.error.exception");
        Object throwableMessage = request.getAttribute("jakarta.servlet.error.exception_message");

        try (PrintWriter out = response.getWriter()) {
            out.println("<h1> Página de erro</h1>");
            out.println("<h2>Status Code: " + statusCodeErro + "</h2>");
            out.println("<p> Type: " + typeErro + "</p>");
            out.println("<p> Message: " + messageErro + "</p>");
            out.println("<p> Request URI: " + requestUri + "</p>");
            out.println("<p> Servlet Name: " + servletName + "</p>");
            out.println("<p> Throwable: " + throwable + "</p>");
            out.println("<p> Throwable Message: " + throwableMessage + "</p>");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}