package br.com.uanderson.agendamvc.controller;

import br.com.uanderson.agendamvc.model.Agenda;
import br.com.uanderson.agendamvc.model.Contato;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CadastroServlet", value = "/cadastro")
public class CadastroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        //recuperar o atributo agendaContext do contexto da aplicação criado no Listener ao iniciar a aplicação
        Agenda agendaContext = (Agenda) getServletContext().getAttribute("agendaContext");

        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        if (nome != null && telefone != null) {
            Contato contato = new Contato(nome, telefone);
            agendaContext.adicionarContato(contato);
            response.sendRedirect("listar-agenda.jsp");

        }else {
            response.sendRedirect("index.html");
        }
    }//method

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}