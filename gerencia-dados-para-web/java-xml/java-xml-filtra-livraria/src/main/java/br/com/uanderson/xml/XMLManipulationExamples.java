package br.com.uanderson.xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLManipulationExamples {

    /**
     * Lê um arquivo XML e exibe seu conteúdo
     * @param filePath caminho do arquivo XML
     */
    public static void lerXML(String filePath) {
        try {
            // 1. Criar DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Obter Document do arquivo
            Document document = builder.parse(new File(filePath));

            // 3. Normalizar estrutura
            document.getDocumentElement().normalize();

            // 4. Buscar elementos pela tag (exemplo com "pessoa")
            NodeList pessoaList = document.getElementsByTagName("pessoa");

            // Iterar sobre os elementos
            for (int i = 0; i < pessoaList.getLength(); i++) {
                Node pessoaNode = pessoaList.item(i);
                if (pessoaNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pessoaElement = (Element) pessoaNode;
                    String nome = pessoaElement.getElementsByTagName("nome").item(0).getTextContent();
                    String idade = pessoaElement.getElementsByTagName("idade").item(0).getTextContent();
                    System.out.println("Pessoa: " + nome + ", Idade: " + idade);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cria um novo arquivo XML com dados de exemplo
     * @param filePath caminho onde o arquivo será salvo
     */
    public static void criarXML(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Criar elemento raiz
            Element raiz = document.createElement("pessoas");
            document.appendChild(raiz);

            // Adicionar uma pessoa
            Element pessoa = document.createElement("pessoa");
            raiz.appendChild(pessoa);

            // Adicionar dados da pessoa
            Element nome = document.createElement("nome");
            nome.appendChild(document.createTextNode("João Silva"));
            pessoa.appendChild(nome);

            Element idade = document.createElement("idade");
            idade.appendChild(document.createTextNode("30"));
            pessoa.appendChild(idade);

            // Salvar o arquivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Arquivo XML criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converte um arquivo XML para String - Método 1
     * @param filePath caminho do arquivo XML
     * @return String contendo o XML
     */
    public static String xmlParaString1(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converte um arquivo XML para String - Método 2 (usando BufferedReader)
     * @param filePath caminho do arquivo XML
     * @return String contendo o XML
     */
    public static String xmlParaString2(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso
        String filePath = "pessoas.xml";

        // Criar XML
        criarXML(filePath);

        // Ler XML
        System.out.println("\nLendo o XML criado:");
        lerXML(filePath);

        // Converter para String usando dois métodos diferentes
        System.out.println("\nXML como String (Método 1):");
        System.out.println(xmlParaString1(filePath));

        System.out.println("\nXML como String (Método 2):");
        System.out.println(xmlParaString2(filePath));
    }
}