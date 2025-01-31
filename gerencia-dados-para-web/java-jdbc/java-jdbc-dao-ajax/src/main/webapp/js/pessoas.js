// Função para carregar todas as pessoas ao carregar a página
document.addEventListener('DOMContentLoaded', carregarPessoas);

// Configurar o listener do formulário
document.getElementById('formPessoa').addEventListener('submit', function(e) {
    e.preventDefault();
    cadastrarPessoa();
});

function carregarPessoas() {
    fetch('pessoa/listar')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('tabelaPessoas');
            tbody.innerHTML = '';

            data.forEach(pessoa => {
                tbody.innerHTML += `
                    <tr>
                        <td>${pessoa.id}</td>
                        <td>${pessoa.nome}</td>
                        <td>${pessoa.idade}</td>
                    </tr>
                `;
            });
        })
        .catch(error => console.error('Erro ao carregar pessoas:', error));
}

function cadastrarPessoa() {
    const nome = document.getElementById('nome').value;
    const idade = document.getElementById('idade').value;

    fetch('pessoa/cadastrar', {
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
        .then(data => {
            alert('Pessoa cadastrada com sucesso!');
            document.getElementById('formPessoa').reset();
            carregarPessoas();
        })
        .catch(error => console.error('Erro ao cadastrar pessoa:', error));
}