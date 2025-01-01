const form = document.getElementById('form-cadastro');
const inputIngrediente = document.getElementById('ingrediente-input');
const btnAddIngrediente = document.getElementById('add-ingrediente');
const listaIngredientes = document.getElementById('ingredientes-list');

// Adicionar ingrediente
btnAddIngrediente.addEventListener('click', () => {
    const valor = inputIngrediente.value.trim();
    if (valor) {
        const li = document.createElement('li');
        li.textContent = valor;

        const btnRemover = document.createElement('button');
        btnRemover.innerHTML = '&times;'; // Adiciona um "X" como conteúdo do botão
        btnRemover.classList.add('remove-ingrediente');
        btnRemover.addEventListener('click', () => li.remove());

        li.appendChild(btnRemover);
        listaIngredientes.appendChild(li); //ul
        inputIngrediente.value = '';
    }
});

/*
        // Adicionar campo dinâmico para ingredientes como html comentado
        btnAddIngrediente.addEventListener('click', () => {
            const input = document.createElement('input');
            input.type = 'text';
            input.name = 'ingrediente';
            input.placeholder = 'Ingrediente';
            ingredientesContainer.appendChild(input);
        });

*/


// Salvar prato no localStorage como XML
form.addEventListener('submit', (event) => {
    event.preventDefault();

    // Criando um objeto JavaScript usando o próprio form
    const prato = {
        nome: form.nome.value,
        ingredientes: Array.from(listaIngredientes.children).map(li => li.firstChild.textContent),
        imagem: form.imagem.value,
        preco: form.preco.value,
    };

    // Criar estrutura XML
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString('<cardapio></cardapio>', 'text/xml');
    const pratoElement = xmlDoc.createElement('prato');

    pratoElement.appendChild(createElementWithText(xmlDoc, 'nome', prato.nome));
    const ingredientesElement = xmlDoc.createElement('ingredientes');
    prato.ingredientes.forEach(ingrediente => {
        ingredientesElement.appendChild(createElementWithText(xmlDoc, 'ingrediente', ingrediente));
    });
    pratoElement.appendChild(ingredientesElement);
    pratoElement.appendChild(createElementWithText(xmlDoc, 'imagem', prato.imagem));
    pratoElement.appendChild(createElementWithText(xmlDoc, 'preco', prato.preco));
    xmlDoc.documentElement.appendChild(pratoElement);

    // Salvar no localStorage
    const serializedCardapio = localStorage.getItem('cardapio');
    let xmlDocFinal;

    if (serializedCardapio) {
        const existingDoc = parser.parseFromString(serializedCardapio, 'text/xml');
        existingDoc.documentElement.appendChild(pratoElement);
        xmlDocFinal = existingDoc;
    } else {
        xmlDocFinal = xmlDoc;
    }

    localStorage.setItem('cardapio', new XMLSerializer().serializeToString(xmlDocFinal));

    alert('Prato cadastrado com sucesso!');
    form.reset();
    listaIngredientes.innerHTML = ''; // Limpa a lista de ingredientes
});

// Função para criar elemento com texto: xmlDoc
function createElementWithText(doc, tag, text) {
    const element = doc.createElement(tag);
    element.textContent = text;
    return element;
}


/*
Caso não usasse o Array.from:

// Criando um objeto JavaScript usando o próprio formulário
const prato = {
    nome: form.nome.value,
    ingredientes: [],
    imagem: form.imagem.value,
    preco: form.preco.value,
};

// Iterar sobre os itens da lista de ingredientes
const lista = listaIngredientes.children; // HTMLCollection
for (let i = 0; i < lista.length; i++) {
    const li = lista[i];
    const textoIngrediente = li.firstChild.textContent;
    prato.ingredientes.push(textoIngrediente);
}



*/