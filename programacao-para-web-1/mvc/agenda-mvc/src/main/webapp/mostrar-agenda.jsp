<%@ page import="br.com.uanderson.agendamvc.model.Agenda" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: uanderson
  Date: 23/11/2024
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cadastrar Agenda</title>
</head>
<body>

<h1>Cadastrar Agenda</h1>

<%

  Agenda agendaContext = (Agenda) application.getAttribute("agendaContext");
  if (agendaContext != null) {
      out.println(agendaContext + "\n");
  }else{
      out.println("<p> Lista Vazia! </p>");
    }
%>


</body>
</html>
