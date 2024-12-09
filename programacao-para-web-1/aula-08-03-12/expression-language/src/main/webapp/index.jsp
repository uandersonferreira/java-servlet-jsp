<%@ page import = "java.util.List" %><%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Expression Language</title>
</head>
<body>
  <%
	  List<Integer> numbersList = List.of(1, 2, 3, 4, 5);
	  pageContext.setAttribute("lista", numbersList);
  %>
  
  <h1>JSP - Expression Language</h1>
  <p>Sintaxe: <code>$ { expressão }</code> </p>
  
  Exemplo de EL com literais
  
   <p> ${true} </p>
   <p> ${false} </p>
   <p> ${"Ola 'meu' Mundo"} </p>
   <p> ${'Ola "meu" Mundo'} </p>
   <p> ${2*4-3} </p>
  <p>Soma (5 + 3): ${5+3} </p>
  
  <h2> Operador . (ponto)</h2>
  <ul>
    <li> O operador . (ponto) acessa as propriedades de um
  objeto ou um valor mapeado de um mapeamento.</li>
    <li> Os objetos implícitos são mapeamentos (exceto
  o pageContext), são conjuntos de chaves e valores</li>
  </ul>
  
  <p>Seja bem-vindo, ${param.nome}</p>
  
  <h2> Operador [] (colchetes)</h2>
  <ul>
    <li> faz o que o operador . (ponto) faz e mais um pouco </li>
    <li> Serve também para acessar array e List </li>
  </ul>
  
  <p>
    Exemplo: <br>
    pessoa["nome"] <br>
    param["nome"] <br>
    vetor[0] <br>
    lista[0] <br>
    vetor["1"] //a string será convertida em inteiro <br>
  </p>
  
  <p>Seja bem-vindo, ${param["nome"]}</p>
  
  <p>Minha lista é: ${pageScope.lista}</p>
  <p>Minha lista no indice 2 é: ${pageScope.lista[2]} </p>
  <p>Minha lista no indice 10 é: ${pageScope.lista[10]} | Não gera erro caso não exista</p>

  <h2>Objetos Implícitos</h2>
  <form action = "encaminhamento.jsp" method="post">
    <input type = "number" name = "numero">
    <input type = "submit" value = "Enviar">
  </form>

  
  
  
  
</body>
</html>