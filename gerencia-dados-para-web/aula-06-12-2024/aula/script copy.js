let inputTitulo = document.getElementById('inputTitulo');
let inputAno = document.getElementById('inputAno');
let inputPreco = document.getElementById('inputPreco');
let form = document.getElementById('form');
let article = document.getElementById('containerLivros');
let divAutores = document.getElementById('div-autores');
let contador = 0;

function adicionarLivro() {
    let titulo = inputTitulo.value;
    let ano = inputAno.value;
    let preco = inputPreco.value;
    
    let inputAutores = document.querySelectorAll('.inputAutor');

    if ((titulo !== "") && (titulo !== null) && (titulo !== undefined) && 
        (ano !== "") && (ano !== null) && (ano !== undefined) && 
        (preco !== "") && (preco !== null) && (preco !== undefined)) {

        ++contador;
        
        const autores = Array.from(inputAutores)
            .map(input => input.value)
            .filter(valor => valor !== ""); // Remove empty values

        const listaAutores = autores.map(autor => `<li>${autor}</li>`).join('');
        
        let novoLivro = `
        <article id="${contador}">
            <h2>${titulo}</h2>
            <ul>
                ${listaAutores}
            </ul>
            <p>${ano}</p>
            <p>R$ ${preco}</p>
            <button class="delete" onclick="deletar(${contador})">Deletar</button>
        </article>
        `;

        article.innerHTML += novoLivro;
        
        inputTitulo.value = '';
        inputAno.value = '';
        inputPreco.value = '';
        inputAutores.forEach(input => input.value = '');
    }
}

function adicionarNovoAutor() {
    let inputHTML = document.createElement('input');
    inputHTML.type = 'text'; 
    inputHTML.classList.add('inputAutor');
    divAutores.appendChild(inputHTML);
    divAutores.appendChild(document.createElement('br')); 
}

function deletar(id) {
    var livro = document.getElementById(id);
    livro.remove();
}
