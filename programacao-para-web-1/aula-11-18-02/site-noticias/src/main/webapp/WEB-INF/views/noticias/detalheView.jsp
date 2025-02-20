<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${noticia.titulo}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/alert-msg.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .noticia-container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .noticia-titulo {
            font-size: 32px;
            color: #333;
            margin-bottom: 15px;
        }
        .noticia-info {
            color: #666;
            font-size: 14px;
            margin-bottom: 20px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
        }
        .noticia-lide {
            font-size: 18px;
            color: #444;
            margin-bottom: 20px;
            font-weight: bold;
        }
        .noticia-corpo {
            line-height: 1.6;
            color: #333;
        }
        .acoes {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
            display: flex;
            gap: 10px;
        }
        .botao {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .botao-editar {
            background: #2196F3;
            color: white;
        }
        .botao-deletar {
            background: #f44336;
            color: white;
        }
        .botao-voltar {
            background: #757575;
            color: white;
        }
    </style>
</head>
<body>
    <div class="alerts-container">
        <c:if test="${not empty success}">
            <div class="success-message">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </div>

    <div class="noticia-container">
        <h1 class="noticia-titulo">${noticia.titulo}</h1>
        
        <div class="noticia-info">
            Por ${noticia.reporter.nome} em
            <fmt:formatDate value="${noticia.dataPublicacao}" pattern="dd/MM/yyyy HH:mm"/>
        </div>

        <div class="noticia-lide">${noticia.lide}</div>
        
        <div class="noticia-corpo">${noticia.corpoNoticia}</div>

        <div class="acoes">
            <a href="${pageContext.request.contextPath}/noticias" class="botao botao-voltar">Voltar</a>
            
            <c:if test="${sessionScope.reporterLogado.id == noticia.reporter.id}">
                <a href="${pageContext.request.contextPath}/noticias/${noticia.id}/editar"
	                class="botao botao-editar">Editar</a>
                
                <form action="${pageContext.request.contextPath}/noticias/${noticia.id}/deletar"
	                method="POST"
	                style="display: inline;"
	                onsubmit="return confirm('Tem certeza que deseja deletar esta notícia?');">
                    <button type="submit" class="botao botao-deletar">Deletar</button>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>