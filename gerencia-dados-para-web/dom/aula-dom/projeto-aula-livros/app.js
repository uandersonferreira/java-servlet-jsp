document.getElementById('addAuthor').addEventListener('click', function() {
    const newAuthorInput = document.createElement('input');
    newAuthorInput.type = 'text';
    newAuthorInput.className = 'author';
    newAuthorInput.required = true;
    document.getElementById('authorsList').insertBefore(newAuthorInput, document.getElementById('addAuthor'));
});

document.getElementById('bookForm').addEventListener('submit', function(event) {
    event.preventDefault();  // Previne o comportamento padrão do formulário

    const title = document.getElementById('title').value;
    const authors = Array.from(document.querySelectorAll('.author')).map(input => input.value);
    const year = document.getElementById('year').value;
    const price = document.getElementById('price').value;

    addBookToList(title, authors, year, price);

    // Limpa o formulário após submissão
    document.getElementById('bookForm').reset();

    // Remove campos de autor adicionais
    const extraAuthors = document.querySelectorAll('.author:not(:first-child)');
    extraAuthors.forEach(authorInput => authorInput.remove());
    /* .author:not(:first-child) Seleciona todos os elementos com a classe author que não são o primeiro filho de seu pai. Em outras palavras, ele seleciona todos os campos de autor que vêm depois do primeiro campo.*/
});

function addBookToList(title, authors, year, price) {
    const bookList = document.getElementById('bookList');

    const article = document.createElement('article');

    const h2 = document.createElement('h2');
    h2.textContent = title;
    article.appendChild(h2);

    const ul = document.createElement('ul');
    authors.forEach(author => {
        const li = document.createElement('li');
        li.textContent = author.trim(); //remover espaços em branco do início e do fim de uma string
        ul.appendChild(li);
    });
    article.appendChild(ul);

    const yearPara = document.createElement('p');
    yearPara.textContent = `Ano: ${year}`;
    article.appendChild(yearPara);

    const pricePara = document.createElement('p');
    pricePara.textContent = `Preço: R$${price}`;
    article.appendChild(pricePara);

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Deletar';
    deleteButton.addEventListener('click', () => {
        bookList.removeChild(article);
    });
    article.appendChild(deleteButton);

    const editButton = document.createElement('button');
    editButton.textContent = 'Editar';
    editButton.classList.add('edit');
    editButton.addEventListener('click', () => {
        editBook(article, title, authors, year, price);
    });
    article.appendChild(editButton);

    bookList.insertBefore(article, bookList.firstChild);
}//function

function editBook(article, title, authors, year, price) {
    document.getElementById('title').value = title;
    document.getElementById('authorsList').innerHTML = '';
    authors.forEach(author => {
        const newAuthorInput = document.createElement('input');
        newAuthorInput.type = 'text';
        newAuthorInput.className = 'author';
        newAuthorInput.value = author;
        newAuthorInput.required = true;
        document.getElementById('authorsList').appendChild(newAuthorInput);
    });
    const addAuthorButton = document.createElement('button');
    addAuthorButton.type = 'button';
    addAuthorButton.id = 'addAuthor';
    addAuthorButton.textContent = 'Adicionar Autor';
    document.getElementById('authorsList').appendChild(addAuthorButton);
    addAuthorButton.addEventListener('click', function() {
        const newAuthorInput = document.createElement('input');
        newAuthorInput.type = 'text';
        newAuthorInput.className = 'author';
        newAuthorInput.required = true;
        document.getElementById('authorsList').insertBefore(newAuthorInput, addAuthorButton);
    });

    document.getElementById('year').value = year;
    document.getElementById('price').value = price;

    document.getElementById('bookForm').addEventListener('submit', () => {
        article.remove();
    }, 
    {
         once: true 
         /* especifica que o listener deve ser executado apenas uma vez. Após a primeira execução, o listener é removido automaticamente*/

    });

}//function
