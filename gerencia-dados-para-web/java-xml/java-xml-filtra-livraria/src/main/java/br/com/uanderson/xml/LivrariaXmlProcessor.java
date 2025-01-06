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

public class LivrariaXmlProcessor {

    // Representa o documento XML carregado na memória.
    private Document xmlDocument;

    // Logger utilizado para registrar mensagens de erro e informações.
    private static final Logger LOGGER = Logger.getLogger(LivrariaXmlProcessor.class.getName());

    /**
     * Construtor que inicializa a classe com o caminho de um arquivo XML.
     *
     * @param xmlFilePath Caminho do arquivo XML a ser processado.
     */
    public LivrariaXmlProcessor(String xmlFilePath) {
        loadXmlDocument(xmlFilePath);
    }

    /**
     * Carrega o arquivo XML a partir do caminho fornecido e armazena o conteúdo como um objeto DOM.
     *
     * @param xmlFilePath Caminho do arquivo XML.
     */
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

    /**
     * Serializa o documento XML carregado em memória para uma String.
     *
     * @return Uma String contendo o XML em formato textual.
     */
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

    public String getAllBooks() {
        NodeList books = xmlDocument.getElementsByTagName("livro");
        return convertXmlToString(books.item(0).getParentNode());
        //pega o primeiro livro/filho [0] e através dele pegamos o pai que a <livraria>
        //passamos para o converterXmlToString que irá mostrar o xml
    }

    public String getBooksByCategory(String category) {
        Element root = xmlDocument.createElement("livraria"); //cria um novo elemento livraria
        NodeList books = xmlDocument.getElementsByTagName("livro"); // pega todos os <livro> do nosso arquivo XML
        boolean found = false;

        for (int i = 0; i < books.getLength(); i++) {
            Element book = (Element) books.item(i); // pega o livro atual do loop e o transforma em um elemento XML
            if (book.getAttribute("categoria").equals(category)) { // verifica se o atributo categoria do livro é igual a categoria passada
                root.appendChild(book.cloneNode(true)); // adiciona o livro ao elemento livraria, o cloneNode(true) clona o livro inteiro
                found = true;
            }
        }
        return found ? convertXmlToString(root) : "Nenhum livro encontrado na categoria: " + category;
        /*
        cloneNode(true):
        true: clona o nó e toda sua subárvore (filhos)
        false: clona apenas o nó e seus atributos
         */
    }

    public String getBooksByLanguage(String language) {
        Element root = xmlDocument.createElement("livraria");
        NodeList titles = xmlDocument.getElementsByTagName("titulo");

        for (int i = 0; i < titles.getLength(); i++) {
            Element title = (Element) titles.item(i);
            if (title.getAttribute("language").equals(language)) {
                root.appendChild(title.getParentNode().cloneNode(true));
            }
        }
        return convertXmlToString(root);
    }

    public String getBooksByCover(String cover) {
        Element root = xmlDocument.createElement("livraria");
        NodeList books = xmlDocument.getElementsByTagName("livro");
        boolean found = false;

        for (int i = 0; i < books.getLength(); i++) {
            Element book = (Element) books.item(i);
            if (book.getAttribute("cover").equals(cover)) {
                root.appendChild(book.cloneNode(true));
                found = true;
            }
        }
        return found ? convertXmlToString(root) : "Nenhum livro com a capa " + cover + " foi encontrado";
    }

    public String getBooksByTitle(String titleText) {
        Element root = xmlDocument.createElement("livraria");
        NodeList titles = xmlDocument.getElementsByTagName("titulo");
        boolean found = false;

        for (int i = 0; i < titles.getLength(); i++) {
            Element title = (Element) titles.item(i);
            if (titleText != null) {
                if (title.getTextContent().toLowerCase().contains(titleText.toLowerCase())) {
                    root.appendChild(title.getParentNode().cloneNode(true));
                    found = true;
                }
            }// check if titleText is null
        }
        return found ? convertXmlToString(root) : "Nenhum livro com o título " + titleText + " foi encontrado";
    }

    public String getBooksByAuthor(String authorName) {
        Element root = xmlDocument.createElement("livraria");
        NodeList authors = xmlDocument.getElementsByTagName("autor");
        boolean found = false;

        for (int i = 0; i < authors.getLength(); i++) {
            Element author = (Element) authors.item(i);
            if (author.getTextContent().equals(authorName)) {
                root.appendChild(author.getParentNode().cloneNode(true));
                found = true;
            }
        }
        return found ? convertXmlToString(root) : "Nenhum livro do autor " + authorName + " foi encontrado";
    }

