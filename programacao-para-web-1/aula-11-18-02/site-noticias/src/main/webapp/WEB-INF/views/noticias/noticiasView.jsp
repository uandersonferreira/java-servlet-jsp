<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Notícias - Lista</title>
    <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/alert-msg.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }

        .noticias-container {
            max-width: 800px;
            margin: 0 auto;
        }

        .noticia-card {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .noticia-titulo {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }

        .noticia-info {
            color: #666;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .search-box {
            margin-bottom: 30px;
            text-align: center;
        }

        .search-box input {
            padding: 10px;
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .botao-nova {
            background: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            display: inline-block;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class = "alerts-container">
        <c:if test = "${not empty success}">
            <div class = "success-message">${success}</div>
        </c:if>
        <c:if test = "${not empty error}">
            <div class = "error-message">${error}</div>
        </c:if>
    </div>

    <div class = "noticias-container">
        <c:if test = "${sessionScope.reporterLogado != null}">
            <a href = "${pageContext.request.contextPath}/noticias/nova" class = "botao-nova">Nova Notícia</a>
        </c:if>

        <div class = "search-box">
            <form action = "${pageContext.request.contextPath}/noticias/buscar" method = "GET">
                <input type = "text" name = "titulo" placeholder = "Buscar notícias..." value = "${termoBusca}">
            </form>
        </div>

        <c:forEach items = "${noticias}" var = "noticia">
            <div class = "noticia-card">
                <h2 class = "noticia-titulo">
                    <a href = "${pageContext.request.contextPath}/noticias/${noticia.id}">${noticia.titulo}</a>
                </h2>
                <div class = "noticia-info">
                    Por ${noticia.reporter.nome} em
                    <fmt:formatDate value = "${noticia.dataPublicacao}" pattern = "dd/MM/yyyy HH:mm"/>
                </div>
                <p>${noticia.lide}</p>
            </div>
        </c:forEach>
    </div>
</body>
</html>