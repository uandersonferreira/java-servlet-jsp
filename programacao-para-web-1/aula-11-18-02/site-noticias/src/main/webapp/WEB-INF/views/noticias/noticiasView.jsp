<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Minhas Notícias</title>
    <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/alert-msg.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar Styles */
        .sidebar {
            width: 300px;
            background: white;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
            position: fixed;
            height: 100vh;
            overflow-y: auto;
        }

        .reporter-info {
            margin-bottom: 30px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 8px;
        }

        .reporter-info h2 {
            color: #333;
            margin-bottom: 15px;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 10px;
        }

        .reporter-info p {
            margin-bottom: 10px;
            color: #666;
        }

        .action-buttons {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-bottom: 20px;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            text-align: center;
            transition: background-color 0.3s;
            color: white;
        }

        .btn-primary {
            background: #4CAF50;
        }

        .btn-primary:hover {
            background: #45a049;
        }

        .btn-back {
            background: #2196F3;
        }

        .btn-back:hover {
            background: #1976D2;
        }

        /* Main Content Styles */
        .main-content {
            margin-left: 300px;
            padding: 20px;
            flex-grow: 1;
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
            transition: transform 0.2s;
        }

        .noticia-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 15px;
        }

        .noticia-titulo-container {
            flex-grow: 1;
        }

        .noticia-acoes {
            display: flex;
            gap: 10px;
            margin-left: 20px;
        }

        .btn-edit {
            background: #FFA000;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .btn-edit:hover {
            background: #FF8F00;
        }

        .btn-delete {
            background: #F44336;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .btn-delete:hover {
            background: #D32F2F;
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
	
	<!-- Sidebar -->
    <aside class = "sidebar">
        <div class = "reporter-info">
            <h2>Meu Perfil</h2>
            <p><strong>Nome:</strong> ${sessionScope.reporterLogado.nome}</p>
            <p><strong>Login:</strong> ${sessionScope.reporterLogado.login}</p>
        </div>

        <div class = "action-buttons">
            <a href = "${pageContext.request.contextPath}/noticias/admin/nova" class = "btn btn-primary">Nova Notícia</a>
            <a href = "${pageContext.request.contextPath}/" class = "btn btn-back">Voltar para Notícias</a>
        </div>
    </aside>
	
	<!-- Main Content -->
    <main class = "main-content">
        <div class = "noticias-container">
            <c:forEach items = "${noticias}" var = "noticia">
                <div class = "noticia-card">
                    <div class = "noticia-header">
                        <div class = "noticia-titulo-container">
                            <h2 class = "noticia-titulo">
                                <a href = "${pageContext.request.contextPath}/noticias/${noticia.id}">${noticia.titulo}</a>
                            </h2>
                            <div class = "noticia-info">
                                Publicado em
                                <fmt:parseDate value = "${noticia.dataPublicacao}" pattern = "yyyy-MM-dd'T'HH:mm"
	                                var = "parsedDate" type = "both"/>
                                <fmt:formatDate value = "${parsedDate}" pattern = "dd/MM/yyyy HH:mm"/>
                            </div>
                        </div>
                        <div class = "noticia-acoes">
                            <a href = "${pageContext.request.contextPath}/noticias/admin/editar/${noticia.id}"
	                            class = "btn-edit">Editar</a>
                            <a href = "#" onclick = "if(confirm('Tem certeza que deseja excluir esta notícia?'))
                              { window.location.href='${pageContext.request.contextPath}/noticias/admin/deletar/${noticia.id}'; return false; }"
	                            class = "btn-delete">Deletar</a>
                        </div>
                    </div>
                    <p>${noticia.lide}</p>
                </div>
            </c:forEach>
        </div>
    </main>
</body>
</html>