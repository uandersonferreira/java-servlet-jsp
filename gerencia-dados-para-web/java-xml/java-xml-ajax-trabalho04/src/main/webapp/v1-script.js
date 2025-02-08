// Função para enviar requisição AJAX
function enviarRequisicao(metodo, url, parametros, callback) {
    const xhr = new XMLHttpRequest();
    xhr.open(metodo, url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            callback(xhr.responseText);
        }
    };
    xhr.send(parametros);
}

// Cadastrar aluno
function cadastrarAluno() {
    const nome = document.getElementById("cadastroNome").value;
    const idade = document.getElementById("cadastroIdade").value;
    const params = "nome=" + encodeURIComponent(nome) + "&idade=" + encodeURIComponent(idade);
    enviarRequisicao("POST", "cadastrarAluno", params, function(resp) {
        document.getElementById("resultadoCadastro").innerText = resp;
    });
}

// Buscar aluno
function buscarAluno() {
    const nome = document.getElementById("buscarNome").value;
    const url = "buscarAluno?nome=" + encodeURIComponent(nome);
    enviarRequisicao("GET", url, null, function(resp) {
        document.getElementById("resultadoBusca").innerText = resp;
    });
}

// Adicionar nota
function adicionarNota() {
    const nome = document.getElementById("notaNome").value;
    const nota = document.getElementById("notaValor").value;
    const params = "nome=" + encodeURIComponent(nome) + "&nota=" + encodeURIComponent(nota);
    enviarRequisicao("POST", "adicionarNota", params, function(resp) {
        document.getElementById("resultadoNota").innerText = resp;
    });
}

// Deletar aluno
function deletarAluno() {
    const nome = document.getElementById("deletarNome").value;
    const params = "nome=" + encodeURIComponent(nome);
    enviarRequisicao("POST", "deletarAluno", params, function(resp) {
        document.getElementById("resultadoDelecao").innerText = resp;
    });
}