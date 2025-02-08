
package br.com.uanderson.processor.v2;

import br.com.uanderson.model.Aluno;
import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlunoXmlProcessor {
    private static final String XML_RELATIVE_PATH = "/WEB-INF/alunos.xml";
    private ServletContext context;

    public AlunoXmlProcessor(ServletContext context) {
        this.context = context;
    }

    private void criarArquivoSeNaoExiste() {
        String filePath = this.context.getRealPath(XML_RELATIVE_PATH);
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Garante que o diretório WEB-INF exista
                file.createNewFile();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element root = doc.createElement("alunos");
                doc.appendChild(root);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Aluno> lerAlunos() {
        criarArquivoSeNaoExiste();
        List<Aluno> alunos = new ArrayList<>();
        try {
            String filePath = this.context.getRealPath(XML_RELATIVE_PATH);
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList alunoNodes = doc.getElementsByTagName("aluno");

            for (int i = 0; i < alunoNodes.getLength(); i++) {
                Element alunoElement = (Element) alunoNodes.item(i);
                String nome = alunoElement.getElementsByTagName("nome").item(0).getTextContent();
                int idade = Integer.parseInt(alunoElement.getElementsByTagName("idade").item(0).getTextContent());

                Aluno aluno = new Aluno(nome, idade);
                NodeList notaNodes = alunoElement.getElementsByTagName("nota");
                for (int j = 0; j < notaNodes.getLength(); j++) {
                    aluno.adicionarNota(Double.parseDouble(notaNodes.item(j).getTextContent()));
                }
                alunos.add(aluno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public void salvarAlunos(List<Aluno> alunos) {
        criarArquivoSeNaoExiste();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("alunos");
            doc.appendChild(root);

            for (Aluno aluno : alunos) {
                Element alunoElement = doc.createElement("aluno");

                Element nome = doc.createElement("nome");
                nome.setTextContent(aluno.getNome());
                alunoElement.appendChild(nome);

                Element idade = doc.createElement("idade");
                idade.setTextContent(String.valueOf(aluno.getIdade()));
                alunoElement.appendChild(idade);

                Element notasElement = doc.createElement("notas");
                for (Double nota : aluno.getNotas()) {
                    Element notaElement = doc.createElement("nota");
                    notaElement.setTextContent(String.valueOf(nota));
                    notasElement.appendChild(notaElement);
                }
                alunoElement.appendChild(notasElement);

                root.appendChild(alunoElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            String filePath = this.context.getRealPath(XML_RELATIVE_PATH);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarAluno(Aluno novoAluno) {
        List<Aluno> alunos = lerAlunos();
        alunos.add(novoAluno);
        salvarAlunos(alunos);
    }

    public Aluno buscarAlunoPorNome(String nome) {
        for (Aluno aluno : lerAlunos()) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                return aluno;
            }
        }
        return null;
    }

    public boolean deletarAluno(String nome) {
        List<Aluno> alunos = lerAlunos();
        boolean removido = alunos.removeIf(aluno -> aluno.getNome().equalsIgnoreCase(nome));
        if (removido) {
            salvarAlunos(alunos);
        }
        return removido;
    }

    public boolean adicionarNota(String nome, double nota) {
        List<Aluno> alunos = lerAlunos();
        for (Aluno aluno : alunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                aluno.adicionarNota(nota);
                salvarAlunos(alunos);
                return true;
            }
        }
        return false;
    }
}

