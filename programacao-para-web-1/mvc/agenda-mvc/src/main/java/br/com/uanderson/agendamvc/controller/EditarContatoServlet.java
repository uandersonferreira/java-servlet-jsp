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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "EditarContatoServlet", value = "/editar")
public class EditarContatoServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditarContatoServlet.class.getName());
    private static final String LISTAR_AGENDA_URL = "listar-agenda.jsp";
    private static final String INDEX_URL = "index.html";
    private static final String EDITAR_JSP = "/editar.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoText = request.getParameter("codigo");
        Agenda agendaContext = (Agenda) getServletContext().getAttribute("agendaContext");

        if (agendaContext == null) {
            response.sendRedirect(LISTAR_AGENDA_URL + "?mensagem=agendaNaoEncontrada");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoText);
            Optional<Contato> contatoOptional = agendaContext.buscarContato(codigo);

            if (contatoOptional.isPresent()) {
                request.setAttribute("contato", contatoOptional.get());
                RequestDispatcher dispatcher = request.getRequestDispatcher(EDITAR_JSP);
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(LISTAR_AGENDA_URL + "?mensagem=naoexiste");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Código inválido: " + codigoText, e);
            response.sendRedirect(LISTAR_AGENDA_URL + "?mensagem=erroCodigoInvalido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoText = request.getParameter("codigo");
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        Agenda agendaContext = (Agenda) getServletContext().getAttribute("agendaContext");

        if (agendaContext == null) {
            response.sendRedirect(LISTAR_AGENDA_URL + "?mensagem=agendaNaoEncontrada");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoText);

            Optional<Contato> contatoOptional = agendaContext.buscarContato(codigo);
            if (contatoOptional.isPresent() && nome != null && telefone != null) {
                Contato contato = contatoOptional.get();
                contato.setNome(nome);
                contato.setTelefone(telefone);
                response.sendRedirect(LISTAR_AGENDA_URL + "?mensagem=sucesso-update");
            } else {
                response.sendRedirect(LISTAR_AGENDA_URL + "?mensagem=erroDadosInvalidos");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Código inválido: " + codigoText, e);
            response.sendRedirect(LISTAR_AGENDA_URL + "?mensagem=erroCodigoInvalido");
        }
    }
}

