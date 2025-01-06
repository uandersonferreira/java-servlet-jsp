
DENTRO DE UM SERVIDOR TEMOS:

CONTAINER (TOMCAT/GLASSFLISH)

POOL DE THREAD
 - THREAD 1
 - THREAD 2
 - THREAD 3

SERVLETS
 - COMPRARSERVLET
 - LISTARSERVLET
 - CADASTRARSERVLET

FUNCIONALIDADE BÁSICA:

1° - THREAD -> [ASSOCIADA] -> SERVLET

2° - CONTAINER -> [CRIAR OS OBJETOS] -> RESPONSE E REQUEST

3° - CONTAINER -> [ASSOCIAR ESSES OBJETOS] - A THREAD -> [ASSOCIADA] -> SERVLET

4° - CONTAINER -> DEVOLVE O OBJETO RESPONSE -> PARA O CLIENTE (NAVEGADOR)

5° - ZERA TUDO

O OBJETO SERVLET:

- É UNICO
- E THREAD SÃO USADAS PARA ATENDER REQUISIÇÕES SIMULTÂNEAS
  - O MESMO OBJETO SERVLET É COMPARTILHADO ENTRE VÁRIAS THREADS. CUIDADO COM POBLEMA DE CONCORRÊNCIA.
- QUANDO UMA REQUISIÇÃO AO SERVLET É FEITA, ELE JÁ FOI PREVIAMENTE CARREGADO.



document.getElementById('add-tecnologia').addEventListener('click', () => {
    const nomeTecnologia = document.getElementById('nome-tecnologia');
    const imagemTecnologia = document.getElementById('imagem-tecnologia');
    
    if (!nomeTecnologia.value || !imagemTecnologia.files[0]) {
        alert("Por favor, preencha o nome e escolha uma imagem para a tecnologia.");
        return;
    }

    const lista = document.getElementById('lista-tecnologias');
    const listItem = document.createElement('li');
    listItem.className = 'tecnologia-item';

    const img = document.createElement('img');
    img.src = URL.createObjectURL(imagemTecnologia.files[0]);
    img.alt = `Ícone da tecnologia ${nomeTecnologia.value}`;

    const span = document.createElement('span');
    span.textContent = nomeTecnologia.value;
    
    const removeBtn = document.createElement('button');
    removeBtn.textContent = "Remover";
    removeBtn.className = 'btn btn-remove';
    removeBtn.setAttribute('aria-label', `Remover ${nomeTecnologia.value}`);
    removeBtn.addEventListener('click', () => {
        lista.removeChild(listItem);
    });

    listItem.appendChild(img);
    listItem.appendChild(span);
    listItem.appendChild(removeBtn);
    lista.appendChild(listItem);

    // Limpar os campos após adicionar
    nomeTecnologia.value = '';
    imagemTecnologia.value = '';
});

// Mostrar nome do arquivo selecionado
document.querySelectorAll('input[type="file"]').forEach(input => {
    input.addEventListener('change', function() {
        const label = this.previousElementSibling;
        label.textContent = this.files[0] ? this.files[0].name : 'Escolher arquivo';
    });
});
