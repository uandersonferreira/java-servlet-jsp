package br.com.uanderson;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaXmlProcessor {
    private Document xmlDocument;
    private static final Logger LOGGER = Logger.getLogger(ListaXmlProcessor.class.getName());

    public ListaXmlProcessor(String xmlFilePath) {
        loadXmlDocument(xmlFilePath);
    }

    private void loadXmlDocument(String xmlFilePath) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newDefaultInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            xmlDocument = documentBuilder.parse(xmlFilePath);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOGGER.log(Level.SEVERE, "Falha ao carregar o documento XML.", ex);
        }
    }

    private String convertXmlToString(Node xmlDocumentNode) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Define indentação

            DOMSource domSource = new DOMSource(xmlDocumentNode);

            StreamResult streamResult = new StreamResult(outputStream);
            transformer.transform(domSource, streamResult);

            return outputStream.toString();

        } catch (TransformerException | IOException ex) {
            throw new RuntimeException("Erro ao transformar o documento XML em texto.", ex);
        }
    }

    public String getAllListas() {
        NodeList lista = xmlDocument.getElementsByTagName("lista");
        return convertXmlToString(lista.item(0).getParentNode());
        //pega a primeira lista/filho [0] e através dela pegamos o pai que a <listas>
        //passamos para o converterXmlToString que irá mostrar o xml
    }

    public String getListById(int id) {
        Element root = xmlDocument.createElement("listas");
        NodeList lists = xmlDocument.getElementsByTagName("lista");
        boolean found = false;

        for (int i = 0; i < lists.getLength(); i++) {
            Element lista = (Element) lists.item(i);
            String listId = lista.getAttribute("id");

            if (listId != null && listId.equals(String.valueOf(id))) {
                root.appendChild(lista.cloneNode(true));
                LOGGER.info("Found list with id: " + id);
                found = true;
                break;
            }
        }

        return found ? convertXmlToString(root) : "Nenhuma lista com o id " + id + " foi encontrada";
    }

    public String getListTitles() {
        Element root = xmlDocument.createElement("listas");
        NodeList lists = xmlDocument.getElementsByTagName("lista");

        if (lists.getLength() == 0) {
            return "Nenhuma lista encontrada.";
        }

        for (int i = 0; i < lists.getLength(); i++) {
            Element lista = (Element) lists.item(i);
            String listId = lista.getAttribute("id");
            String listTitle = lista.getElementsByTagName("titulo").item(0).getTextContent();

            Element titleElement = xmlDocument.createElement("titulo");
            titleElement.setTextContent(listTitle);
            titleElement.setAttribute("id", listId);
            root.appendChild(titleElement);
        }
        return convertXmlToString(root);
    }


}//class
