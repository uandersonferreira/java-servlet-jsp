<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Pessoa</title>
    <link rel="stylesheet" type="text/css" href="css/update.css">
    <style>
        /* Estilo para o erro */
        .alert-error {
            color: white;
            background-color: red;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            text-align: center;
        }
        
        .error-message {
            color: red;
            font-size: 12px;
            display: block;
            margin-top: 5px;
        }
        
        /* Adiciona uma transição suave ao desaparecimento das mensagens de erro */
        .alert {
            transition: opacity 1s ease-out;
        }
    </style>
</head>
<body>
    <h1>Update Pessoa</h1>
    
    <!-- Exibe a mensagem de erro se houver -->
    <c:if test="${not empty mensagemErro}">
        <div id="errorAlert" class="alert alert-error">
                ${mensagemErro}
        </div>
    </c:if>

    <form action="updateServlet" method="post">
        <input type="hidden" name="id" value="${pessoa.id}">
        
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="${pessoa.nome}">
        <c:if test="${campoErro == 'nome'}">
            <span class="error-message">Nome é obrigatório.</span>
        </c:if>
        <br><br>
        
        <label for="idade">Idade:</label>
        <input type="number" id="idade" name="idade" value="${pessoa.idade}" min="0" step="1">
        <c:if test="${campoErro == 'idade'}">
            <span class="error-message">Idade deve ser maior que 0.</span>
        </c:if>
        <br><br>
        
        <input type="submit" value="Update">
    </form>

    <script src="js/remover-alert.js"></script>
</body>
</html>
