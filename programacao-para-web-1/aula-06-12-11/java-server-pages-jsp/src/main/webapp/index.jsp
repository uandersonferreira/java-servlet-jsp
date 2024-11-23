<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         errorPage="trata-erro.jsp"
%>

<html>
<head>
    <title>JSP Page</title>
</head>
<body>

<%@include file="cabecalho.jsp" %>

<h1>Hello World!</h1>

<%--  Permite Adicionar código Java dentro do html usando a tag JSP (Scriptlets "<% %>")--%>
<%! //Declaração - scope de classe é não de method, logo é onde declaramos as variaveis e metodos igual a classe em Java

    public void jspInit(){
      //Chamado quando o servlet do JSP é inicializado
      contador = 100;
    }

    int contador = 0;
    public String message(){
      return "Mensagem escrita dentro de Method declarado em uma Tag de Declaração que possui escopo de classe no JSP";
    }

  public void jspDestroy() {
      //Chamado quando o servlet do JSP é destruído
  }
%>

<% //scriptlets - scopo de method do doGet(), doPost(), doPut(), doDelete(), etc.

    // Tudo nesse escopo é código Java
    String message = "Mensagem escrita dentro de uma tag JSP que permite adicionar código Java dentro do html";
    out.println("<h2>" + message + "</h2>");

    //gerando um erro ao dividir por zero - java.lang.ArithmeticException: / by zero
    //out.println(10/0);

    //Contar quantas vezes o usuário acessa a página
    out.println("Visitou: " + contador + " Vezes");
    contador++;

    out.println("<br>");
    out.println(message());

%>

</body>
</html>

<%--
 @page -> é uma diretiva de jsp. Responsável por trazer informações sobre a página JSP;
        - language="java" - informa que a linguagem de programação utilizada é a Java;
        - contentType="text/html;charset=UTF-8" - informa que o conteúdo da página é um documento HTML e
          que o charset é UTF-8;
        - pageEncoding="UTF-8" - informa que a codificação da página é UTF-8;
        - import="java.util.*" - informa que estamos importando a biblioteca inteira do java.util.*;

Scriptlets  "< % % >"  -> é uma forma de adicionar código Java dentro do html, é como se eu estivesse dentro dos metodos
             doGet(), doPost(), doPut(), doDelete(), etc.

Comentários HTML - Vai para o lado do cliente (É mostardo no Navegador)
Comentários JSP - Vai para o lado do servidor (Não é mostrado no Navegador)

--%>