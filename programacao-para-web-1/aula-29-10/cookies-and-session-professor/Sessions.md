# Sessão

As sessões são usadas para manter dados específicos de um usuário entre várias requisições HTTP, possibilitando que o
servidor identifique o mesmo usuário em diferentes páginas de uma aplicação web.

Como o **protocolo HTTP** é sem estado, ele não armazena informações entre requisições. As sessões são uma solução para
contornar essa limitação, fornecendo uma maneira de **armazenar dados no servidor** e associá-los a um cliente
específico (navegador). Dessa forma, podemos identificar o usuário e manter informações entre suas requisições.

Por exemplo, é através das sessões que você consegue manter-se logado em um sistema após fazer login, permitindo acessar
diversas páginas sem precisar se autenticar novamente.

### Características das Sessões no Jakarta (Servlets)

- **Armazenamento de dados do lado do servidor**: Dados específicos do usuário são armazenados no servidor e associados
  a uma sessão.
- **Identificação única**: Cada sessão possui um identificador único que vincula o usuário ao servidor, mantendo os
  dados disponíveis entre requisições.
- **Implementação com Cookies e URLs**: A identificação da sessão é geralmente feita por cookies, mas também pode ser
  transmitida por meio de URLs (geralmente evitado por motivos de segurança).

## Criando uma Sessão

Para criar ou recuperar uma sessão existente, use o método `getSession` do objeto `HttpServletRequest`:

```java
HttpSession sessao = request.getSession(true);
```

- `getSession(true)`: Retorna a sessão atual. Se a sessão não existir, cria uma nova.
- `getSession(false)`: Retorna a sessão atual, mas se não existir, retorna `null`.
- `getSession()`: Equivalente a `getSession(true)`.

## Identificador da Sessão

Cada sessão possui um identificador único que pode ser obtido com o método `getId`:

```java
String sessionId = sessao.getId();
```

Esse identificador é crucial para o servidor distinguir entre diferentes usuários e sessões.

## Guardando e Recuperando Dados na Sessão

As sessões permitem armazenar objetos associados a um usuário específico usando `setAttribute` e recuperá-los com
`getAttribute`.

### Guardando Dados na Sessão

```java
sessao.setAttribute("nome","João");
```

### Recuperando Dados da Sessão

```java
String nome = (String) sessao.getAttribute("nome");
```

- **Exemplo completo**:
  ```java
  HttpSession sessao = request.getSession();
  sessao.setAttribute("usuario", "João");
  String usuario = (String) sessao.getAttribute("usuario");
  ```

## Removendo Dados da Sessão

Para remover um atributo específico da sessão, use o método `removeAttribute`:

```java
sessao.removeAttribute("nome");
```

## Tempo de Inatividade da Sessão

O tempo de inatividade de uma sessão é o período em que a sessão permanece aberta sem receber novas requisições. O
método `setMaxInactiveInterval` define o tempo máximo de inatividade da sessão em segundos. Após esse tempo, a sessão é
automaticamente invalidada:

```java
sessao.setMaxInactiveInterval(1800); // 30 minutos
```

- **Tempo em segundos**: Define o tempo máximo de inatividade.
- **Valor zero ou negativo**: Indica que a sessão nunca expira automaticamente.
- **Consulta do tempo de inatividade**: Pode-se usar `getMaxInactiveInterval()` para verificar o tempo definido.

## Encerrando uma Sessão

Uma sessão é automaticamente encerrada após o tempo de inatividade configurado, mas pode ser invalidada manualmente a
qualquer momento com `invalidate()`:

```java
sessao.invalidate();
```

O método `invalidate()` encerra a sessão imediatamente e remove todos os dados associados a ela.

---