    public String getBooksWithMultipleAuthors() {
        Element root = xmlDocument.createElement("livraria");
        NodeList books = xmlDocument.getElementsByTagName("livro");

        for (int i = 0; i < books.getLength(); i++) {
            Element book = (Element) books.item(i);
            NodeList authors = book.getElementsByTagName("autor");
            if (authors.getLength() > 1) {
                root.appendChild(book.cloneNode(true));
            }
        }
        return convertXmlToString(root);
    }

    public String getBooksByYear(int year) {
        Element root = xmlDocument.createElement("livraria");
        NodeList years = xmlDocument.getElementsByTagName("ano");

        for (int i = 0; i < years.getLength(); i++) {
            Element yearElement = (Element) years.item(i);
            if (Integer.parseInt(yearElement.getTextContent()) == year) {
                root.appendChild(yearElement.getParentNode().cloneNode(true));
            }
        }
        return convertXmlToString(root);
    }

    public String getBooksByPrice(double price) {
        Element root = xmlDocument.createElement("livraria");
        NodeList prices = xmlDocument.getElementsByTagName("preco");

        for (int i = 0; i < prices.getLength(); i++) {
            Element priceElement = (Element) prices.item(i);
            if (Double.parseDouble(priceElement.getTextContent()) == price) {
                root.appendChild(priceElement.getParentNode().cloneNode(true));
            }
        }
        return convertXmlToString(root);
    }

    public String getBooksByPriceRange(double minPrice, double maxPrice) {
        Element root = xmlDocument.createElement("livraria");
        NodeList prices = xmlDocument.getElementsByTagName("preco");
        boolean found = false;

        for (int i = 0; i < prices.getLength(); i++) {
            Element priceElement = (Element) prices.item(i);
            double price = Double.parseDouble(priceElement.getTextContent());
            if (price >= minPrice && price <= maxPrice) {
                root.appendChild(priceElement.getParentNode().cloneNode(true));
                found = true;
            }
        }

        return found ? convertXmlToString(root) :
                String.format("Nenhum livro encontrado na faixa de preço: R$ %.2f - R$ %.2f", minPrice, maxPrice);

    }

    public static void main(String[] args) {
        LivrariaXmlProcessor processor = new LivrariaXmlProcessor("src/main/webapp/WEB-INF/livraria.xml");

        // Examples
        System.out.println("=====================getBooksByCategory==============================\n");
        System.out.println(processor.getBooksByCategory("TI"));
        System.out.println("=========================getBooksByLanguage==========================\n");
        System.out.println(processor.getBooksByLanguage("en"));
        System.out.println("======================getBooksByPriceRange=============================\n");
        System.out.println(processor.getBooksByPriceRange(40.0, 60.0));
        System.out.println("====================getAllBooks===================================\n");
        System.out.println(processor.getAllBooks());
        System.out.println("====================getBooksByAuthor===============================\n");
        System.out.println(processor.getBooksByAuthor("J.K. Rowling"));
        System.out.println("====================getBooksByTitle===============================\n");
        System.out.println(processor.getBooksByTitle("The Lord of the Rings"));
        System.out.println("====================getBooksByYear===============================\n");
        System.out.println(processor.getBooksByYear(2005));
        System.out.println("====================getBooksByPrice===============================\n");
        System.out.println(processor.getBooksByPrice(50.0));
        System.out.println("=====================getBooksByCover==============================\n");
        System.out.println(processor.getBooksByCover("hardcover"));
        System.out.println("=====================getBooksWithMultipleAuthors==============================\n");
        System.out.println(processor.getBooksWithMultipleAuthors());
        System.out.println("===================================================\n");


    }
}

/*

#### Objetos e funcionamento:
1. `TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();`
   - Cria uma instância da fábrica de transformadores que será usada para criar objetos do tipo `Transformer`.
   - `TransformerFactory` é responsável por configurar a transformação de um documento XML.

2. `Transformer transformer = transformerFactory.newTransformer();`
   - Cria um transformador que será usado para converter o documento DOM (representado por `xmlDocument`) em um formato textual (XML).

3. `DOMSource domSource = new DOMSource(xmlDocument);`
   - Define o objeto `xmlDocument` como a fonte de dados para a transformação.
   - `DOMSource` é a classe que encapsula o documento DOM como uma fonte de entrada.

4. `ByteArrayOutputStream outputStream = new ByteArrayOutputStream();`
   - Cria um fluxo de saída em memória que armazenará os dados do XML transformado.
   - Ele é usado porque queremos o resultado como uma `String`.

5. `StreamResult streamResult = new StreamResult(outputStream);`
   - Define o destino da transformação, neste caso, o `outputStream`.

6. `transformer.transform(domSource, streamResult);`
   - Realiza a transformação do `DOMSource` (o documento DOM) e envia o resultado para o `StreamResult` (o fluxo de saída).

7. `return outputStream.toString();`
   - Converte o conteúdo armazenado no `ByteArrayOutputStream` para uma `String` e a retorna.


 */