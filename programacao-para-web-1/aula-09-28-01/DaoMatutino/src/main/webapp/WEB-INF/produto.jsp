<%--
  Created by IntelliJ IDEA.
  User: professor
  Date: 28/01/2025
  Time: 08:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="inserir" method="post">
        <label>Nome
            <input type="text" name="nome">
        </label>
        <label>Descrição
            <input type="text" name="descricao">
        </label>
        <label>Preço
            <input type="number" name="preco">
        </label>
        <input type="submit" value="Iserir Produto">
    </form>
    <table border="1">
        <thead>
            <tr>
                <th>Id</th>
                <th>Nome</th>
                <th>Descrição</th>
                <th>Preço</th>
                <th>Deletar</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${requestScope.produtos}" var="produto">
                <tr>
                    <td>${produto.id}</td>
                    <td>${produto.nome}</td>
                    <td>${produto.descricao}</td>
                    <td>${produto.preco}</td>
                    <td><a href="deletar?id=${produto.id}">Deletar</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
