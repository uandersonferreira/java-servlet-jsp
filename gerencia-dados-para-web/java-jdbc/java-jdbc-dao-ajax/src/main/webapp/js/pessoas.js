// Carregar pessoas quando a página for carregada
document.addEventListener('DOMContentLoaded', carregarPessoas);

// Configurar o listener do formulário
document.getElementById('formPessoa').addEventListener('submit', function(e) {
    e.preventDefault();
    salvarPessoa();
});

function carregarPessoas() {
    fetch('api/pessoas/listar')
        .then(response => response.json())
        .then(apiResponse => {
            if (apiResponse.sucesso) {
                const tbody = document.getElementById('tabelaPessoas');
                tbody.innerHTML = '';

                apiResponse.dados.forEach(pessoa => {
                    tbody.innerHTML += `
                        <tr>
                            <td>${pessoa.id}</td>
                            <td>${pessoa.nome}</td>
                            <td>${pessoa.idade}</td>
                            <td>
                                <button onclick="deletarPessoa(${pessoa.id})">Excluir</button>
                                <button onclick="buscarPessoa(${pessoa.id})">Detalhes</button>
                            </td>
                        </tr>
                    `;
                });
            } else {
                alert('Erro ao carregar pessoas: ' + apiResponse.mensagem);
            }
        })
        .catch(error => console.error('Erro ao carregar pessoas:', error));
}

function salvarPessoa() {
    const nome = document.getElementById('nome').value;
    const idade = document.getElementById('idade').value;

    fetch('api/pessoas/salvar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            nome: nome,
            idade: parseInt(idade)
        })
    })
        .then(response => response.json())
        .then(apiResponse => {
            if (apiResponse.sucesso) {
                alert('Pessoa cadastrada com sucesso!');
                document.getElementById('formPessoa').reset();
                carregarPessoas();
            } else {
                alert('Erro ao cadastrar pessoa: ' + apiResponse.mensagem);
            }
        })
        .catch(error => console.error('Erro ao cadastrar pessoa:', error));
}

function deletarPessoa(id) {
    if (confirm('Deseja realmente excluir esta pessoa?')) {
        fetch(`api/pessoas/deletar/${id}`, {
            method: 'DELETE'
        })
            .then(response => response.json())
            .then(apiResponse => {
                if (apiResponse.sucesso) {
                    alert('Pessoa excluída com sucesso!');
                    carregarPessoas();
                } else {
                    alert('Erro ao excluir pessoa: ' + apiResponse.mensagem);
                }
            })
            .catch(error => console.error('Erro ao excluir pessoa:', error));
    }
}

function buscarPessoa(id) {
    fetch(`api/pessoas/buscar/${id}`)
        .then(response => response.json())
        .then(apiResponse => {
            if (apiResponse.sucesso) {
                const pessoa = apiResponse.dados;
                alert(`Detalhes da Pessoa:\nID: ${pessoa.id}\nNome: ${pessoa.nome}\nIdade: ${pessoa.idade}`);
            } else {
                alert('Erro ao buscar pessoa: ' + apiResponse.mensagem);
            }
        })
        .catch(error => console.error('Erro ao buscar pessoa:', error));
}