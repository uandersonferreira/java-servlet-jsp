# Guia Prático sobre JSP e Ações Padrão

Este documento aborda os conceitos e exemplos básicos sobre as ações padrão do JSP (`<jsp:useBean />`,
`<jsp:getProperty />`, `<jsp:setProperty />`), baseando-se em boas práticas e exemplos. Essas ações são amplamente
utilizadas em aplicações web para interagir com objetos JavaBeans e preencher dados de formulários.

---

# **Ação Padrão - Standart Actions**

- São tags XML que podem ser inseridas no código JSP e
  trazem funcionalidades lógicas com uma sintaxe
  simples
- O container transforma as ações padrão em código Java
  durante a compilação do JSP
- Com as ações padrão é possível, dentre outras coisas:
  instanciar objetos, definir ou obter propriedades, incluir
  arquivos, reencaminhar requisições, etc.


## **1. Ação `<jsp:include />`**

A ação `<jsp:include />` é utilizada para incluir um conteúdo externo (outro arquivo JSP, por exemplo) em uma página JSP. Diferentemente da diretiva `<%@ include %>`, que é processada em **tempo de compilação**, a ação `<jsp:include />` é executada em **tempo de requisição**, permitindo a inclusão de conteúdos dinâmicos.

### Diferenças:

| Diretiva Include (`<%@ include %>`)        | Ação Include (`<jsp:include />`)            |
|-------------------------------------------|---------------------------------------------|
| Executada em **tempo de compilação**.     | Executada em **tempo de requisição**.       |
| Conteúdo incluído é estático.             | Conteúdo incluído pode ser dinâmico.        |
| Usa a sintaxe: `<%@ include file="..." %>`| Usa a sintaxe: `<jsp:include page="..." />` |

### Estrutura:
```jsp
<jsp:include page="caminhoDoArquivo" />
```

### Exemplo:
#### Arquivo `cabecalho.jsp`:
```jsp
<% out.print("<header>Cabeçalho</header>"); %>
```

#### Arquivo `pagina.jsp`:
```jsp
<body>
    <jsp:include page="cabecalho.jsp" />
    <p>Conteúdo principal da página</p>
</body>
```

- Nesse exemplo, o conteúdo de `cabecalho.jsp` será incluído dinamicamente na página `pagina.jsp` a cada requisição.

---

## **2. Ação `<jsp:param />`**

A ação `<jsp:param />` é usada para enviar parâmetros de uma página JSP para outra página incluída ou encaminhada.

### Estrutura:
```jsp
<jsp:include page="destino.jsp">
    <jsp:param name="nomeDoParametro" value="valorDoParametro" />
</jsp:include>
```

### Exemplo:
#### Arquivo `pagina.jsp`:
```jsp
<jsp:include page="boasVindas.jsp">
    <jsp:param name="nome" value="José" />
</jsp:include>
```

#### Arquivo `boasVindas.jsp`:
```jsp
<%
    out.print("Seja bem-vindo, " + request.getParameter("nome") + "!");
%>
```

- Resultado renderizado no navegador:  
  **Seja bem-vindo, José!**

---

## **3. Ação `<jsp:forward />`**

A ação `<jsp:forward />` redireciona a requisição para outra página JSP, Servlet ou outro recurso no servidor. Após o redirecionamento, nenhuma parte do código restante da página original será executada.

### Estrutura:
```jsp
<jsp:forward page="destino.jsp" />
```

### Exemplo Básico:
```jsp
<jsp:forward page="outraPagina.jsp" />
```

- Encaminha a requisição para `outraPagina.jsp`.

### Enviando Parâmetros com `<jsp:forward />`:
#### Arquivo `pagina.jsp`:
```jsp
<jsp:forward page="outraPagina.jsp">
    <jsp:param name="mensagem" value="Olá, mundo!" />
</jsp:forward>
```

#### Arquivo `outraPagina.jsp`:
```jsp
<%
    String mensagem = request.getParameter("mensagem");
    out.print(mensagem); // Resultado: Olá, mundo!
%>
```

---

## **4. Atributo `scope` no `<jsp:useBean />`**

O atributo `scope` define onde o objeto criado ou referenciado pelo `<jsp:useBean />` estará disponível. Os valores possíveis para o atributo `scope` são:

