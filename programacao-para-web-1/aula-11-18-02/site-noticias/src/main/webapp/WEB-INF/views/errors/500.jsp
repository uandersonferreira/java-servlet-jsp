<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erro do Servidor</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>500 - Erro do Servidor</h1>
        <p>Ocorreu um erro interno no servidor. Nossa equipe já foi notificada e está trabalhando para resolver o problema.</p>
        <p>Por favor, tente novamente mais tarde.</p>
        <a href="${pageContext.request.contextPath}/" class="home-link">Voltar para a página inicial</a>
    </div>
</body>
</html>