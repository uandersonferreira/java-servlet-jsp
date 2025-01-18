<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Read Pessoas</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link rel="stylesheet" type="text/css" href="css/modal.css">
    <link rel="stylesheet" type="text/css" href="css/modal-show-dados.css">

</head>
<body>
    
    <c:if test="${not empty sessionScope.mensagem}">
        <div class="alert ${sessionScope.tipoMensagem == 'erro' ? 'alert-error' : 'alert-success'}">
                ${sessionScope.mensagem}
        </div>
        <% session.removeAttribute("mensagem"); %>
        <% session.removeAttribute("tipoMensagem"); %>
    </c:if>
    
    <!-- Modal de Exibição dos Dados do Usuário -->
<div id="viewModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 class="modal-title">Detalhes da Pessoa</h2>
        </div>
        <div class="modal-body">
            <p><strong>ID:</strong> <span id="modalId"></span></p>
            <p><strong>Nome:</strong> <span id="modalNome"></span></p>
            <p><strong>Idade:</strong> <span id="modalIdade"></span></p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-cancelar" onclick="fecharModalView()">Fechar</button>
        </div>
    </div>
</div>

    
    <!-- Modal de confirmação -->
    <div id="confirmModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2 class="modal-title">Confirmar Exclusão</h2>
            </div>
            <div class="modal-body">
                <p>Tem certeza que deseja excluir esta pessoa?</p>
                <p>Esta ação não poderá ser desfeita.</p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-cancelar" onclick="fecharModal()">Cancelar</button>
                <button class="btn btn-confirmar" onclick="confirmarExclusao()">Confirmar Exclusão</button>
            </div>
        </div>
    </div>

    <h1>Read Pessoas</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Idade</th>
            <th>Ações</th>
        </tr>
        
        <c:forEach var="pessoa" items="${pessoas}">
            <tr>
                <td>${pessoa.id}</td>
                <td>${pessoa.nome}</td>
                <td>${pessoa.idade}</td>
                <td>
                    <a href="createServlet">Criar</a> |
                    <a href="updateServlet?id=${pessoa.id}">Update</a> |
                    <a href="#" onclick="abrirModal(${pessoa.id}); return false;">Delete</a> |
                    <a href="#" onclick="abrirModalView('${pessoa.id}', '${pessoa.nome}', '${pessoa.idade}'); return false;">View</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <script src="js/modal-delete.js"></script>
    <script src="js/remover-alert.js"></script>
    <script src="js/modal-visualizacao.js"></script>
</body>
</html>