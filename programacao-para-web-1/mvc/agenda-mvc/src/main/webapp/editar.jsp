<%@ page import="br.com.uanderson.agendamvc.model.Contato" %><%--
  Created by IntelliJ IDEA.
  User: uanderson
  Date: 24/11/2024
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agenda</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }

        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }

        form {
            max-width: 500px;
            margin: 0 auto;
            background-color: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="text"]:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 5px rgba(76, 175, 80, 0.2);
        }

        button[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }

        button[type="submit"]:hover {
            background-color: #45a049;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #666;
            text-decoration: none;
        }

        a:hover {
            color: #333;
            text-decoration: underline;
        }

    </style>
</head>
<body>
<h1>Editar Contato</h1>
<%
    Contato contato = (Contato) request.getAttribute("contato");
%>

<form action="editar" method="post">
    <input type="hidden" name="codigo" value="${contato.codigo}" />

    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" value="${contato.nome}" required />

    <label for="telefone">Telefone:</label>
    <input type="text" id="telefone" name="telefone" value="${contato.telefone}" required />

    <button type="submit">Salvar</button>
</form>

<a href="listar">Cancelar</a>
</body>

</html>
