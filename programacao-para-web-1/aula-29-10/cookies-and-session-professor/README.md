# COKKIES

Serve para armazenar dados no cliente (Navegador). Guardar dados pequenos 
no proprio navegador.

## Tipos

### Cookies de sessão

- São removidos quando o cliente fechar o navegador
- Fica na mermória RAM

### Cookies persistentes

- São cookies que permanecem no cliente mesmo que ele feche o navegador
- Fica salvo o disco
- Pode ser eliminado pelo cliente quando ele limpar os cookies do
  navegador

## Criando um Cookie

- Instancie a classe Cookie do pacote javax.servlet.http
- O método construtor recebe dois parâmetros, o nome e o
  valor do cookie
- Chame o método setMaxAge para definir o tempo de

## vida do cookie.

- Valores positivos representam o tempo de vida em segundos
- Valores negativos representam que o cookie não é persistente
- Valor zero indica para remover o cookie
- Chame o método addCookie do response

## Criando um Cookie

```java
Cookie c = new Cookie("chave", "1234");
c.setMaxAge(3600);
response.addCookie(c);
```

## Recuperando um Cookie

- Chame o método getCookies do request, ele irá retornar
  um vetor de cookies
- Faça um laço para percorrer o vetor de cookies e para
  cada cookie utilize os métodos:
    - getName
    - getValue

## Recuperando um Cookie

```java
  Cookie cs[] = request.getCookies();
  for(Cookie c :cs){
      out.print("<p><b>"+c.getName() +"</b>: "+ c.getValue() +"</p>");
  }
```