| **Escopo**    | **Descrição**                                                                                                                                           |
|---------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`page`**    | (Padrão) O objeto está disponível apenas na página JSP atual.                                                    |
| **`request`** | O objeto estará disponível durante a requisição HTTP atual, podendo ser acessado por outras páginas ou Servlets que fazem parte da mesma requisição.    |
| **`session`** | O objeto estará disponível durante toda a sessão do usuário.                                                     |
| **`application`** | O objeto estará disponível em toda a aplicação, podendo ser acessado por qualquer página ou Servlet enquanto a aplicação estiver ativa.             |

### Exemplo:
```jsp
<jsp:useBean id="pessoa" class="pacote.Pessoa" scope="request" />
<jsp:setProperty name="pessoa" property="nome" value="José" />
```

- Se já existir um atributo chamado `pessoa` no escopo `request`, ele será referenciado. Caso contrário, será criado um novo objeto `Pessoa`.

---

## **5. Resumo das Ações**

| **Ação**         | **Descrição**                                                                                                                                                     |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `<jsp:useBean />` | Cria ou referencia um objeto JavaBean, podendo definir seu escopo.                                                                                              |
| `<jsp:getProperty />` | Obtém o valor de uma propriedade de um objeto JavaBean.                                                                                                     |
| `<jsp:setProperty />` | Define o valor de uma propriedade de um objeto JavaBean, podendo usar valores fixos ou parâmetros da requisição.                                             |
| `<jsp:include />` | Inclui dinamicamente outro recurso JSP ou Servlet na página atual, permitindo conteúdo dinâmico.                                                                |
| `<jsp:param />`   | Passa parâmetros de uma página para outra ao usar `<jsp:include />` ou `<jsp:forward />`.                                                                        |
| `<jsp:forward />` | Encaminha a requisição para outro recurso no servidor (página JSP, Servlet, etc.), podendo passar parâmetros com `<jsp:param />`.                                |

---

## **6. Exemplos Combinados**

### Exemplo Completo:
#### Formulário HTML:
```html
<form action="processa.jsp" method="post">
    <input type="text" name="nome" placeholder="Digite seu nome" />
    <button type="submit">Enviar</button>
</form>
```

#### Arquivo `processa.jsp`:
```jsp
<jsp:useBean id="pessoa" class="pacote.Pessoa" scope="request" />
<jsp:setProperty name="pessoa" property="*" />

<jsp:include page="boasVindas.jsp">
    <jsp:param name="extra" value="Parabéns por usar JSP!" />
</jsp:include>
```

#### Arquivo `boasVindas.jsp`:
```jsp
<%
    String nome = request.getParameter("nome");
    String extra = request.getParameter("extra");
    out.print("Seja bem-vindo, " + nome + "! " + extra);
%>
```


## **7. Ação `<jsp:useBean />`**

A tag `<jsp:useBean />` é usada para instanciar uma classe Java ou referenciar um objeto já existente no escopo
especificado. Ela trabalha diretamente com classes JavaBeans.

### Estrutura:

```jsp
<jsp:useBean id="identificador" class="pacote.Classe" scope="escopo" />
```

- **`id`**: Identificador único do objeto.
- **`class`**: Nome da classe que será instanciada.
- **`scope`**: Define o escopo do objeto (`page`, `request`, `session`, ou `application`). O escopo padrão é `page`.

### Exemplo:

```jsp
<jsp:useBean id="pessoa" class="pacote.Pessoa" />
<%= pessoa.getNome() %>
```

- Supondo que exista uma classe `Pessoa` com um método `getNome()`, o exemplo acima cria uma instância da classe
  `Pessoa` e permite acessar sua propriedade `nome`.

---

## **8. Ação `<jsp:getProperty />`**

A tag `<jsp:getProperty />` é usada para acessar uma propriedade de um objeto JavaBean instanciado com
`<jsp:useBean />`.

### Estrutura:

```jsp
<jsp:getProperty name="id_do_bean" property="nome_da_propriedade" />
```

- **`name`**: Identificador do objeto (definido em `<jsp:useBean />`).
- **`property`**: Nome da propriedade a ser acessada.

### Exemplo:

```jsp
<jsp:useBean id="pessoa" class="pacote.Pessoa" />
<jsp:getProperty name="pessoa" property="nome" />
```

