package br.com.uanderson.servlets.v1;

import br.com.uanderson.processor.v1.AlunoXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;

@WebServlet("/deletarAluno")
public class DeletarAlunoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nomeBusca = request.getParameter("nome");

        if (nomeBusca == null || nomeBusca.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Nome é obrigatório para deleção!");
            return;
        }

        try {
            Document doc = AlunoXmlProcessor.getDocument(getServletContext());

            NodeList alunos = doc.getElementsByTagName("aluno");
            boolean encontrado = false;
            for (int i = 0; i < alunos.getLength(); i++) {
                Element aluno = (Element) alunos.item(i);
                String nome = aluno.getElementsByTagName("nome").item(0).getTextContent();
                if (nome.equalsIgnoreCase(nomeBusca)) {
                    // Remove o elemento <aluno> da raiz
                    aluno.getParentNode().removeChild(aluno);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                response.getWriter().write("Aluno não encontrado!");
                return;
            }

            // Salva as alterações no arquivo XML
            AlunoXmlProcessor.saveDocument(doc, getServletContext());

            response.getWriter().write("Aluno deletado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao deletar o aluno!");
        }
    }
}
