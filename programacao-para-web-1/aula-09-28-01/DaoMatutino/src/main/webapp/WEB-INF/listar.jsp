<%@ page contentType = "text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Produtos</title>
    <link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        margin: 2rem;
        background-color: #f5f5f5;
    }

    .container {
        max-width: 1200px;
        margin: 0 auto;
        background: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 1rem 0;
    }

    th, td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    th {
        background-color: #f8f9fa;
    }

    tr:hover {
        background-color: #f9f9f9;
    }

    .actions a {
        text-decoration: none;
        color: #333;
        margin: 0 5px;
        padding: 6px 10px;
        border-radius: 4px;
        transition: all 0.3s;
    }

    .edit-btn {
        color: #2196F3;
    }

    .edit-btn:hover {
        background-color: #e3f2fd;
    }

    .delete-btn {
        color: #f44336;
    }

    .delete-btn:hover {
        background-color: #ffebee;
    }

    .add-btn {
        display: inline-flex;
        align-items: center;
        gap: 8px;
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white !important;
        border-radius: 4px;
        text-decoration: none;
    }

    .add-btn:hover {
        background-color: #45a049;
    }
    
    .alert {
        position: fixed;
        top: 20px;
        left: 65%;
        transform: translateX(-65%);
        width: 50%;
        padding: 15px 20px;
        border-radius: 6px;
        background-color: #4CAF50;
        color: white;
        font-size: 16px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        display: flex;
        align-items: center;
        gap: 10px;
        animation: fadeIn 0.5s ease-in-out;
        text-align: center;
        justify-content: center;
    }
    
    .alert i {
        font-size: 18px;
    }
    
    @keyframes fadeIn {
        from { opacity: 0; transform: translate(-50%, -10px); }
        to { opacity: 1; transform: translate(-50%, 0); }
    }
    
    @keyframes fadeOut {
        from { opacity: 1; transform: translate(-50%, 0); }
        to { opacity: 0; transform: translate(-50%, -10px); }
    }
    
</style>
</head>
<body>
    <c:if test = "${not empty mensagem}">
        <div class = "alert alert-success">
		        ${mensagem}
        </div>
    </c:if>
    
    <h1>Produtos Cadastrados</h1>
    <table border = "1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Preço</th>
            <th>Ações</th>
        </tr>
        <c:forEach items = "${produtos}" var = "produto">
            <tr>
                <td>${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.descricao}</td>
                <td>${produto.preco}</td>
                <td class = "actions">
                    <a href = "editar?id=${produto.id}" class = "edit-btn" title = "Editar">
                        <i class = "fas fa-edit"></i>
                    </a>
                    <a href = "#" onclick = "confirmDelete(${produto.id})" class = "delete-btn" title = "Excluir">
                        <i class = "fas fa-trash-alt"></i>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <a href = "inserir" class = "add-btn">
        <i class = "fas fa-plus"></i>Novo Produto
    </a>
    
    
    <script>
            function confirmDelete(id) {
                if (confirm('Tem certeza que deseja excluir este produto?')) {
                    window.location.href = 'deletar?id=' + id;
                }
            }

            document.addEventListener("DOMContentLoaded", function () {
                const alertBox = document.querySelector(".alert");
                if (alertBox) {
                    setTimeout(() => {
                        alertBox.style.animation = "fadeOut 0.5s ease-in-out";
                        setTimeout(() => alertBox.remove(), 500);
                    }, 5000);
                }
            });
            
    </script>
    
    
</body>
</html>