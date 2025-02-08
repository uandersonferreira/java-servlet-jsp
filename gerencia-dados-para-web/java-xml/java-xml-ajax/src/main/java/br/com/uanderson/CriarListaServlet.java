package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CriarListaServlet", value = "/CriarListaServlet")
public class CriarListaServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CriarListaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        try {
            int listId = Integer.parseInt(request.getParameter("listId"));
            String title = request.getParameter("title");

            String xmlPathFile = getServletContext().getRealPath("/WEB-INF/listas.xml");
            ListaXmlProcessor processor = new ListaXmlProcessor(xmlPathFile);

            boolean success = processor.addNewList(listId, title);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Lista criada com sucesso!");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falha ao criar lista");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "ID inválido", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro interno", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro: " + e.getMessage());
        }
    }
}