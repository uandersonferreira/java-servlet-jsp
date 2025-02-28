<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
  
  <%-- Criar dois inpunts buttons para enviar dados e receber dados --%>
  <form id = "form">
    <label for = "name">Nome:</label><br>
    <input type = "text" id = "name" name = "name"><br>
    <label for = "idade">Idade:</label><br>
    <input type = "number" id = "idade" name = "idade"><br>
  <input type = "button" id = "btnEnviar" value = "Enviar Dados">
  </form>
  
  <input type = "button" id = "btnReceber" value = "Receber Dados">
  
  <div id="mostrarDados">
  
  </div>
  
  <script src = "script.js"></script>
  
</body>
</html>