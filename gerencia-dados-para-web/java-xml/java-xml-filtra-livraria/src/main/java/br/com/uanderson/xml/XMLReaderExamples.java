package br.com.uanderson.xml;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import java.io.File;

public class XMLReaderExamples {

    /**
     * Implementação 1: Leitura direta dos elementos student
     * Esta implementação é mais específica para a estrutura do XML fornecido
     */
    public static void readXMLMethod1(String filePath) {
        try {
            // Criar File object
            File file = new File(filePath);

            // Criar DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parsear o arquivo XML
            Document doc = dBuilder.parse(file);

            // Normalizar o XML (boa prática)
            doc.getDocumentElement().normalize();

            // Mostrar elemento raiz
            System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName());

            // Obter lista de estudantes
            NodeList studentList = doc.getElementsByTagName("student");

            // Percorrer cada estudante
            for (int i = 0; i < studentList.getLength(); i++) {
                Node studentNode = studentList.item(i);
                System.out.println("\nNome do Nó: " + studentNode.getNodeName());

                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNode;

                    // Extrair informações do estudante
                    System.out.println("ID: " + getElementValue(studentElement, "id"));
                    System.out.println("Nome: " + getElementValue(studentElement, "firstname"));
                    System.out.println("Sobrenome: " + getElementValue(studentElement, "lastname"));
                    System.out.println("Disciplina: " + getElementValue(studentElement, "subject"));
                    System.out.println("Nota: " + getElementValue(studentElement, "marks"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementação 2: Leitura recursiva de todos os nós
     * Esta implementação é mais genérica e pode ser usada com qualquer estrutura XML
     */
    public static void readXMLMethod2(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = docBuilder.parse(file);

            System.out.println("Elemento raiz: " + document.getDocumentElement().getNodeName());

            if (document.hasChildNodes()) {
                printNodes(document.getChildNodes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar para imprimir nós recursivamente
     */
    private static void printNodes(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Imprime informações do nó
                System.out.println("\nNó: " + node.getNodeName() + " [ABERTO]");
                System.out.println("Conteúdo: " + node.getTextContent().trim());

                // Verifica atributos
                if (node.hasAttributes()) {
                    NamedNodeMap attributes = node.getAttributes();
                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attribute = attributes.item(j);
                        System.out.println("Atributo: " + attribute.getNodeName() +
                                " = " + attribute.getNodeValue());
                    }
                }

                // Chamada recursiva para nós filhos
                if (node.hasChildNodes()) {
                    printNodes(node.getChildNodes());
                }

                System.out.println("Nó: " + node.getNodeName() + " [FECHADO]");
            }
        }
    }

    /**
     * Método auxiliar para obter o valor de um elemento
     */
    private static String getElementValue(Element element, String tagName) {
        return element.getElementsByTagName(tagName)
                .item(0)
                .getTextContent();
    }

    public static void main(String[] args) {
        String xmlFilePath = "students.xml";

        System.out.println("=== Método 1: Leitura Direta ===");
        readXMLMethod1(xmlFilePath);

        System.out.println("\n=== Método 2: Leitura Recursiva ===");
        readXMLMethod2(xmlFilePath);
    }
}