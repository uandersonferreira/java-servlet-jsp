<%--
  Created by IntelliJ IDEA.
  User: uanderson
  Date: 02/12/2024
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<html>
  <head>
    <title>Mostrar Nome</title>
  </head>
<body>
  
    <h1>Teste JSP:Forward</h1>
    <jsp:forward page="index.jsp">
      <jsp:param name="erro" value="Não foi possível mostrar o nome"/>
    </jsp:forward>
</body>
</html>
