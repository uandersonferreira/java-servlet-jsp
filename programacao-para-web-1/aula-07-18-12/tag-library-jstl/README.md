# O que é JSTL?

JSTL (JSP Standard Tag Library) é uma biblioteca de tags JSP que encapsula funcionalidades comuns em desenvolvimento web Java. O nome pode ser quebrado assim:
- JSP = JavaServer Pages (tecnologia para criar páginas web dinâmicas)
- Standard = Padrão (porque é a biblioteca oficial)
- Tag = Marcação (similar ao HTML)
- Library = Biblioteca (conjunto de funcionalidades)


1. **Core**: Funcionalidades gerais como controle de fluxo e manipulação de variáveis.
2. **Internacionalização e Formatação**: Para lidar com idiomas e formatos (número, data).
3. **SQL**: Acesso a bancos de dados relacionais.
4. **XML**: Processamento de documentos XML.

### Dependências no Maven
Antes de usar JSTL com Glassfish, você precisa adicionar as dependências no arquivo `pom.xml`:

```xml
<dependency>
    <groupId>jakarta.platform</groupId>
    <artifactId>jakarta.jakartaee-web-api</artifactId>
    <version>9.1.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    <version>3.0.1</version>
</dependency>
```

Com Tomcat 11
```xml
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    <version>3.0.1</version>
</dependency>
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>jakarta.servlet.jsp</groupId>
    <artifactId>jakarta.servlet.jsp-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>

```

# Bibliotecas JSTL

1. **Core (c)**
   Esta é a biblioteca mais usada. Vamos ver exemplos práticos:

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Exemplo de if -->
<c:if test="${idade >= 18}">
    <p>Você é maior de idade</p>
</c:if>

<!-- Exemplo de forEach -->
<c:forEach var="item" items="${listaCompras}">
    <p>Item: ${item.nome} - Preço: ${item.preco}</p>
</c:forEach>

<!-- Exemplo de choose/when/otherwise (similar ao switch/case) -->
<c:choose>
    <c:when test="${nota >= 7}">
        <p>Aprovado</p>
    </c:when>
    <c:when test="${nota >= 5}">
        <p>Recuperação</p>
    </c:when>
    <c:otherwise>
        <p>Reprovado</p>
    </c:otherwise>
</c:choose>
```

2. **Formatting (fmt)**
   Para internacionalização e formatação de números/datas:

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Formatação de data -->
<fmt:formatDate value="${data}" pattern="dd/MM/yyyy"/>

<!-- Formatação de número -->
<fmt:formatNumber value="${preco}" type="currency"/>

<!-- Mensagem internacionalizada -->
<fmt:setLocale value="pt_BR"/>
<fmt:setBundle basename="mensagens"/>
<fmt:message key="bemvindo"/>
```

3. **SQL (sql)**
   Para operações com banco de dados:

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!-- Configurar conexão -->
<sql:setDataSource var="db" driver="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost/banco"
    user="usuario" password="senha"/>

<!-- Consulta -->
<sql:query dataSource="${db}" var="resultado">
    SELECT * FROM produtos;
</sql:query>

<!-- Exibir resultados -->
<c:forEach var="linha" items="${resultado.rows}">
    <p>${linha.nome} - ${linha.preco}</p>
</c:forEach>
```

4. **XML (x)**
   Para processamento de documentos XML:

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<!-- Processar XML -->
<x:parse xml="${documentoXML}" var="doc"/>

<!-- Iterar sobre elementos -->
<x:forEach select="$doc/livros/livro" var="item">
    <p><x:out select="$item/titulo"/></p>
</x:forEach>
```

# Elementos dos comandos JSTL

Vamos detalhar a estrutura de um comando JSTL:

```jsp
<c:if test="${condição}">
```

Onde:
- `c`: é o prefixo definido no taglib
- `if`: é o nome da tag
- `test`: é o atributo que recebe a condição
- `${}`: é a sintaxe para Expression Language (EL), que acessa objetos Java

# Integração com Java

A JSTL trabalha diretamente com objetos Java através do Expression Language. Por exemplo:

```java
// No Servlet
List<Produto> produtos = produtoDAO.listarTodos();
request.setAttribute("produtos", produtos);
```

```jsp
<!-- Na página JSP -->
<c:forEach var="produto" items="${produtos}">
    <p>${produto.nome}</p>
</c:forEach>
```

Esta integração permite que você trabalhe com objetos Java sem escrever código Java diretamente na página JSP, tornando o código mais limpo e maintainable.

Os principais benefícios de usar JSTL são:
1. Código mais limpo e legível
2. Separação clara entre lógica (Java) e apresentação (JSP)
3. Reutilização de código através das tags
4. Redução de erros de desenvolvimento
5. Manutenção mais fácil

# Documentação Completa JSTL (JSP Standard Tag Library)

## 1. Core Tags (prefix="c")
URI: `http://java.sun.com/jsp/jstl/core`

### 1.1 Controle de Fluxo

