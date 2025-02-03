<%@ page contentType = "text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = "pt-BR">
<head>
    <meta charset = "UTF-8">
    <meta name = "viewport" content = "width=device-width, initial-scale=1.0">
    <title><c:out value = "${empty produto ? 'Novo' : 'Editar'}"/> Produto</title>
    <link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            border: 1px solid black;
        }

        .form-container {
            width: 50%;
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        .form-group {
            margin-bottom: 1rem;
            text-align: left;
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="number"],
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
        }

        button {
            background-color: #2196F3;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            transition: background 0.3s;
        }

        button:hover {
            background-color: #1976D2;
        }

        .cancel-link {
            margin-top: 8px;
            background-color: #6e6e6e;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            transition: background 0.3s;
        }

        .cancel-link:hover {
            color: #fff;
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
<%--
Comportamento da EL (Expression Language) "Salvar novo produto":
    - A EL (${}) é projetada para ser null-safe
    - Se a variável produto não existir, empty produto retorna true
    - Não gera NullPointerException
    
Se produto for null (caso de novo cadastro):
    - ${produto.nome} → retorna null
    - <c:out> converte null para string vazia ("")
    - Evita exibição de "null" no campo
--%>
    <div class = "form-container">
        <h2><c:out value = "${empty produto ? 'Cadastrar' : 'Editar'}"/> Produto</h2>
        <form action = "${empty produto ? 'inserir' : 'editar'}" method = "post" id = "produto-form">
            <c:if test = "${not empty produto}">
                <input type = "hidden" name = "id" value = "${produto.id}">
            </c:if>
            
            <div class = "form-group">
                <label for = "nome">Nome:</label>
                <input type = "text" id = "nome" name = "nome" value = "${produto.nome}" required>
            </div>
            
            <div class = "form-group">
                <label for = "descricao">Descrição:</label>
                <textarea id = "descricao" name = "descricao" required>${produto.descricao}</textarea>
            </div>
            
            <div class = "form-group">
                <label for = "preco">Preço:</label>
                <input type = "number" id = "preco" step = "0.01" min="0.0" name = "preco" value = "${produto.preco}" required>
            </div>
            
            <button type = "submit">
                <i class = "fas fa-save"></i> Salvar
            </button>
        </form>
        <a href = "produtos" class = "button cancel-link">
            <i class = "fas fa-times"></i> Cancelar
        </a>
    </div>
    
    <script>
        document.getElementById('produto-form').addEventListener('submit', function (e) {
            const inputs = document.querySelectorAll('input[required], textarea[required]');
            let isValid = true;

            inputs.forEach(input => {
                if (!input.value.trim()) {
                    isValid = false;
                    input.style.borderColor = '#f44336';
                } else {
                    input.style.borderColor = '#ccc';
                }
            });

            if (!isValid) {
                e.preventDefault();
                alert('Por favor, preencha todos os campos obrigatórios!');
            }
        });
    </script>
</body>
</html>
