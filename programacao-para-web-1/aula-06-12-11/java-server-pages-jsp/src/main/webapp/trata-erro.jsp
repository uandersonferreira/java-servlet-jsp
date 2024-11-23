<%--
  Created by IntelliJ IDEA.
  User: uanderson
  Date: 10/11/2024
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page
        contentType="text/html;charset=UTF-8"
        language="java"
        isErrorPage="true"
%>

<html>
<head>
    <title>Tratamento de erro</title>
</head>
<body>

<h1>Opa, ocorreu um erro!!!</h1>

<p>Ocorreu um erro inesperado na sua requisição. Por favor, tente novamente mais tarde.</p>

<%
    //imprime o erro que ocorreu
    out.println(exception);
%>

<%= " <h3>  Usando expressões (´< %= % >) para mostrar conteúdo na tela, sem o uso de out.println()</h3> " %>

</body>
</html>

<%--

isErrorPage="true" -> define que essa página é uma página de tratamento de erro;
                exception -> é uma variável que contém o erro que ocorreu na página,
                e que pode ser acessada dentro do escopo da página de tratamento de erro,
                ela é criada automaticamente pelo servidor de aplicação, quando a página de
                tratamento de erro é criada;

Scriptlets "< % % >"  é diferente de expressões (´< %= % >)

Scriptlets -> é uma forma de adicionar código Java dentro do html, é como se eu estivesse dentro dos metodos
             doGet(), doPost(), doPut(), doDelete(), etc.

Expressões (´< %= % >) -> é uma forma de imprimir o conteúdo sem o uso do out.println(), pois é
                        como se eu estivesse dentro dos out.println("ESTARIA AQUI DENTRO");
                        No caso o conteudo passado entre as tags  (´< %= CONTEUDO  % >)

expressões (´< %= % >) -> é uma forma de imprimir o conteúdo sem o uso do out.println(),
                        pois o conteudo entre as tags é o parametro passado para a expressão,
                        e o resultado da expressão é impresso na tela;
 --%>