package br.com.uanderson.agendamvc.controller;

import br.com.uanderson.agendamvc.model.Agenda;
import br.com.uanderson.agendamvc.model.Contato;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "EditarContatoServlet", value = "/editar")
public class EditarContatoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoText = request.getParameter("codigo");

        // Simulação de uma agenda (normalmente buscaria do banco de dados)
        Agenda agendaContext = (Agenda) getServletContext().getAttribute("agendaContext");

        Contato contato = null;
        if (agendaContext != null) {
            if (codigoText != null) {
                int codigo = Integer.parseInt(codigoText);
                try {
                    contato = agendaContext.buscarContato(codigo); // Método para buscar o contato
                } catch (Exception e) {
                    response.sendRedirect("listar-agenda.jsp?mensagem=naoexiste");
                }
            } else {
                response.sendRedirect("index.html");
            }
        }

        if (contato != null) {
            request.setAttribute("contato", contato); // Passa o contato para o JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/editar.jsp");
            dispatcher.forward(request, response);
            /*
              No dispacho os objetos request e response, são transferidos para a jsp,
              por isso que conseguimos pegar na jsp o objeto contato:
              -     Contato contato = (Contato) request.getAttribute("contato");
             */
        } else {
            response.sendRedirect("listar?mensagem=naoexiste");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoText = request.getParameter("codigo");
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        // Simulação de atualização na agenda
        Agenda agendaContext = (Agenda) getServletContext().getAttribute("agendaContext");

        if (agendaContext != null) {
            if (codigoText != null && nome != null && telefone != null) {
                int codigo = Integer.parseInt(codigoText);

                Contato contato = null; // Busca o contato existente
                try {
                    contato = agendaContext.buscarContato(codigo);
                } catch (Exception e) {
                    response.sendRedirect("listar-agenda.jsp?mensagem=naoexiste");
                }

                if (contato != null) {
                    // Atualiza os dados do contato
                    contato.setNome(nome);
                    contato.setTelefone(telefone);
                    response.sendRedirect("listar-agenda.jsp?mensagem=sucesso-update");
                    return;
                }
            }
            response.sendRedirect("listar-agenda.jsp?mensagem=naoexiste");

        }else {
            response.sendRedirect("listar-agenda.jsp?mensagem=naoexiste");
        }

    }//method

}//class
