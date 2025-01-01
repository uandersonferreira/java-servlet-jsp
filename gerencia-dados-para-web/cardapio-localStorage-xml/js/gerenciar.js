const tbody = document.querySelector('#gerenciar-container tbody');

// Recuperar XML do localStorage
const xmlString = localStorage.getItem('cardapio');
if (!xmlString) {
    alert('Lista vazia');
}

const parser = new DOMParser();
const xmlDoc = parser.parseFromString(xmlString, 'text/xml');

// Mostrar itens com botão de deletar e editar
const pratos = xmlDoc.getElementsByTagName('prato');
Array.from(pratos).forEach((prato, index) => {
    const tr = document.createElement('tr');

    const nomeTd = document.createElement('td');
    nomeTd.textContent = prato.querySelector('nome').textContent;
    tr.appendChild(nomeTd);

    const ingredientesTd = document.createElement('td');
    ingredientesTd.textContent = prato.querySelector('ingredientes').textContent;
    tr.appendChild(ingredientesTd);

    const imagemTd = document.createElement('td');
    const img = document.createElement('img');
    img.src = prato.querySelector('imagem').textContent;
    img.alt = nomeTd.textContent;
    img.style.width = '50px'; // Ajuste o tamanho conforme necessário
    imagemTd.appendChild(img);
    tr.appendChild(imagemTd);

    const precoTd = document.createElement('td');
    precoTd.textContent = prato.querySelector('preco').textContent;
    tr.appendChild(precoTd);

    const acoesTd = document.createElement('td');

    // Botão Deletar
    const deleteBtn = document.createElement('button');
    deleteBtn.textContent = 'Deletar';
    deleteBtn.addEventListener('click', () => {
        xmlDoc.documentElement.removeChild(prato);
        localStorage.setItem('cardapio', new XMLSerializer().serializeToString(xmlDoc));
        window.location.reload();
    });
    acoesTd.appendChild(deleteBtn);

    // Botão Editar
    const editBtn = document.createElement('button');
    editBtn.textContent = 'Editar';
    editBtn.addEventListener('click', () => {
        editPrato(prato, index);
    });
    acoesTd.appendChild(editBtn);

    tr.appendChild(acoesTd);
    tbody.appendChild(tr);
});

function editPrato(prato, index) {
    const nome = prato.querySelector('nome').textContent;
    const novoNome = prompt('Edite o nome do prato:', nome);
    if (novoNome !== null && novoNome.trim() !== '') {
        prato.querySelector('nome').textContent = novoNome.trim();
        localStorage.setItem('cardapio', new XMLSerializer().serializeToString(xmlDoc));
        window.location.reload();
    }
}