#### c:if
```jsp
<c:if test="${condição}">
    <!-- conteúdo -->
</c:if>
```
- **Uso**: Executa o conteúdo se a condição for verdadeira
- **Atributos**:
    - test: expressão booleana
    - var: nome da variável para armazenar o resultado
    - scope: escopo da variável (page, request, session, application)

#### c:choose, c:when, c:otherwise
```jsp
<c:choose>
    <c:when test="${condição1}">
        <!-- conteúdo 1 -->
    </c:when>
    <c:when test="${condição2}">
        <!-- conteúdo 2 -->
    </c:when>
    <c:otherwise>
        <!-- conteúdo padrão -->
    </c:otherwise>
</c:choose>
```
- **Uso**: Similar ao switch-case em Java
- **Observações**:
    - Pode ter múltiplos `<c:when>`
    - `<c:otherwise>` é opcional

### 1.2 Iteração

#### c:forEach
```jsp
<!-- Iteração sobre coleção -->
<c:forEach var="item" items="${collection}" begin="0" end="10" step="2" varStatus="status">
    ${status.count}: ${item}
</c:forEach>

<!-- Iteração numérica -->
<c:forEach var="i" begin="1" end="5">
    Número: ${i}
</c:forEach>
```
- **Atributos**:
    - var: variável de iteração
    - items: coleção a ser iterada
    - begin: índice inicial
    - end: índice final
    - step: incremento
    - varStatus: objeto com informações da iteração (index, count, first, last)

#### c:forTokens
```jsp
<c:forTokens items="maçã,banana,laranja" delims="," var="fruta">
    <p>${fruta}</p>
</c:forTokens>
```
- **Uso**: Divide uma string em tokens e itera sobre eles
- **Atributos**: Similar ao forEach, mais o delims (delimitadores)

### 1.3 Manipulação de Variáveis

#### c:set
```jsp
<!-- Definir variável -->
<c:set var="nome" value="João" scope="session"/>

<!-- Definir propriedade de objeto -->
<c:set target="${user}" property="name" value="João"/>
```
- **Atributos**:
    - var: nome da variável
    - value: valor
    - scope: escopo da variável
    - target: objeto alvo
    - property: propriedade do objeto

#### c:remove
```jsp
<c:remove var="nome" scope="session"/>
```
- **Uso**: Remove uma variável do escopo especificado

### 1.4 URL e Redirecionamento

#### c:url
```jsp
<c:url value="/pagina.jsp" var="minhaUrl">
    <c:param name="id" value="123"/>
    <c:param name="action" value="view"/>
</c:url>
<a href="${minhaUrl}">Link</a>
```
- **Uso**: Cria URLs com codificação automática
- **Atributos**:
    - value: URL base
    - var: variável para armazenar a URL
    - context: contexto da aplicação

O uso da tag `<c:url>` com o atributo **session** é extremamente útil em cenários onde o identificador de sessão do usuário não pode ser passado através de cookies, mas sim anexado diretamente à URL. Essa situação ocorre, por exemplo, quando o usuário desativa cookies no navegador.

---

### **Quando é usado e por que é útil?**
- **Contexto de Sessão Sem Cookies**: Se os cookies estão desabilitados no navegador, o servidor não pode associar a sessão do usuário automaticamente através de cookies. Nesse caso, o identificador da sessão (**Session ID**) precisa ser anexado manualmente às URLs para que o servidor reconheça o usuário durante as requisições subsequentes.
- **Uso de `<c:url>`**: A tag `<c:url>` insere automaticamente o identificador da sessão (JSESSIONID) na URL, garantindo que a sessão do usuário seja mantida mesmo sem o suporte de cookies.

---

### **Exemplo Prático de Uso da Tag `<c:url>` com Sessão**
Aqui está como implementar isso em uma página JSP:

```jsp
<h2>Tag c:url com Session</h2>
<p>
    Quando os cookies estão desabilitados, o identificador de sessão (JSESSIONID) será adicionado à URL automaticamente:
</p>
<p>
    Link para outra página (com identificador de sessão embutido na URL):
    <a href="<c:url value='/outraPagina.jsp' />">Ir para Outra Página</a>
</p>
```

---

### **O que acontece internamente?**
1. Se os cookies estiverem habilitados:
  - O identificador da sessão (JSESSIONID) será passado como parte dos **headers HTTP**.
  - A URL gerada pelo `<c:url>` será algo como:
    ```
    http://localhost:8080/outraPagina.jsp
    ```

2. Se os cookies estiverem desabilitados:
  - O `<c:url>` incluirá automaticamente o JSESSIONID na URL, algo como:
    ```
    http://localhost:8080/outraPagina.jsp;jsessionid=12345ABCDE
    ```

---

### **Por que `<c:url>` é preferível nesse caso?**
- A tag `<c:url>` abstrai a lógica para incluir o **JSESSIONID** na URL apenas quando necessário, evitando que você precise escrever essa lógica manualmente.
- Ela é útil em aplicativos que precisam funcionar corretamente em navegadores com **cookies desativados**.

---

