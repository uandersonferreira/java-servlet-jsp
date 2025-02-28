function criarAjaxGet(url, dados, funcao) {
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = funcao;
    ajax.open('GET', url + "?" + dados, true);
    ajax.send();
}

function criarAjaxPost(url, dados, funcao) {
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = funcao;
    ajax.open('POST', url, true);
    ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    ajax.send(dados);
}

// Função para enviar dados
document.getElementById("btnEnviar").addEventListener('click', () => {
    const nome = document.getElementById("name").value;
    const idade = document.getElementById("idade").value;

    const dados = `name=${encodeURIComponent(nome)}&idade=${encodeURIComponent(idade)}`;

    criarAjaxPost('/hello-servlet', dados, function() {
        if (this.readyState === 4 && this.status === 200) {
            alert('Dados enviados com sucesso!');
        }
    });
});

// Função para receber dados
document.getElementById("btnReceber").addEventListener('click', () => {
    criarAjaxGet('/hello-servlet', '', function() {
        if (this.readyState === 4 && this.status === 200) {
            const dados = JSON.parse(this.responseText);
            const mostrarDados = document.getElementById("mostrarDados");

            // Limpa o conteúdo anterior
            mostrarDados.innerHTML = '';

            // Exibe os dados recebidos
            dados.forEach(item => {
                const p = document.createElement('p');
                p.textContent = `Nome: ${item.name}, Idade: ${item.idade}`;
                mostrarDados.appendChild(p);
            });
        }
    });
});