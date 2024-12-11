/*
LocalStorage and SessionStorage 

Chat estou estudando sobre o LocalStorage e SessionStorage
e gostaria da sua ajuda para entender melhor sobre esses conceitos.
Portanto, me ajude a criar um readme.md explicativo sobre os metódos de LocalStorage e SessionStorage.
E se possível, me ajude a criar um exemplo de código para cada método.
Ou um exemplo completo usando o LocalStorage e SessionStorage com a minupulação do DOM.
Me explique sintaxe e uso de cada método.
Abaixo está o código que estou estudando e que foi mostardo em sala de aula:



*/

// 1- Inserindo dados no LocalStorage: chave(key) e valor(value)
localStorage.setItem('name', 'Uanderson');
// Ao salvar dados no LocalStorage, os dados são salvos em formato de string
// e não se perdem mesmo após o navegador ser fechado

// 2- Recuperando dados do LocalStorage via chave(key)
const nameSaved = localStorage.getItem('name');
console.log("Nome salvo no localStorage: " + nameSaved);

// 3- Recuperando dados do LocalStorage que não existe
const nameNotFound = localStorage.getItem('nameNotFound');
console.log("Nome não encontrado: " + nameNotFound); // Retorna null

if(nameNotFound === null){
    console.log("Nome não encontrado");
}

// 4- Removendo dados do LocalStorage via chave(key)
localStorage.removeItem('name');

// 5- Removendo todos os dados do LocalStorage
localStorage.setItem('name', 'Uanderson');
localStorage.setItem('age', '20');
localStorage.setItem('city', 'São Paulo');

console.log(
    'dados salvos no LocalStorage: ', 
    localStorage.getItem('name'), 
    localStorage.getItem('age'), 
    localStorage.getItem('city')
);

// o tipo de dado salvo no LocalStorage é string
console.log(typeof localStorage.getItem('name'));

localStorage.clear();

console.log(
    'dados após limpar o LocalStorage: ', 
    localStorage.getItem('name'), 
    localStorage.getItem('age'), 
    localStorage.getItem('city')
);

// 6- Inserindo dados no SessionStorage: chave(key) e valor(value)
sessionStorage.setItem('number', '122456');

// Ao fechar o navegador, os dados do SessionStorage são perdidos.

// 7 - Recuperando dados do SessionStorage via chave(key)
const numberSaved = sessionStorage.getItem('number');
console.log("Número salvo no SessionStorage: " + numberSaved);

// 8 - Removendo um dado do SessionStorage via chave(key)
sessionStorage.removeItem('number');

// 9 - Removendo todos os dados do SessionStorage
sessionStorage.clear();

// 10- Salvar objeto no LocalStorage
/* Passos que devo sseguir para conseguir fazer isso:
    1- Converter o objeto em uma string JSON
    2- Salvar a string no LocalStorage
*/
const user = {
    name: 'Uanderson',
    age: 20,
    city: 'São Paulo'
}
localStorage.setItem('user', JSON.stringify(user));

// 11- Recuperar objeto (Salvo como string JSON) do LocalStorage
const userSaved = localStorage.getItem('user');
console.log("Usuário salvo no LocalStorage: " + userSaved);

// 12- Converter string JSON para objeto
const userObject = JSON.parse(userSaved);
console.log("Usuário salvo no LocalStorage: " + userObject.name);
