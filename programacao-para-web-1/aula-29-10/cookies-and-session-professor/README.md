# COOKIES

Os cookies são utilizados para armazenar dados pequenos no navegador do cliente, permitindo que o servidor identifique e
mantenha informações sobre o usuário em diferentes sessões e interações. Os cookies são definidos pelo servidor e
enviados ao cliente, e então o navegador os armazena e os envia de volta ao servidor em cada requisição subsequente.

## Tipos de Cookies

### Cookies de Sessão

- São temporários e existem apenas enquanto o navegador está aberto.
- São armazenados na memória RAM e não são gravados no disco, o que significa que, ao fechar o navegador, esses cookies
  são automaticamente apagados.
- Ideais para armazenar dados temporários, como uma sessão de login, que não devem persistir entre diferentes sessões de
  navegação.

### Cookies Persistentes

- São cookies que permanecem no cliente mesmo após o fechamento do navegador.
- São salvos no disco rígido, de modo que permanecem disponíveis até que atinjam a data de expiração ou sejam excluídos
  manualmente pelo usuário.
- São úteis para lembrar preferências e dados de autenticação, como um “Lembrar-me” em uma página de login.

## Comportamento dos Cookies no Processo de Requisição e Resposta

1. **Enviando o Cookie do Servidor para o Cliente (Response)**:

- Ao enviar uma resposta para o cliente, o servidor pode definir um cookie usando o cabeçalho `Set-Cookie`.
- Esse cabeçalho contém informações como nome, valor, domínio, caminho, e as configurações de segurança (como `HttpOnly`
  e `Secure`), além do tempo de expiração para cookies persistentes.
- Exemplo do cabeçalho `Set-Cookie`:
  ```
  Set-Cookie: chave=1234; Max-Age=3600; HttpOnly; Secure
  ```

2. **Enviando o Cookie do Cliente para o Servidor (Request)**:

- Em cada requisição subsequente, o navegador automaticamente adiciona o cabeçalho `Cookie` contendo todos os cookies
  armazenados que correspondem ao domínio e caminho do servidor.
- O servidor pode então ler esses cookies e usar suas informações para personalizar a resposta.
- Exemplo do cabeçalho `Cookie`:
  ```
  Cookie: chave=1234; outroCookie=5678
  ```

## Criando e Configurando um Cookie em Java (Servlets)

### Passo 1: Criar e Configurar o Cookie

- Instancie a classe `Cookie` do pacote `javax.servlet.http`.
- Utilize o construtor que recebe dois parâmetros: o nome e o valor do cookie.
- Chame o método `setMaxAge(int segundos)` para definir a duração do cookie em segundos.
    - Valores positivos representam a duração em segundos (ex.: `3600` para 1 hora).
    - Valor negativo (padrão) indica que o cookie é de sessão e será removido ao fechar o navegador.
    - Valor zero indica que o cookie deve ser removido imediatamente.
- Para que o cookie seja enviado ao cliente, utilize o método `addCookie` da resposta (`HttpServletResponse`).

### Exemplo:

```java
Cookie c = new Cookie("chave", "1234");
c.

setMaxAge(3600); // Define que o cookie expira em 1 hora
response.

addCookie(c);
```

### Configurações Adicionais

- **HttpOnly**: Define se o cookie é acessível apenas pelo protocolo HTTP, evitando que scripts do lado do cliente (
  JavaScript) acessem o cookie.
  ```java
  c.setHttpOnly(true);
  ```
- **Secure**: Indica que o cookie deve ser enviado apenas em conexões HTTPS.
  ```java
  c.setSecure(true);
  ```
- **Domain e Path**: Especificam para quais URLs o cookie é válido, permitindo que ele seja compartilhado em subdomínios
  ou diretórios específicos.
  ```java
  c.setDomain("exemplo.com");
  c.setPath("/app");
  ```

## Recuperando um Cookie em Java (Servlets)

1. Utilize o método `getCookies()` do objeto `HttpServletRequest`, que retorna um array de cookies.
2. Percorra o array de cookies para verificar se o nome do cookie desejado existe e, se encontrado, utilize `getName()`
   e `getValue()` para obter suas informações.

### Exemplo:

```java
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (Cookie c : cookies) {
        if (c.getName().equals("chave")) {
            out.print("<p><b>" + c.getName() + "</b>: " + c.getValue() + "</p>");
        }
    }
}
```


