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
import java.text.DecimalFormat;

@WebServlet("/buscarAluno")
public class BuscarAlunoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nomeBusca = request.getParameter("nome");

        if (nomeBusca == null || nomeBusca.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Nome é obrigatório para a busca!");
            return;
        }

        try {
            Document doc = AlunoXmlProcessor.getDocument(getServletContext());

            NodeList alunos = doc.getElementsByTagName("aluno");
            boolean encontrado = false;
            String respMsg = "";
            for (int i = 0; i < alunos.getLength(); i++) {
                Element aluno = (Element) alunos.item(i);
                String nome = aluno.getElementsByTagName("nome").item(0).getTextContent();
                if (nome.equalsIgnoreCase(nomeBusca)) {
                    encontrado = true;
                    String idade = aluno.getElementsByTagName("idade").item(0).getTextContent();

                    // Calcula a média das notas
                    Element notasElement = (Element) aluno.getElementsByTagName("notas").item(0);
                    NodeList notaList = notasElement.getElementsByTagName("nota");
                    double soma = 0;
                    int qtd = notaList.getLength();
                    for (int j = 0; j < qtd; j++) {
                        soma += Double.parseDouble(notaList.item(j).getTextContent());
                    }
                    double media = qtd > 0 ? soma / qtd : 0;
                    DecimalFormat df = new DecimalFormat("#.##");

                    respMsg = "Nome: " + nome + "\nIdade: " + idade + "\nMédia: " + df.format(media);
                    break;
                }
            }

            if (!encontrado) {
                response.getWriter().write("Aluno não encontrado!");
            } else {
                response.getWriter().write(respMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao buscar o aluno!");
        }
    }
}
