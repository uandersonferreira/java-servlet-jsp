package br.com.uanderson;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLDocumentManager {
    private Document document;
    private static final Logger LOGGER = Logger.getLogger(XMLDocumentManager.class.getName());

    public XMLDocumentManager(String xmlFilePath) {
        loadXMLDocument(xmlFilePath);
    }

    private void loadXMLDocument(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFilePath);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOGGER.log(Level.SEVERE, "Error loading XML document", ex);
        }
    }

    // Read all elements from XML
    public String readAllElements() {
        Element rootElement = document.getDocumentElement();
        NodeList elements = rootElement.getChildNodes();
        StringBuilder content = new StringBuilder();

        content.append("XML Content:\n");
        content.append("Root element: ").append(rootElement.getNodeName()).append("\n");

        for (int i = 0; i < elements.getLength(); i++) {
            Node node = elements.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                content.append("Element: ").append(node.getNodeName())
                        .append(" = ").append(node.getTextContent())
                        .append("\n");
            }
        }
        return content.toString();
    }

    // Read specific element by tag name
    public String readElementByName(String tagName) {
        NodeList elements = document.getElementsByTagName(tagName);
        if (elements.getLength() > 0) {
            return elements.item(0).getTextContent();
        }
        return "Element not found";
    }

    // Add new element with current date
    public void addDateElement() {
        Element rootElement = document.getDocumentElement();
        Element dateElement = document.createElement("dataEnvio");
        dateElement.setTextContent(LocalDate.now().toString());
        rootElement.appendChild(dateElement);
    }

    public void addClasseAttribute(String classeValue) {
        Element rootElement = document.getDocumentElement();
        rootElement.setAttribute("classe", classeValue);
    }

    // Get all attributes
    public String getAllAttributes(){
        Element rootElement = document.getDocumentElement();
        NamedNodeMap attributes = rootElement.getAttributes();
        StringBuilder content = new StringBuilder();

        content.append("XML Content Attributes:\n");
        for (int i = 0; i < attributes.getLength(); i++) {
            Node nodeAttribute = attributes.item(i);
            content.append("Attribute: ").append(nodeAttribute.getNodeName());
            content.append(" = ").append(nodeAttribute.getNodeValue());
            content.append("\n");
        }
        return content.toString();
    }

    // get attributes by tag name
    public String getAttributesByName(String attributeName) {
        Element rootElement = document.getDocumentElement();
        NamedNodeMap attributes = rootElement.getAttributes();
        StringBuilder content = new StringBuilder();

        content.append("XML Content Attributes:\n");
        Node nodeAttribute = attributes.getNamedItem(attributeName); // Get the attribute by name
        if (nodeAttribute != null) {
            content.append("Attribute: ").append(nodeAttribute.getNodeName());
            content.append(" = ").append(nodeAttribute.getNodeValue());
            content.append("\n");

            return content.toString();
        }

        return "Not found";

    }


    // Add custom element
    public void addNewElement(String elementName, String elementValue) {
        Element rootElement = document.getDocumentElement();
        Element newElement = document.createElement(elementName);
        newElement.setTextContent(elementValue);
        rootElement.appendChild(newElement);
    }

    // Update element content
    public void updateElement(String elementName, String newValue) {
        NodeList elements = document.getElementsByTagName(elementName);
        if (elements.getLength() > 0) {
            elements.item(0).setTextContent(newValue);
        }
    }

    // Save changes to XML file
    public void saveXMLFile(String outputPath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(outputPath));
            transformer.transform(source, result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving XML file", e);
        }
    }

    public void serializarXML(OutputStream outputStream) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro na serialização do XML", e);
            throw new RuntimeException("Falha na serialização", e);
        }
    }

    public void serializarXML(Writer writer) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro na serialização do XML", e);
            throw new RuntimeException("Falha na serialização", e);
        }
    }



    public static void main(String[] args) {
        String xmlPath = "src/main/webapp/WEB-INF/nota.xml";
        XMLDocumentManager xmlManager = new XMLDocumentManager(xmlPath);

        // Reading examples
        System.out.println("Reading all elements:");
        System.out.println(xmlManager.readAllElements());

        System.out.println("\nReading specific element 'de':");
        System.out.println(xmlManager.readElementByName("de"));

        // Modification examples
        System.out.println("\nAdding new elements:");
        xmlManager.addDateElement();
        xmlManager.addNewElement("assunto", "Importante");

        // Update example
        xmlManager.updateElement("corpo", "Texto atualizado da mensagem");

        // Show modified content
        System.out.println("\nAfter modifications:");
        System.out.println(xmlManager.readAllElements());

        // Save changes
        xmlManager.saveXMLFile(xmlPath + ".modified.xml");

        System.out.println(xmlManager.getAllAttributes());
        System.out.println(xmlManager.getAttributesByName("carta"));

        xmlManager.addClasseAttribute("teste");

        System.out.println(xmlManager.getAllAttributes());

        xmlManager.serializarXML(System.out);
    }
}
