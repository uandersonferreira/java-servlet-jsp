package br.com.uanderson.servlets.v1;

import br.com.uanderson.processor.v1.AlunoXmlProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;

@WebServlet("/cadastrarAluno")
public class CadastrarAlunoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String idadeStr = request.getParameter("idade");

        if (nome == null || nome.trim().isEmpty() || idadeStr == null || idadeStr.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Nome e idade são obrigatórios!");
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Idade inválida!");
            return;
        }

        try {
            // Obtém o documento XML (criando o arquivo se necessário)
            Document doc = AlunoXmlProcessor.getDocument(getServletContext());

            // Cria o novo elemento <aluno>
            Element novoAluno = doc.createElement("aluno");

            Element nomeElement = doc.createElement("nome");
            nomeElement.setTextContent(nome);
            novoAluno.appendChild(nomeElement);

            Element idadeElement = doc.createElement("idade");
            idadeElement.setTextContent(String.valueOf(idade));
            novoAluno.appendChild(idadeElement);

            // Cria o container de notas (vazio)
            Element notasElement = doc.createElement("notas");
            novoAluno.appendChild(notasElement);

            // Adiciona o novo aluno à raiz <alunos>
            doc.getDocumentElement().appendChild(novoAluno);

            // Salva as alterações no arquivo XML
            AlunoXmlProcessor.saveDocument(doc, getServletContext());

            response.getWriter().write("Aluno cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao cadastrar o aluno!");
        }
    }
}

