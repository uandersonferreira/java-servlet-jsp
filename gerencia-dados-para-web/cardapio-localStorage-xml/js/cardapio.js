const container = document.getElementById('cardapio-container');
const template = document.getElementById('template-prato');

// Recuperar XML do localStorage
const xmlString = localStorage.getItem('cardapio');
if (!xmlString) {
    container.textContent = "Nenhum prato cadastrado no momento.";
}

const parser = new DOMParser();
const xmlDoc = parser.parseFromString(xmlString, 'text/xml');

// Exibir cada prato
const pratos = xmlDoc.getElementsByTagName('prato');
if (pratos.length === 0) {
    container.textContent = "Nenhum prato cadastrado no momento.";
}

Array.from(pratos).forEach(prato => {
    //clona o template
    const clone = template.content.cloneNode(true);

    // Nome do prato
    clone.querySelector('h2').textContent = prato.querySelector('nome')?.textContent || "Sem nome";

    // Ingredientes
    const ul = clone.querySelector('ul');
    const ingredientes = prato.getElementsByTagName('ingrediente');
    if (ingredientes.length > 0) {
        Array.from(ingredientes).forEach(ing => {
            const li = document.createElement('li');
            li.textContent = ing.textContent;
            ul.appendChild(li);
        });
    } else {
        const li = document.createElement('li');
        li.textContent = "Sem ingredientes cadastrados";
        ul.appendChild(li);
    }

    // Imagem do prato
    const img = clone.querySelector('img');
    const imagemUrl = prato.querySelector('imagem')?.textContent;
    if (imagemUrl) {
        img.src = imagemUrl;
        img.alt = `Imagem de ${prato.querySelector('nome')?.textContent || "prato"}`;
    } else {
        img.style.display = 'none'; // Esconde a imagem caso não haja URL
    }

    // Preço
    clone.querySelector('.preco').textContent = prato.querySelector('preco')?.textContent
        ? `R$ ${parseFloat(prato.querySelector('preco').textContent).toFixed(2)}`
        : "Preço não informado";

    // Adiciona o prato ao container
    container.appendChild(clone);
});

