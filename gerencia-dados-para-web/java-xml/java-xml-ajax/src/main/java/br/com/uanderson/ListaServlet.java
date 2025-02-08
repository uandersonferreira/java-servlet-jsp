package br.com.uanderson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ListaServlet", value = "/ListaServlet")
public class ListaServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ListaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String listaId = request.getParameter("id");

        try (PrintWriter out = response.getWriter()) {

            if (listaId != null && !listaId.isEmpty()) {
                int id = Integer.parseInt(listaId);
                String xmlPathFile = getServletContext().getRealPath("/WEB-INF/listas.xml");
                ListaXmlProcessor listaXmlProcessor = new ListaXmlProcessor(xmlPathFile);
                String lista = listaXmlProcessor.getListById(id);
                out.print(lista);
            } else {
                out.print("Erro: Lista não encontrada.");
            }
        }

    }//method


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String action = request.getParameter("action");
        boolean success = false;
        String xmlPathFile = getServletContext().getRealPath("/WEB-INF/listas.xml");

        try {
            ListaXmlProcessor processor = new ListaXmlProcessor(xmlPathFile);

            switch (action) {
                case "addList":
                    success = processarNovaLista(request, processor);
                    break;

                case "addItem":
                    success = processarNovoItem(request, processor);
                    break;

                case "updateItem":
                    success = processarAtualizacaoItem(request, processor);
                    break;

                case "removeItem":
                    success = processarRemocaoItem(request, processor);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação inválida");
                    return;
            }

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Operação realizada com sucesso!");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falha na operação");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Erro de formato numérico", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro interno", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro: " + e.getMessage());
        }
    }

    private boolean processarNovaLista(HttpServletRequest request, ListaXmlProcessor processor) {
        int listId = Integer.parseInt(request.getParameter("listId"));
        String title = request.getParameter("title");
        return processor.addNewList(listId, title);
    }

    private boolean processarNovoItem(HttpServletRequest request, ListaXmlProcessor processor) {
        int listaId = Integer.parseInt(request.getParameter("listaId"));
        String itemText = request.getParameter("itemText");
        boolean feito = "sim".equals(request.getParameter("feito"));
        boolean prioridade = "sim".equals(request.getParameter("prioridade"));
        return processor.addItemToList(listaId, itemText, feito, prioridade);
    }

    private boolean processarAtualizacaoItem(HttpServletRequest request, ListaXmlProcessor processor) {
        int listaId = Integer.parseInt(request.getParameter("listaId"));
        String oldText = request.getParameter("oldItemText");
        String newText = request.getParameter("newItemText");
        boolean feito = "sim".equals(request.getParameter("feito"));
        boolean prioridade = "sim".equals(request.getParameter("prioridade"));
        return processor.updateItem(listaId, oldText, newText, feito, prioridade);
    }

    private boolean processarRemocaoItem(HttpServletRequest request, ListaXmlProcessor processor) {
        int listaId = Integer.parseInt(request.getParameter("listaId"));
        String itemText = request.getParameter("itemText");
        return processor.removeItem(listaId, itemText);
    }


}//class