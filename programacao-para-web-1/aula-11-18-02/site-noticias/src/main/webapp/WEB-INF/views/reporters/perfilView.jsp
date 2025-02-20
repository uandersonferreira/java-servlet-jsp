<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>Perfil do Repórter</title>
	<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/alert-msg.css">
	<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class = "container">
        <h2>Perfil do Repórter</h2>
	    
	    <div class = "alerts-container">
	        <c:if test = "${not empty success}">
	            <div class = "alert alert-success">${success}</div>
	        </c:if>
	        <c:if test = "${not empty error}">
	            <div class = "alert alert-error">${error}</div>
	        </c:if>
	    </div>
	    
	    <!-- Verifica se o perfil é do usuário logado -->
	    <c:set var = "isProfileReporterLogado" value = "${reporter.id == reporterLogado.id}"/>
	    
        <form action = "/reporters/admin/perfil" method = "POST">
	        <input type = "hidden" name = "id" value = "${reporter.id}">
            <div class = "form-group">
                <label class = "form-label" for = "nome">Nome:</label>
	            <c:if test = "${isProfileReporterLogado}">
                    <input type = "text" class = "form-control" id = "nome" name = "nome" value = "${reporter.nome}"
	                    required>
		        </c:if>
	            <c:if test = "${not isProfileReporterLogado}">
                    <input type = "text" class = "form-control" id = "nome" name = "nome" value = "${reporter.nome}"
	                    readonly>
		        </c:if>
            </div>
            
            <div class = "form-group">
                <label class = "form-label" for = "login">Login:</label>
                <input type = "text" class = "form-control" id = "login" value = "${reporter.login}" readonly>
            </div>
            
            <div class = "form-group">
                <label class = "form-label" for = "senha">Nova Senha:</label>
				<c:if test = "${isProfileReporterLogado}">
                    <input type = "password" class = "form-control" id = "senha" name = "senha"
	                    placeholder = "Digite para alterar a senha">
				</c:if>
	            <c:if test = "${not isProfileReporterLogado}">
                    <input type = "password" class = "form-control" id = "senha" name = "senha"
	                    placeholder = "Digite para alterar a senha" readonly>
				</c:if>
            </div>
	        
	        <div>
		        <c:if test = "${isProfileReporterLogado}"> <!-- Exibe o botão de exclusão apenas se o perfil é do usuário logado -->
	                <button type = "submit" class = "btn btn-primary">Atualizar Perfil</button>
			    </c:if>
		        
		        <c:if test = "${isProfileReporterLogado}"> <!-- Exibe o botão de exclusão apenas se o perfil é do usuário logado -->
			        <a href = "/reporters/admin/excluir/${reporter.id}" class = "btn btn-danger">Excluir</a>
		        </c:if>
	            <a href = "/reporters/admin/listar" class = "btn btn-secondary">Voltar</a>
	        </div>
        </form>
    </div>
	
	<script src = "${pageContext.request.contextPath}/js/alert-msg.js"></script>

</body>
</html>

