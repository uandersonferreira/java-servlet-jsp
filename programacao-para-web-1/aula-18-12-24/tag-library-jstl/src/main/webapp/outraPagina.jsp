<%--
  Created by IntelliJ IDEA.
  User: uanderson
  Date: 16/12/2024
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<html>
  <head>
    <title>Outra Página JSP </title>
  </head>
  <body>
    
    <h1>Outra Página JSP</h1>
    <p>Esta é outra página JSP.</p>
    <p>Ela é acessada a partir do import na página principal.</p>
    
    <p>Parâmetro recebido: ${param.nome}</p>
    <h2>Bem-vindo(a): ${param.nome} </h2>
    
  </body>
</html>
