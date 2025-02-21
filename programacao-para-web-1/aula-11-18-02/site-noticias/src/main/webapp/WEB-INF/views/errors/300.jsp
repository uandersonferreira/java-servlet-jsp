<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirecionamento</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>300 - Redirecionamento</h1>
        <p>O recurso que você está tentando acessar foi movido ou redirecionado.</p>
        <p>Você será redirecionado automaticamente em alguns instantes.</p>
        <a href="${pageContext.request.contextPath}/" class="home-link">Voltar para a página inicial</a>
    </div>
</body>
</html>