- Para que isso funcione, a classe `Pessoa` deve ter um método `getNome()` que retorna uma `String`.

---

## **9. Ação `<jsp:setProperty />`**

A tag `<jsp:setProperty />` é usada para definir uma propriedade de um objeto JavaBean.

### Estrutura:

```jsp
<jsp:setProperty name="id_do_bean" property="nome_da_propriedade" value="valor" />
```

- **`name`**: Identificador do objeto (definido em `<jsp:useBean />`).
- **`property`**: Nome da propriedade a ser definida.
- **`value`**: Valor que será atribuído à propriedade.

### Exemplo:

```jsp
<jsp:useBean id="pessoa" class="pacote.Pessoa" />
<jsp:setProperty name="pessoa" property="nome" value="Pedro" />
<jsp:getProperty name="pessoa" property="nome" />
```

- Para que isso funcione, a classe `Pessoa` deve ter um método `setNome(String)`.

---

## **10. Preenchendo um JavaBean com Parâmetros da Requisição**

### Usando o atributo `param`:

```jsp
<form action="pagina.jsp" method="post">
    <input type="text" name="nomeDaPessoa" />
</form>

<jsp:useBean id="pessoa" class="pacote.Pessoa" />
<jsp:setProperty name="pessoa" property="nome" param="nomeDaPessoa" />
```

- O atributo `param` especifica qual parâmetro da requisição deve ser usado para preencher a propriedade.

### Omitindo o atributo `param`:

Se o nome do campo no formulário HTML for igual ao nome da propriedade, o atributo `param` pode ser omitido:

```jsp
<form action="pagina.jsp" method="post">
    <input type="text" name="nome" />
</form>

<jsp:useBean id="pessoa" class="pacote.Pessoa" />
<jsp:setProperty name="pessoa" property="nome" />
```

---

### Preenchendo Todas as Propriedades:

```jsp
<jsp:useBean id="pessoa" class="pacote.Pessoa" />
<jsp:setProperty name="pessoa" property="*" />
```

- O valor `"*"` indica que todas as propriedades do objeto serão preenchidas automaticamente com os parâmetros da
  requisição que tenham nomes correspondentes às propriedades.

---

## **11. Atributo `scope` do `<jsp:useBean />`**

O atributo `scope` controla onde o objeto será armazenado e compartilhado.

- **`page`**: (Padrão) O objeto estará disponível apenas na página atual.
- **`request`**: O objeto estará disponível durante a requisição HTTP atual.
- **`session`**: O objeto será armazenado na sessão do usuário.
- **`application`**: O objeto será compartilhado por toda a aplicação.

### Exemplo:

```jsp
<jsp:useBean id="pessoa" class="pacote.Pessoa" scope="request" />
<jsp:setProperty name="pessoa" property="nome" value="José" />
```

- Se já existir um atributo chamado `pessoa` no escopo `request`, ele será referenciado. Caso contrário, um novo objeto
  será criado.

---

## **12. Resumo das Ações Padrão**

| Ação                  | Finalidade                                   |
|-----------------------|----------------------------------------------|
| `<jsp:useBean />`     | Instancia ou referencia um objeto JavaBean.  |
| `<jsp:getProperty />` | Obtém o valor de uma propriedade do objeto.  |
| `<jsp:setProperty />` | Define o valor de uma propriedade do objeto. |

---

## **13. Exemplos Completos**

### Exemplo com Formulário:

```jsp
<form action="processa.jsp" method="post">
    <input type="text" name="nome" placeholder="Nome" />
    <input type="text" name="telefone" placeholder="Telefone" />
    <button type="submit">Enviar</button>
</form>

<%-- processa.jsp --%>
<jsp:useBean id="pessoa" class="pacote.Pessoa" />
<jsp:setProperty name="pessoa" property="*" />
<p>Nome: <jsp:getProperty name="pessoa" property="nome" /></p>
<p>Telefone: <jsp:getProperty name="pessoa" property="telefone" /></p>
```

---

## ** Referências**

1. Basham, Bryan; Sierra, Kathy; Bates, Bert. *Use a Cabeça! Servlets & JSP.* Alta Books, 2008.
2. Gonçalves, Edson. *Desenvolvendo Aplicações Web com JSP, Servlets, JSF, EJB 3, Ajax.* Rio de Janeiro, 2007.

---
