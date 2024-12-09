<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<html>
  <head>
    <title>Mostrar Número</title>
    <style>
      .numero-destaque {
        text-align: center;
        font-size: 2em;
        font-weight: bold;
        color: #0066cc;
        padding: 20px;
        background-color: #f0f0f0;
        border-radius: 10px;
        display: block;
        width: 50%;
        margin: 50px auto;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      }
    </style>
  </head>
  <body>
    <h1>Estou na página Mostrar Número</h1>
    <p class="numero-destaque">O número é ${requestScope.dado}</p>
  </body>
</html>
