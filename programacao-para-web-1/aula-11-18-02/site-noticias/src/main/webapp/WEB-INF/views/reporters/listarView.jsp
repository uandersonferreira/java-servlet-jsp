<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Lista de Repórteres</title>
	<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/alert-msg.css">
	<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class = "container">
	    <div class="logout">
		    <c:if test = "${not empty reporterLogado}">
		        <a href = "/reporters/logout" class = "btn btn-danger">Logout</a>
	        </c:if>
	    </div>
	    
        <h2>Lista de Repórteres</h2>
	    
	    <div class = "alerts-container">
	        <c:if test = "${not empty success}">
	            <div class = "alert alert-success">${success}</div>
	        </c:if>
	        <c:if test = "${not empty error}">
	            <div class = "alert alert-error">${error}</div>
	        </c:if>
	    </div>
	    
	    <h3>Olá, ${reporterLogado.login}!</h3>
	    
        <table class = "table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Login</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items = "${reporters}" var = "reporter">
                    <tr>
                        <td>${reporter.id}</td>
                        <td>${reporter.nome}</td>
                        <td>${reporter.login}</td>
                        <td>
                            <a href = "/reporters/admin/perfil/${reporter.id}" class = "btn btn-info">Ver Perfil</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <a href = "/noticias/noticias" class = "btn btn-secondary">Voltar</a>
    </div>
	
	<script src = "${pageContext.request.contextPath}/js/alert-msg.js"></script>

</body>
</html>