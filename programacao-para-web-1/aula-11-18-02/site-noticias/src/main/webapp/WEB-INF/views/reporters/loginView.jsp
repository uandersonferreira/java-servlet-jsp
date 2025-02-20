<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html lang = "pt-br">
<head>
    <meta charset = "UTF-8">
    <meta name = "viewport" content = "width=device-width, initial-scale=1.0">
    <title>Tela de Login</title>
    <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/alert-msg.css">
    <style>
        body {
            font-family: sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .page-wrapper {
            width: 100%;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            position: relative;
        }

        .alerts-container {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            z-index: 1000;
            padding: 20px;
        }

        .login-wrapper {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 400px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .input-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class = "page-wrapper">
        <!-- Container para alertas  alert-success-->
        <div class = "alerts-container">
            <c:if test = "${not empty success}">
                <div class = "create-message"> ${success} </div>
            </c:if>
            
            <c:if test = "${not empty error}">
                <div class = "error-message"> ${error} </div>
            </c:if>
        </div>
	    
	    <!-- Container do formulário de login -->
        <div class = "login-wrapper">
            <div class = "container">
                <h2>Login</h2>
                <form action = "/reporters/login" method = "post">
                    <div class = "input-group">
                        <label for = "username">Login</label>
                        <input type = "text" id = "username" name = "login" required>
                    </div>
                    <div class = "input-group">
                        <label for = "password">Senha</label>
                        <input type = "password" id = "password" name = "senha" required>
                    </div>
                    <button type = "submit">Entrar</button>
                </form>
            </div>
        </div>
    </div>
    <script src = "${pageContext.request.contextPath}/js/alert-msg.js"></script>
</body>
</html>