package br.com.uanderson.servlets;

import br.com.uanderson.xml.LivrariaXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@WebServlet(name = "LivrariaXmlServlet", value = "/livraria-xml")
public class LivrariaXmlServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LivrariaXmlServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String titulo = request.getParameter("titulo");

        try (PrintWriter out = response.getWriter()) {
            // Validate input parameter
            if (titulo == null || titulo.trim().isEmpty()) {
                sendError(out, "Título não pode ser vazio", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String xmlPathFile = getServletContext().getRealPath("/WEB-INF/livraria.xml");

            // Validate XML file existence
            if (!Files.exists(Paths.get(xmlPathFile))) {
                String errorMsg = "Arquivo XML não encontrado: " + xmlPathFile;
                logger.info(errorMsg);
                sendError(out, errorMsg, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            try {
                LivrariaXmlProcessor processor = new LivrariaXmlProcessor(xmlPathFile);
                String result = processor.getBooksByTitle(titulo);

                if (result == null || result.trim().isEmpty()) {
                    sendError(out, "Nenhum livro encontrado com o título: " + titulo, HttpServletResponse.SC_NOT_FOUND);
                } else {
                    out.println(result);
                }

            } catch (Exception ex) {
                logger.info("Erro ao processar a requisição: " + ex);
                sendError(out, "Erro interno ao processar a requisição: " + ex.getMessage(),
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void sendError(PrintWriter out, String message, int statusCode) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<erro>");
        out.println("    <codigo>" + statusCode + "</codigo>");
        out.println("    <mensagem>" + message + "</mensagem>");
        out.println("</erro>");
    }
}
