document.addEventListener('DOMContentLoaded', function () {
    carregarPessoas();

    const form = document.getElementById('formPessoa');
    form.addEventListener('submit', salvarPessoa);
    document.getElementById('btnCancelar').addEventListener('click', cancelarEdicao);
});

function carregarPessoas() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'pessoas-xml', true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const xmlDoc = xhr.responseXML;
            const pessoas = xmlDoc.getElementsByTagName('pessoa');
            const tbody = document.getElementById('tabelaPessoas');
            tbody.innerHTML = '';

            for (let i = 0; i < pessoas.length; i++) {
                const pessoa = pessoas[i];
                const id = pessoa.getElementsByTagName('id')[0].textContent;
                const nome = pessoa.getElementsByTagName('nome')[0].textContent;
                const idade = pessoa.getElementsByTagName('idade')[0].textContent;

                tbody.innerHTML += `
          <tr>
            <td>${id}</td>
            <td>${nome}</td>
            <td>${idade}</td>
            <td>
              <button onclick="editarPessoa(${id}, '${nome}', ${idade})">Editar</button>
              <button onclick="deletarPessoa(${id})">Excluir</button>
            </td>
          </tr>
        `;
            }
        }
    };

    xhr.send();
}

function salvarPessoa(e) {
    e.preventDefault();
    const id = document.getElementById('pessoaId').value;
    const nome = document.getElementById('nome').value;
    const idade = document.getElementById('idade').value;

    const xhr = new XMLHttpRequest();
    // Se existir id, usamos o endpoint de atualização; caso contrário, cria uma nova pessoa
    const endpoint = id ? 'update-xml' : 'create-xml';
    xhr.open('POST', endpoint, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('formPessoa').reset();
            document.getElementById('pessoaId').value = '';
            document.getElementById('btnCancelar').classList.add('hidden');
            carregarPessoas();
        }
    };

    let parametros = `nome=${encodeURIComponent(nome)}&idade=${encodeURIComponent(idade)}`;
    if (id) {
        parametros += `&id=${encodeURIComponent(id)}`;
    }
    xhr.send(parametros);
}

function deletarPessoa(id) {
    if (confirm('Confirma a exclusão?')) {
        const xhr = new XMLHttpRequest();
        // Para requisição GET, os parâmetros devem ser passados na URL
        xhr.open('GET', 'delete-xml?id=' + encodeURIComponent(id), true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                carregarPessoas();
            }
        };
        xhr.send();
    }
}

function editarPessoa(id, nome, idade) {
    document.getElementById('pessoaId').value = id;
    document.getElementById('nome').value = nome;
    document.getElementById('idade').value = idade;
    document.getElementById('btnCancelar').classList.remove('hidden');
}

function cancelarEdicao() {
    document.getElementById('formPessoa').reset();
    document.getElementById('pessoaId').value = '';
    document.getElementById('btnCancelar').classList.add('hidden');
}

