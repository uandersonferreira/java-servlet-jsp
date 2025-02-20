<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${noticia != null ? 'Editar' : 'Nova'} Notícia</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/alert-msg.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .form-container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-grupo {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: bold;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        textarea {
            min-height: 200px;
            resize: vertical;
        }
        .botoes {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .botao {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .botao-salvar {
            background: #4CAF50;
            color: white;
        }
        .botao-cancelar {
            background: #757575;
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="alerts-container">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </div>

    <div class="form-container">
        <h1>${noticia != null ? 'Editar' : 'Nova'} Notícia</h1>
        
        <form action="${pageContext.request.contextPath}/noticias/${noticia != null ? noticia.id.concat('/editar') : 'nova'}"
	        method="POST">
            
            <div class="form-grupo">
                <label for="titulo">Título</label>
                <input type="text" id="titulo" name="titulo" required
	                value="${noticia != null ? noticia.titulo : ''}">
            </div>

            <div class="form-grupo">
                <label for="lide">Lide</label>
                <textarea id="lide" name="lide" required>${noticia != null ? noticia.lide : ''}</textarea>
            </div>

            <div class="form-grupo">
                <label for="corpo">Corpo da Notícia</label>
                <textarea id="corpo" name="corpo" required>${noticia != null ? noticia.corpoNoticia : ''}</textarea>
            </div>

            <div class="botoes">
                <button type="submit" class="botao botao-salvar">Salvar</button>
                <a href="${pageContext.request.contextPath}/noticias" class="botao botao-cancelar">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>