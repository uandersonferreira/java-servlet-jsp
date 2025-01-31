<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href = "ConnectionDatabaseServlet">Listar Produtos</a>
  <form action="ConnectionDatabaseServlet" method="GET">
    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" required>
    <br>
    <label for="descricao">Descrição:</label>
    <input type="text" id="descricao" name="descricao" required>
    <br>
    <label for="preco">Preço:</label>
    <input type="number" id="preco" name="preco" MIN="0.0" step="0.01" required>
    <br>
    <input type="submit" value="Salvar">
    <input type="reset" value="Limpar">
  </form>
  
  
  
</body>
</html>