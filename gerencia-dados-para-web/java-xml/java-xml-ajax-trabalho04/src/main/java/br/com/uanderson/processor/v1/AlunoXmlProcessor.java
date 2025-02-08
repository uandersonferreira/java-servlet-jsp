
package br.com.uanderson.processor.v1;

import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class AlunoXmlProcessor {

    // Caminho relativo do arquivo XML
    private static final String XML_RELATIVE_PATH = "/WEB-INF/alunos.xml";

    /**
     * Obtém o objeto Document correspondente ao arquivo XML.
     * Se o arquivo não existir, ele será criado com a estrutura básica.
     */
    public static Document getDocument(ServletContext context) throws Exception {
        String filePath = context.getRealPath(XML_RELATIVE_PATH);
        File xmlFile = new File(filePath);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;

        if (!xmlFile.exists()) {
            // Cria um novo documento com a raiz <alunos>
            doc = dBuilder.newDocument();
            Element root = doc.createElement("alunos");
            doc.appendChild(root);
            // Grava o documento recém-criado
            saveDocument(doc, xmlFile);
        } else {
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
        }
        return doc;
    }

    /**
     * Salva o objeto Document no arquivo XML, utilizando o ServletContext para obter o caminho.
     */
    public static void saveDocument(Document doc, ServletContext context) throws Exception {
        String filePath = context.getRealPath(XML_RELATIVE_PATH);
        File xmlFile = new File(filePath);
        saveDocument(doc, xmlFile);
    }

    // Método privado que efetua a gravação do Document em um arquivo específico.
    private static void saveDocument(Document doc, File xmlFile) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }
}
