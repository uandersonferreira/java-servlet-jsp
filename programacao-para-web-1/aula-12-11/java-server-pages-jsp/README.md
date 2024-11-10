# Java server Pages - JSP


## Recursos Tags JSP

- Scriptlets ->  <%   %>
- Directives ->  <%@   %>
- Expressions ->  <%=   %>
- Declarations ->  <%!   %>
- Comments ->  <%--   --%>

## Objetos Implícitos no JSP

- JspWriter -> out -> Função: Envia saída para o cliente.
- HttpServletRequest -> request
- HttpServletResponse -> response
- HttpSession -> session
- ServletContext -> application
- ServletConfig -> config
- JspException -> exception
- PageContext -> pageContext
- Object -> page
---

## **Tags JSP**

| Tag | Sintaxe | Uso | Exemplo |
|-----|---------|-----|---------|
| Scriptlets | `<%  %>` | Código Java executado no servidor | `<% for(int i=0; i<5; i++) { %>` |
| Directives | `<%@  %>` | Configurações da página JSP | `<%@ page import="java.util.*" %>` |
| Expressions | `<%=  %>` | Exibe valor de variável/expressão | `<%= usuario.getNome() %>` |
| Declarations | `<%!  %>` | Declara métodos/variáveis de classe | `<%! private int contador = 0; %>` |
| Comments | `<%--  --%>` | Comentários (não visíveis no HTML) | `<%-- Debug info --%>` |

## **Objetos Implícitos**

| Objeto | Nome | Representa | Uso Comum |
|--------|------|------------|-----------|
| out | JspWriter | Saída da página | Escrever conteúdo na resposta |
| request | HttpServletRequest | Requisição HTTP | Obter parâmetros, headers, cookies |
| response | HttpServletResponse | Resposta HTTP | Definir headers, cookies, redirect |
| session | HttpSession | Sessão do usuário | Armazenar dados entre requisições |
| application | ServletContext | Contexto da aplicação | Dados compartilhados entre usuários |
| config | ServletConfig | Configuração do servlet | Parâmetros de inicialização |
| exception | JspException | Exceções da página | Tratamento de erros (pages de erro) |
| pageContext | PageContext | Contexto da página | Acesso a outros objetos implícitos |
| page | Object | Página atual | Raramente usado (this) |

Variações importantes:
1. Directives têm três tipos:
    - `<%@ page ... %>` - Configurações da página
    - `<%@ include ... %>` - Inclusão de arquivos
    - `<%@ taglib ... %>` - Declaração de bibliotecas de tags

2. Expressions (`<%=  %>`) é equivalente a `out.print()` em scriptlets

3. Comments podem ser feitos de três formas:
    - `<%-- ... --%>` (JSP comment - não vai para o HTML)
    - `<!-- ... -->` (HTML comment - vai para o HTML)
    - `//` ou `/* */` (dentro de scriptlets)

    
## **Escopo de Classe (Fora dos métodos)**
- `<%! ... %>` (Declarations)
    - Equivalente a escrever no nível da classe
    - Variáveis/métodos são compartilhados entre todas as requisições
    - Pode declarar métodos e variáveis de instância
    - Persistem durante todo ciclo de vida do servlet

Exemplo:
```jsp
<%! 
    private int contador = 0;  // variável de instância
    public String getInfo() {  // método da classe
        return "Informação";
    }
%>
```

**Escopo de Serviço (Dentro do _jspService/doGet/doPost)**
- `<% ... %>` (Scriptlets)
- `<%= ... %>` (Expressions)
    - Equivalente a escrever dentro do método _jspService
    - Variáveis são locais à requisição atual
    - Cada usuário tem sua própria cópia
    - São recriadas a cada requisição

Exemplo:
```jsp
<%
    String nome = request.getParameter("nome"); // variável local
    int idade = 25; // só existe durante esta requisição
%>
```

**Importante saber:**
1. Scriptlets e Expressions compartilham o mesmo escopo (método)
2. Declarations são mais raramente usadas pois podem causar problemas de concorrência
3. Use Declarations apenas quando precisar compartilhar estado entre requisições
4. Declarations não têm acesso direto aos objetos implícitos (request, response, etc)
5. O uso moderno de JSP geralmente evita scriptlets e declarations, preferindo JSTL e Expression Language (EL)

