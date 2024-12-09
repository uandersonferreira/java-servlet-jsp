<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.Map" %>
<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html lang = "pt-br">
<html>
<head>
    <meta charset = "UTF-8">
    <meta name = "viewport" content = "width=device-width, initial-scale=1.0">
    <title>Meu Sistema de Login</title>
    <link rel = "stylesheet" href = "asserts/css/estilo.css">
    <link rel = "stylesheet" href = "asserts/css/alert-msg.css">
    <link rel = "stylesheet" href = "asserts/css/relatorio.css">
</head>
<body>

<header>
    <a href = "#" class = "logo">Uanderson <span>.</span></a>
     <nav>
          <ul class = "navigation">
              <li><a class = "link-destaque" href = "index.jsp">Home</a></li>
              <li><a class = "link-destaque" href = "relatorio.jsp">Relatorio</a></li>
              
              <% if (session.getAttribute("usuario") != null) { %>
                  <li><a class = "link-destaque" href = "logout">Sair</a></li>
              <% } %>
              
          </ul>
     </nav>
</header>

<%@include file="alert-msg.jsp"%>