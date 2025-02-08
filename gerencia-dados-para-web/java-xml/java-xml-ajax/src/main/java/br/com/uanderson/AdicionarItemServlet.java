package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AdicionarItemServlet", value = "/AdicionarItemServlet")
public class AdicionarItemServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AdicionarItemServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        try {
            int listaId = Integer.parseInt(request.getParameter("listaId"));
            String itemText = request.getParameter("itemText");
            boolean feito = "sim".equals(request.getParameter("feito"));
            boolean prioridade = "sim".equals(request.getParameter("prioridade"));

            String xmlPathFile = getServletContext().getRealPath("/WEB-INF/listas.xml");
            ListaXmlProcessor processor = new ListaXmlProcessor(xmlPathFile);

            boolean success = processor.addItemToList(listaId, itemText, feito, prioridade);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Item adicionado com sucesso!");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falha ao adicionar item");
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