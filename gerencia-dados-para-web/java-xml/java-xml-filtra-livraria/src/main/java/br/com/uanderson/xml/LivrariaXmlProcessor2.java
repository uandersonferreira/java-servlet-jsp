package br.com.uanderson.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LivrariaXmlProcessor2 {
    private Document xmlDocument;
    private static final Logger LOGGER = Logger.getLogger(LivrariaXmlProcessor2.class.getName());

    // Interface para critérios de filtro
    private interface FilterCriteria {
        boolean matches(Element element);
    }

    public LivrariaXmlProcessor2(String xmlFilePath) {
        loadXmlDocument(xmlFilePath);
    }

    private void loadXmlDocument(String xmlFilePath) {
        try {
            // Configura a fábrica para construir documentos DOM.
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newDefaultInstance();

            // Cria um construtor de documentos DOM.
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Faz o parsing do XML e armazena o resultado no atributo xmlDocument.
            xmlDocument = documentBuilder.parse(xmlFilePath);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            // Loga o erro com detalhes no caso de falha ao carregar o XML.
            LOGGER.log(Level.SEVERE, "Falha ao carregar o documento XML.", ex);
        }
    }

    private String convertXmlToString(Node xmlDocumentNode) {
        // Usando try-with-resources para garantir que os recursos sejam fechados automaticamente.
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Cria uma fábrica de transformadores para conversão do DOM para texto.
            TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();

            // Cria o transformador para processar o documento XML.
            Transformer transformer = transformerFactory.newTransformer();

            // Define o documento DOM como a fonte de dados.
            DOMSource domSource = new DOMSource(xmlDocumentNode);

            // Define o destino da transformação como o ByteArrayOutputStream.
            StreamResult streamResult = new StreamResult(outputStream);

            // Realiza a transformação do DOM para XML em formato textual.
            transformer.transform(domSource, streamResult);

            // Retorna o XML em formato String, utilizando a codificação padrão UTF-8.
            return outputStream.toString();

        } catch (TransformerException | IOException ex) {
            // Lança uma RuntimeException com detalhes sobre o erro ocorrido.
            throw new RuntimeException("Erro ao transformar o documento XML em texto.", ex);
        }
    }

    // Método genérico de filtragem
    private String filterBooks(String tagName, FilterCriteria criteria, String notFoundMessage) {
        Element root = xmlDocument.createElement("livraria");
        NodeList elements = xmlDocument.getElementsByTagName(tagName);
        boolean found = false;

        for (int i = 0; i < elements.getLength(); i++) {
            Element element = (Element) elements.item(i);
            if (criteria.matches(element)) {
                Node bookNode = tagName.equals("livro") ? element : element.getParentNode();
                root.appendChild(bookNode.cloneNode(true));
                found = true;
            }
        }

        return found ? convertXmlToString(root) : notFoundMessage;
    }

    // Métodos refatorados usando o filtro genérico
    public String getBooksByCategory(String category) {
        return filterBooks("livro",
                element -> element.getAttribute("categoria").equals(category),
                "Nenhum livro encontrado na categoria: " + category);
    }

    public String getBooksByLanguage(String language) {
        return filterBooks("titulo",
                element -> element.getAttribute("language").equals(language),
                "Nenhum livro encontrado no idioma: " + language);
    }

    public String getBooksByTitle(String titleText) {
        return filterBooks("titulo",
                element -> titleText != null &&
                        element.getTextContent().toLowerCase().contains(titleText.toLowerCase()),
                "Nenhum livro com o título " + titleText + " foi encontrado");
    }

    public String getBooksByPrice(double price) {
        return filterBooks("preco",
                element -> Double.parseDouble(element.getTextContent()) == price,
                "Nenhum livro encontrado com o preço: R$ " + price);
    }

    public String getBooksByPriceRange(double minPrice, double maxPrice) {
        return filterBooks("preco",
                element -> {
                    double price = Double.parseDouble(element.getTextContent());
                    return price >= minPrice && price <= maxPrice;
                },
                String.format("Nenhum livro encontrado na faixa de preço: R$ %.2f - R$ %.2f", minPrice, maxPrice));
    }

    public String getBooksWithMultipleAuthors() {
        return filterBooks("livro",
                element -> element.getElementsByTagName("autor").getLength() > 1,
                "Nenhum livro com múltiplos autores encontrado");
    }

    // Método getAllBooks permanece igual pois tem lógica diferente
    public String getAllBooks() {
        NodeList books = xmlDocument.getElementsByTagName("livro");
        return convertXmlToString(books.item(0).getParentNode());
    }

    public static void main(String[] args) {
        LivrariaXmlProcessor2 processor = new LivrariaXmlProcessor2("src/main/webapp/WEB-INF/livraria.xml");

        // Testes dos métodos refatorados
        System.out.println("=== Livros por Categoria (TI) ===");
        System.out.println(processor.getBooksByCategory("TI"));

        System.out.println("\n=== Livros em Inglês ===");
        System.out.println(processor.getBooksByLanguage("en"));

        System.out.println("\n=== Livros entre R$ 40-60 ===");
        System.out.println(processor.getBooksByPriceRange(40.0, 60.0));

        System.out.println("\n=== Livros com Múltiplos Autores ===");
        System.out.println(processor.getBooksWithMultipleAuthors());

        System.out.println("\n=== Livros por Título (Hobbit) ===");
        System.out.println(processor.getBooksByTitle("Hobbit"));
    }
}