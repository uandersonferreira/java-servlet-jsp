<%--
  Created by IntelliJ IDEA.
  User: uanderson
  Date: 09/12/2024
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
    <h1>Mostra Pessoa</h1>
    
    <jsp:useBean id = "pessoa" class = "br.com.uanderson.expressionlanguage.modelo.Pessoa">
        <jsp:setProperty name = "pessoa" property = "*"/>
    </jsp:useBean>
    
    <p>O nome é: ${pessoa.nome}</p>
    <p>A idade é: ${pessoa.idade}</p>
  
  </body>
</html>
