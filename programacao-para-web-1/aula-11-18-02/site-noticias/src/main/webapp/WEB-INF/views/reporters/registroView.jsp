<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Reporter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/alert-msg.css">
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        
        .page-wrapper {
            width: 100%;
            max-width: 800px;
            margin: 40px auto;
            padding: 0 20px;
            box-sizing: border-box;
        }
        
        .alerts-container {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            z-index: 1000;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        
        h1 {
            text-align: center;
            color: #333;
            margin: 30px 0;
            font-size: 32px;
            padding-top: 16px;
        }
        
        .form-container {
            background-color: #fff;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 750px;
            box-sizing: border-box;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
        }
        
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }
        
        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: #4CAF50;
            outline: none;
        }
        
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            width: 100%;
            transition: background-color 0.3s ease;
        }
        
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        
        @media (max-width: 768px) {
            .page-wrapper {
                margin: 20px auto;
            }
            
            .form-container {
                padding: 20px;
            }
            
            h1 {
                font-size: 24px;
                margin: 20px 0;
            }
        }
    </style>
</head>

<body>
    <div class="alerts-container">
        <c:if test="${not empty error}">
            <div class="error-message">
                 ${error}
            </div>
        </c:if>
    </div>

    <div class="page-wrapper">
        <h1>Cadastro de Reporter</h1>
        
        <div class="form-container">
            <form action="/reporters/novo/registro" method="POST">
                <div class="form-group">
                    <label for="nome">Nome:</label>
                    <input type="text" name="nome" id="nome" required>
                </div>

                <div class="form-group">
                    <label for="login">Login:</label>
                    <input type="text" name="login" id="login" required>
                </div>

                <div class="form-group">
                    <label for="senha">Senha:</label>
                    <input type="password" name="senha" id="senha" required>
                </div>

                <input type="submit" value="Cadastrar">
            </form>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/alert-msg.js"></script>
</body>
</html>