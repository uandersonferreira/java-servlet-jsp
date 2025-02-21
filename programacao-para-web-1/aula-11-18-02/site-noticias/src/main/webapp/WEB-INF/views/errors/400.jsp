<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erro do Cliente</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>400 - Erro do Cliente</h1>
        <p>Ocorreu um erro na sua solicitação. Verifique os dados enviados e tente novamente.</p>
        <p>Se o problema persistir, entre em contato com o suporte.</p>
        <a href="${pageContext.request.contextPath}/" class="home-link">Voltar para a página inicial</a>
    </div>
</body>
</html>