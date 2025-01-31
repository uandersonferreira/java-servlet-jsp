// Função para abrir o modal de visualização
function abrirModalView(id, nome, idade) {
    // Preenche os dados do modal de visualização
    document.getElementById("modalId").innerText = id;
    document.getElementById("modalNome").innerText = nome;
    document.getElementById("modalIdade").innerText = idade;

    // Exibe o modal de visualização
    document.getElementById("viewModal").style.display = "block";
}

// Função para fechar o modal de visualização
function fecharModalView() {
    document.getElementById("viewModal").style.display = "none";
}
