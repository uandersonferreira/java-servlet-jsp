<%--
  Created by IntelliJ IDEA.
  User: uanderson
  Date: 09/12/2024
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<html>
  <head>
    <title>JSP - Encaminhamento</title>
  </head>
  <body>
    <%
      request.setAttribute("dado", request.getParameter("numero"));
    %>
    <h1>JSP:forward - Encaminhamento</h1>
    <jsp:forward page="mostrar_numero.jsp"> </jsp:forward>
  
  </body>
</html>
