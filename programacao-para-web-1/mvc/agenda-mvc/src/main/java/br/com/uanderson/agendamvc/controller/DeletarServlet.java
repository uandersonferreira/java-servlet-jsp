package br.com.uanderson.agendamvc.controller;

import br.com.uanderson.agendamvc.model.Agenda;
import br.com.uanderson.agendamvc.model.Contato;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.Charset;

@WebServlet(name = "DeletarServlet", value = "/deletar")
public class DeletarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        //recuperar o atributo agendaContext do contexto da aplicação criado no Listener ao iniciar a aplicação
        Agenda agendaContext = (Agenda) getServletContext().getAttribute("agendaContext");

        String codigoText = request.getParameter("codigo");

        if (codigoText != null) {
            int codigo = Integer.parseInt(codigoText);
            try {
                agendaContext.removerContato(codigo);
                response.sendRedirect("listar-agenda.jsp");
            } catch (Exception e) {
                response.sendRedirect("listar-agenda.jsp?mensagem=naoexiste&causeerror=" + e.getMessage());
            }
        }else {
            response.sendRedirect("index.html");
        }

    }//method

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}