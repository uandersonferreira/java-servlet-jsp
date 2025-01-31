<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Cadastrar nova Pessoa</title>
    <link rel = "stylesheet" type = "text/css" href = "css/update.css">
</head>
<body>
        <!-- Exibe a mensagem de erro se houver -->
    <c:if test = "${not empty mensagemErro}">
        <div id = "errorAlert" class = "alert alert-error">
		        ${mensagemErro}
        </div>
    </c:if>

<h1>Cadastrar Pessoa</h1>
<form action = "createServlet" method = "post">
    <label for = "nome">Nome:</label>
        <input type = "text" id = "nome" name = "nome" value = "${pessoa.nome}">
        <c:if test = "${campoErro == 'nome'}">
            <span class = "error-message">Nome é obrigatório.</span>
        </c:if>
        <br><br>
    
        <label for = "idade">Idade:</label>
        <input type = "number" id = "idade" name = "idade" value = "${pessoa.idade}" min = "0" step = "1">
        <c:if test = "${campoErro == 'idade'}">
            <span class = "error-message">Idade deve ser maior que 0.</span>
        </c:if>
        <br><br>
    
    <input type = "submit" value = "Create">
</form>
    
        <script src = "js/remover-alert.js"></script>

</body>
</html>
