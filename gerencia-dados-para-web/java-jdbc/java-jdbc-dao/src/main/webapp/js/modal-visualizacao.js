let pessoaIdParaDeletar;

function abrirModal(id) {
    pessoaIdParaDeletar = id;
    document.getElementById('confirmModal').style.display = 'block';
}

function fecharModal() {
    document.getElementById('confirmModal').style.display = 'none';
}

function confirmarExclusao() {
    if (pessoaIdParaDeletar) {
        window.location.href = 'DeleteServlet?id=' + pessoaIdParaDeletar;
    }
    fecharModal();
}

// Fechar modal ao clicar fora dele
window.onclick = function(event) {
    let modal = document.getElementById('confirmModal');
    if (event.target == modal) {
        fecharModal();
    }
}

// Remove as mensagens de alerta após 5 segundos
document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
        var alertElements = document.getElementsByClassName('alert');
        if (alertElements.length > 0) {
            alertElements[0].style.display = 'none';
        }
    }, 5000);
});