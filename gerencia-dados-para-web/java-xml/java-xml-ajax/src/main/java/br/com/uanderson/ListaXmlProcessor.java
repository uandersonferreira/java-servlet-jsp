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
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaXmlProcessor {
    private Document xmlDocument;
    private final String xmlFilePath;
    private static final Logger LOGGER = Logger.getLogger(ListaXmlProcessor.class.getName());

    public ListaXmlProcessor(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        loadXmlDocument();
    }

    private void loadXmlDocument() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            File file = new File(this.xmlFilePath);

            if (file.exists()) {
                xmlDocument = documentBuilder.parse(file);
            } else {
                xmlDocument = documentBuilder.newDocument();
                Element root = xmlDocument.createElement("listas");
                xmlDocument.appendChild(root);
                saveXmlDocument(); // Cria o arquivo se não existir
            }

            xmlDocument.getDocumentElement().normalize();

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

    /**
     * Adiciona um novo item a uma lista específica
     * @param listId ID da lista
     * @param itemText Texto do item
     * @param feito Status de conclusão (sim/não)
     * @param prioridade Prioridade do item (sim/não)
     * @return true se o item foi adicionado com sucesso
     */
    public boolean addItemToList(int listId, String itemText, boolean feito, boolean prioridade) {
        NodeList lists = xmlDocument.getElementsByTagName("lista");

        for (int i = 0; i < lists.getLength(); i++) {
            Element lista = (Element) lists.item(i);
            String currentListId = lista.getAttribute("id");

            if (currentListId != null && currentListId.equals(String.valueOf(listId))) {
                Element itensElement = (Element) lista.getElementsByTagName("itens").item(0);

                Element newItem = xmlDocument.createElement("item");
                newItem.setTextContent(itemText);

                if (feito) {
                    newItem.setAttribute("feito", "sim");
                }

                if (prioridade) {
                    newItem.setAttribute("prioridade", "sim");
                }

                itensElement.appendChild(newItem);
                saveXmlDocument();
                return true;
            }
        }

        return false;
    }

    /**
     * Atualiza um item existente
     * @param listId ID da lista
     * @param oldItemText Texto do item original
     * @param newItemText Novo texto do item
     * @param feito Novo status de conclusão
     * @param prioridade Nova prioridade
     * @return true se o item foi atualizado com sucesso
     */
    public boolean updateItem(int listId, String oldItemText, String newItemText, boolean feito, boolean prioridade) {
        NodeList lists = xmlDocument.getElementsByTagName("lista");

        for (int i = 0; i < lists.getLength(); i++) {
            Element lista = (Element) lists.item(i);
            String currentListId = lista.getAttribute("id");

            if (currentListId != null && currentListId.equals(String.valueOf(listId))) {
                NodeList items = lista.getElementsByTagName("item");

                for (int j = 0; j < items.getLength(); j++) {
                    Element item = (Element) items.item(j);

                    if (item.getTextContent().trim().equals(oldItemText.trim())) {
                        // Atualizar texto
                        item.setTextContent(newItemText);

                        // Atualizar status
                        if (feito) {
                            item.setAttribute("feito", "sim");
                        } else {
                            item.removeAttribute("feito");
                        }

                        // Atualizar prioridade
                        if (prioridade) {
                            item.setAttribute("prioridade", "sim");
                        } else {
                            item.removeAttribute("prioridade");
                        }

                        saveXmlDocument();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Remove um item de uma lista específica
     * @param listId ID da lista
     * @param itemText Texto do item a ser removido
     * @return true se o item foi removido com sucesso
     */
    public boolean removeItem(int listId, String itemText) {
        NodeList lists = xmlDocument.getElementsByTagName("lista");

        for (int i = 0; i < lists.getLength(); i++) {
            Element lista = (Element) lists.item(i);
            String currentListId = lista.getAttribute("id");

            if (currentListId != null && currentListId.equals(String.valueOf(listId))) {
                NodeList items = lista.getElementsByTagName("item");

                for (int j = 0; j < items.getLength(); j++) {
                    Element item = (Element) items.item(j);

                    if (item.getTextContent().trim().equals(itemText.trim())) {
                        item.getParentNode().removeChild(item);
                        saveXmlDocument();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Adiciona uma nova lista ao documento XML
     * @param listId ID da nova lista
     * @param listTitle Título da nova lista
     * @return true se a lista foi adicionada com sucesso
     */
    public boolean addNewList(int listId, String listTitle) {
        Element root = xmlDocument.getDocumentElement();

        // Verificar se já existe uma lista com o mesmo ID
        NodeList existingLists = xmlDocument.getElementsByTagName("lista");
        for (int i = 0; i < existingLists.getLength(); i++) {
            Element existingList = (Element) existingLists.item(i);
            if (existingList.getAttribute("id").equals(String.valueOf(listId))) {
                return false;
            }
        }

        // Criar nova lista
        Element newList = xmlDocument.createElement("lista");
        newList.setAttribute("id", String.valueOf(listId));

        Element titleElement = xmlDocument.createElement("titulo");
        titleElement.setTextContent(listTitle);
        newList.appendChild(titleElement);

        Element itensElement = xmlDocument.createElement("itens");
        newList.appendChild(itensElement);

        root.appendChild(newList);
        saveXmlDocument();
        return true;
    }

    /**
     * Salva as modificações no arquivo XML
     */
    private void saveXmlDocument() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new File(xmlFilePath));

            transformer.transform(source, result);
            LOGGER.info("XML document updated successfully.");
        } catch (TransformerException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o documento XML.", ex);
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
