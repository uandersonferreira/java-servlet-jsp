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