### **Exemplo de Cenário Real**
Imagine um sistema de compras onde os usuários podem navegar entre várias páginas. Se um usuário desativar cookies, sem o `<c:url>` e o JSESSIONID na URL, o servidor não conseguirá identificar a sessão do usuário, o que pode resultar em perda de informações importantes, como itens no carrinho.

Com o `<c:url>`, mesmo que os cookies estejam desabilitados, a sessão será mantida, garantindo a continuidade da navegação do usuário.

---

### **Nota Adicional**
Se você estiver configurando o Tomcat ou outro servidor de aplicações, certifique-se de que a aplicação esteja configurada para **reconhecer sessões baseadas em URL** quando necessário. Isso é feito configurando a propriedade `disableURLRewriting` no arquivo `web.xml` (por padrão, essa funcionalidade está habilitada).

#### c:redirect
```jsp
<c:redirect url="/nova-pagina.jsp">
    <c:param name="msg" value="redirecionado"/>
</c:redirect>
```
- **Uso**: Redireciona para outra página
- **Atributos**:
    - url: destino do redirecionamento
    - context: contexto da aplicação

## 2. Formatting Tags (prefix="fmt")
URI: `http://java.sun.com/jsp/jstl/fmt`

### 2.1 Números

#### fmt:formatNumber
```jsp
<!-- Formato moeda -->
<fmt:formatNumber value="${valor}" type="currency"/>

<!-- Formato percentual -->
<fmt:formatNumber value="${valor}" type="percent"/>

<!-- Formato customizado -->
<fmt:formatNumber value="${valor}" pattern="#,##0.00"/>
```
- **Atributos**:
    - value: número a ser formatado
    - type: tipo de formatação (number, currency, percent)
    - pattern: padrão de formatação
    - currencyCode: código da moeda
    - maxFractionDigits: máximo de casas decimais

### 2.2 Datas

#### fmt:formatDate
```jsp
<!-- Data curta -->
<fmt:formatDate value="${data}" type="date" dateStyle="short"/>

<!-- Data e hora -->
<fmt:formatDate value="${data}" type="both" dateStyle="full" timeStyle="short"/>

<!-- Padrão customizado -->
<fmt:formatDate value="${data}" pattern="dd/MM/yyyy HH:mm"/>
```
- **Atributos**:
    - value: data a ser formatada
    - type: tipo (date, time, both)
    - dateStyle: estilo da data (short, medium, long, full)
    - timeStyle: estilo da hora
    - pattern: padrão de formatação

### 2.3 Internacionalização

#### fmt:setLocale
```jsp
<fmt:setLocale value="pt_BR"/>
```
- **Uso**: Define o local para formatação

#### fmt:setBundle
```jsp
<fmt:setBundle basename="messages"/>
```
- **Uso**: Define o arquivo de recursos

#### fmt:message
```jsp
<fmt:message key="welcome" var="welcomeMsg"/>
<h1>${welcomeMsg}</h1>
```
- **Uso**: Recupera mensagem do arquivo de recursos

## 3. SQL Tags (prefix="sql")
URI: `http://java.sun.com/jsp/jstl/sql`

### 3.1 Conexão

#### sql:setDataSource
```jsp
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost/db"
    user="user" password="pass"/>
```
- **Atributos**:
    - var: variável para DataSource
    - driver: classe do driver JDBC
    - url: URL de conexão
    - user/password: credenciais

### 3.2 Operações

#### sql:query
```jsp
<sql:query var="result" dataSource="${ds}">
    SELECT * FROM usuarios WHERE id = ?
    <sql:param value="${param.id}"/>
</sql:query>
```
- **Uso**: Executa consulta SELECT
- **Atributos**:
    - var: variável para resultado
    - dataSource: fonte de dados
    - maxRows: máximo de linhas

#### sql:update
```jsp
<sql:update dataSource="${ds}">
    INSERT INTO usuarios (nome, email) VALUES (?, ?)
    <sql:param value="${param.nome}"/>
    <sql:param value="${param.email}"/>
</sql:update>
```
- **Uso**: Executa INSERT, UPDATE ou DELETE

## 4. XML Tags (prefix="x")
URI: `http://java.sun.com/jsp/jstl/xml`

### 4.1 Processamento

#### x:parse
```jsp
<x:parse xml="${xmlDoc}" var="doc"/>
```
- **Uso**: Parse de documento XML

#### x:out
```jsp
<x:out select="$doc/usuarios/usuario/nome"/>
```
- **Uso**: Exibe valor de expressão XPath

### 4.2 Controle de Fluxo XML

#### x:if
```jsp
<x:if select="$doc/usuarios/usuario[@tipo='admin']">
    <!-- conteúdo -->
</x:if>
```
- **Uso**: Condional baseado em XPath

#### x:forEach
```jsp
<x:forEach select="$doc/usuarios/usuario" var="user">
    <x:out select="$user/nome"/>
</x:forEach>
```
- **Uso**: Iteração sobre nós XML

### 4.3 Transformação

#### x:transform
```jsp
<x:transform xml="${xmlDoc}" xslt="${xsltDoc}"/>
```
- **Uso**: Aplica transformação XSLT