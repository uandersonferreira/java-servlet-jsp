
LER DADOS DE UM XML E MOSTRAR, PASSOS NECESSÁRIOS:

1. **Pegar um Document Builder**
2. **Pegar o Document**
3. **Normalizar a strutura XML**
4. **Pegar todos os elementos pela tag name**
---

# Documentação do LivrariaXmlProcessor

## Índice
1. [Estrutura Básica](#estrutura-básica)
2. [Inicialização](#inicialização)
3. [Conversão XML para String](#conversão-xml-para-string)
4. [Métodos de Busca](#métodos-de-busca)

## Estrutura Básica

### Atributos da Classe
```java
private Document xmlDocument;
private static final Logger LOGGER;
```
- `xmlDocument`: Armazena o documento XML carregado em memória
- `LOGGER`: Utilizado para logging de erros e informações

## Inicialização

### Construtor e Carregamento do XML
```java
public LivrariaXmlProcessor(String xmlFilePath) {
    loadXmlDocument(xmlFilePath);
}
```
1. Recebe o caminho do arquivo XML
2. Chama o método `loadXmlDocument` para carregar o arquivo

### Método loadXmlDocument
```java
private void loadXmlDocument(String xmlFilePath) {
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newDefaultInstance();
    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    xmlDocument = documentBuilder.parse(xmlFilePath);
}
```
1. Cria uma fábrica de DocumentBuilder
2. Cria um DocumentBuilder
3. Faz o parsing do arquivo XML
4. Armazena o resultado no `xmlDocument`

## Conversão XML para String

### Método convertXmlToString
```java
private String convertXmlToString(Node xmlDocumentNode) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource domSource = new DOMSource(xmlDocumentNode);
    StreamResult streamResult = new StreamResult(outputStream);
    transformer.transform(domSource, streamResult);
    return outputStream.toString();
}
```
1. Cria um stream de bytes para armazenar o resultado
2. Configura o transformador para converter o XML
3. Define a fonte (DOM) e o destino (Stream)
4. Realiza a transformação
5. Retorna o resultado como String

## Métodos de Busca

### getAllBooks
```java
public String getAllBooks() {
    NodeList books = xmlDocument.getElementsByTagName("livro");
    return convertXmlToString(books.item(0).getParentNode());
}
```
1. Obtém todos os elementos com tag "livro"
2. Pega o nó pai do primeiro livro (que é a livraria)
3. Converte para String e retorna

### getBooksByCategory
```java
public String getBooksByCategory(String category) {
    Element root = xmlDocument.createElement("livraria");
    NodeList books = xmlDocument.getElementsByTagName("livro");
    
    for (int i = 0; i < books.getLength(); i++) {
        Element book = (Element) books.item(i);
        if (book.getAttribute("categoria").equals(category)) {
            root.appendChild(book.cloneNode(true));
        }
    }
}
```
1. Cria novo elemento raiz "livraria"
2. Obtém lista de todos os livros
3. Para cada livro:
    - Verifica se a categoria corresponde
    - Se sim, clona o livro e adiciona ao novo elemento raiz
4. Retorna o resultado convertido para String

### getBooksByLanguage
```java
public String getBooksByLanguage(String language) {
    Element root = xmlDocument.createElement("livraria");
    NodeList titles = xmlDocument.getElementsByTagName("titulo");
    
    for (int i = 0; i < titles.getLength(); i++) {
        Element title = (Element) titles.item(i);
        if (title.getAttribute("language").equals(language)) {
            root.appendChild(title.getParentNode().cloneNode(true));
        }
    }
}
```
1. Cria novo elemento raiz
2. Obtém todos os elementos "titulo"
3. Para cada título:
    - Verifica o atributo "language"
    - Se corresponde, adiciona o livro inteiro (nó pai do título)

### getBooksByCover
```java
public String getBooksByCover(String cover) {
    Element root = xmlDocument.createElement("livraria");
    NodeList books = xmlDocument.getElementsByTagName("livro");
    
    for (int i = 0; i < books.getLength(); i++) {
        Element book = (Element) books.item(i);
        if (book.getAttribute("cover").equals(cover)) {
            root.appendChild(book.cloneNode(true));
        }
    }
}
```
1. Cria novo elemento raiz
2. Obtém todos os livros
3. Para cada livro:
    - Verifica o atributo "cover"
    - Se corresponde, clona e adiciona ao resultado

### getBooksByTitle
```java
public String getBooksByTitle(String titleText) {
    Element root = xmlDocument.createElement("livraria");
    NodeList titles = xmlDocument.getElementsByTagName("titulo");
    
    for (int i = 0; i < titles.getLength(); i++) {
        Element title = (Element) titles.item(i);
        if (titleText != null && title.getTextContent().toLowerCase().contains(titleText.toLowerCase())) {
            root.appendChild(title.getParentNode().cloneNode(true));
        }
    }
}
```
1. Cria novo elemento raiz
2. Obtém todos os títulos
3. Para cada título:
    - Verifica se o texto contém a busca (case insensitive)
    - Se contém, adiciona o livro inteiro

### getBooksByAuthor
```java
public String getBooksByAuthor(String authorName) {
    Element root = xmlDocument.createElement("livraria");
    NodeList authors = xmlDocument.getElementsByTagName("autor");
    
    for (int i = 0; i < authors.getLength(); i++) {
        Element author = (Element) authors.item(i);
        if (author.getTextContent().equals(authorName)) {
            root.appendChild(author.getParentNode().cloneNode(true));
        }
    }
}
```
1. Cria novo elemento raiz
2. Obtém todos os autores
3. Para cada autor:
    - Verifica se o nome corresponde
    - Se sim, adiciona o livro inteiro

### getBooksWithMultipleAuthors
```java
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
}
```
1. Cria novo elemento raiz
2. Obtém todos os livros
3. Para cada livro:
    - Conta o número de autores
    - Se tem mais de um, adiciona ao resultado

### getBooksByPrice e getBooksByPriceRange
```java
public String getBooksByPrice(double price) {
    Element root = xmlDocument.createElement("livraria");
    NodeList prices = xmlDocument.getElementsByTagName("preco");
    
    for (int i = 0; i < prices.getLength(); i++) {
        Element priceElement = (Element) prices.item(i);
        if (Double.parseDouble(priceElement.getTextContent()) == price) {
            root.appendChild(priceElement.getParentNode().cloneNode(true));
        }
    }
}
```
1. Cria novo elemento raiz
2. Obtém todos os preços
3. Para cada preço:
    - Converte para double e compara
    - Se corresponde, adiciona o livro inteiro

Observações Importantes:
- Todos os métodos seguem um padrão similar:
    1. Criar elemento raiz novo
    2. Buscar elementos relevantes
    3. Filtrar baseado em critérios
    4. Clonar e adicionar ao resultado
    5. Converter para String
- O uso de `cloneNode(true)` é importante para não modificar o documento original
- A estrutura permite fácil extensão para novos critérios de busca


---
A estratégia geral para construir filtros XML em Java segue este padrão:

1. **Estrutura Base**
```java
Element root = xmlDocument.createElement("livraria");  // Novo documento para resultado
NodeList elementos = xmlDocument.getElementsByTagName("elemento_alvo");  // Busca elementos
// Itera, filtra e adiciona ao root
return convertXmlToString(root);  // Converte resultado para string
```

2. **Padrões de Filtro**:

- **Filtro por Atributo** (categoria, idioma, cover):
```java
Element elemento = (Element) elementos.item(i);
if (elemento.getAttribute("atributo").equals(valorBuscado)) {
    root.appendChild(elemento.cloneNode(true));
}
```

- **Filtro por Conteúdo** (título, autor):
```java
if (elemento.getTextContent().contains(valorBuscado)) {
    root.appendChild(elemento.getParentNode().cloneNode(true));
}
```

- **Filtro por Valor Numérico** (preço, ano):
```java
double valor = Double.parseDouble(elemento.getTextContent());
if (valor >= minimo && valor <= maximo) {
    root.appendChild(elemento.getParentNode().cloneNode(true));
}
```

3. **Pontos Importantes**:
- Use `getParentNode()` quando precisar pegar o livro inteiro
- Use `cloneNode(true)` para copiar elementos com subárvore
- Converta strings para números quando necessário
- Crie um novo elemento root para armazenar resultados

Exemplo prático de como pensar ao criar um novo filtro:
1. Identifique o elemento alvo (livro, título, autor, etc.)
2. Determine se precisa filtrar por atributo ou conteúdo
3. Escolha o padrão de filtro apropriado
4. Adapte a lógica de comparação conforme necessário