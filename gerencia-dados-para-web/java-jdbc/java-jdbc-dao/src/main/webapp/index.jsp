<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Read Pessoas</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1>Read Pessoas</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Idade</th>
        <th>Ações</th>
    </tr>
    <%-- Supondo que a lista de pessoas esteja disponível como um atributo de request --%>
    <c:forEach var="pessoa" items="${pessoas}">
        <tr>
            <td>${pessoa.id}</td>
            <td>${pessoa.nome}</td>
            <td>${pessoa.idade}</td>
            <td><a href="update.jsp?id=${pessoa.id}">Update</a> |
                <a href="delete.jsp?id=${pessoa.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
