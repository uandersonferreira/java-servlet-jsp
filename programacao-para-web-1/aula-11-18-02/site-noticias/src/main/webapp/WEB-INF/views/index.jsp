<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html lang = "pt-BR">
<head>
    <meta charset = "UTF-8">
    <meta name = "viewport" content = "width=device-width, initial-scale=1.0">
    <title>Portal de Notícias</title>
    <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/alert-msg.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f4f4f4;
        }

        .container {
            max-width: 99vw;
            margin: 0 auto;
            padding: 0 20px;
        }

        /* Header Styles */
        header {
            background-color: #2c3e50;
            color: white;
            padding: 1rem 0;
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 1.5rem;
            font-weight: bold;
        }

        /* Login Form Styles */
        .login-form {
            display: flex;
            gap: 1rem;
            align-items: center;
        }

        .login-form input {
            padding: 0.5rem;
            border: none;
            border-radius: 4px;
        }

        .btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            background-color: #3498db;
            color: white;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #2980b9;
        }

        /* Content Layout */
        .content-wrapper {
            display: flex;
            margin-top: 20px;
        }

        /* Sidebar Menu */
        .sidebar {
            width: 300px;
            background-color: white;
            padding: 20px;
            min-height: calc(100vh - 80px);
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
        }

        .news-menu {
            list-style: none;
        }

        .news-menu-item {
            padding: 10px;
            border-bottom: 1px solid #eee;
            cursor: pointer;
            color: #2c3e50;
        }

        .news-menu-item:hover {
            background-color: #f8f9fa;
            color: #3498db;
        }

        /* Main Content Area */
        .main-content {
            flex-grow: 1;
            padding: 20px;
            background-color: white;
            margin-left: 20px;
            min-height: calc(100vh - 80px);
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }

        .news-content {
            max-width: 800px;
            margin: 0 auto;
        }

        .news-title {
            color: #2c3e50;
            margin-bottom: 20px;
        }

        .news-date {
            color: #666;
            font-size: 0.9rem;
            margin-bottom: 20px;
        }

        .news-body {
            line-height: 1.8;
        }
    </style>
</head>
<body>
    
    <div class = "alerts-container">
	        <c:if test = "${not empty success}">
	            <div class = "alert alert-success">${success}</div>
	        </c:if>
	        <c:if test = "${not empty error}">
	            <div class = "alert alert-error">${error}</div>
	        </c:if>
    </div>
    
    <header>
        <div class = "container header-content">
            <div class = "logo">Portal de Notícias</div>
            <div action = "reporters/login" method = "post" class = "login-form">
                <c:if test = "${reporterLogado == null}">
                    <a href = "/reporters/login" class = "btn">Login</a>
                </c:if>
                <a href = "/reporters/registro" class = "btn">Cadastrar</a>
                
                 <c:forEach items = "${noticias}" var = "noticia">
                       <c:if test = "${noticia.reporter.id == reporterLogado.id && reporterLogado != null}">
                            <a href = "/noticias/admin/reporter/${noticia.reporter.id}"
	                            class = "btn btn-info">Minhas Noticias</a>
                        </c:if>
                 </c:forEach>
                
                <c:if test = "${not empty reporterLogado}">
		            <a href = "/reporters/admin/listar" class = "btn btn-info">Reporters</a>
                    <a href = "/reporters/logout" class = "btn btn-danger">Logout</a>
	             </c:if>
            </div>
        </div>
    </header>

    <div class = "container">
        <div class = "content-wrapper">
            <!-- Sidebar Menu -->
            <aside class = "sidebar">
                <ul class = "news-menu">
                    <c:forEach items = "${noticias}" var = "noticia">
                        <li class = "news-menu-item" onclick = "location.href='/noticias/${noticia.id}'">
		                        ${noticia.titulo}
                        </li>
                    </c:forEach>
                </ul>
            </aside>
	        
	        <!-- Main Content Area -->
            <main class = "main-content">
                <article class = "news-content">
                    <c:if test = "${noticia != null}">
                        <h1 class = "news-title">${noticia.titulo}</h1>
                        <div class = "news-date">
                             <c:if test = "${noticia.reporter != null}">
                                 <strong>Autor: </strong>${noticia.reporter.nome}
                             </c:if>
                             |  <strong>Data Publicação:</strong>
                                <fmt:parseDate value = "${noticia.dataPublicacao}" pattern = "yyyy-MM-dd'T'HH:mm"
	                                var = "parsedDate" type = "both"/>
                                <fmt:formatDate value = "${parsedDate}" pattern = "dd/MM/yyyy HH:mm"/>
                        </div>
                       
                        <div class = "news-body">
                            <p>${noticia.lide}</p>
                            <p>${noticia.corpoNoticia}</p>
                        </div>

                    </c:if>
                    <c:if test = "${noticia == null}">
                        <h2>Selecione uma notícia para ler</h2>
                    </c:if>
                </article>
            </main>
        </div>
    </div>

	<script src = "${pageContext.request.contextPath}/js/alert-msg.js"></script>

</body>
</html>


