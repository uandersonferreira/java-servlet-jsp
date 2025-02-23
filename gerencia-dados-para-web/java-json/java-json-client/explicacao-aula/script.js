// PEGAR UM TEXTO EM JSON E CONVERTER EM OBJETO JAVASCRIPT
var texto = '{"nome": "João", "idade": 25, "cidade": "São Paulo"}';
var objeto = JSON.parse(texto);
console.log(objeto);

//USANDO O SEGUNDO PARÂMETRO DO JSON.PARSE FUNCTION 'REVIVER' PARA 
// MODIFICAR O OBJETO (1° LETRA DO NOME EM MAIÚSCULA)
var texto = '{"nome": "joão", "idade": 25, "cidade": "São Paulo"}';
var objeto = JSON.parse(texto, function(chave, valor){
    if(chave == 'nome'){
        return valor.charAt(0).toUpperCase() + valor.slice(1);
    }
    return valor;
});
console.log(objeto);

//ENVIAR DADOS DE UM OBJETO JAVASCRIPT PARA UM SERVIDOR EM FORMATO TEXTO JSON
var objeto = {nome: "João", idade: 25, cidade: "São Paulo"};
var texto = JSON.stringify(objeto);
console.log(texto);

//SALVANDO NO LOCAL STORAGE
localStorage.setItem('objeto', texto);

// ENVIANDO PARA O SERVIDOR
function enviar(metodo, endpoint){
    var objeto = {nome: "João", idade: 25, cidade: "São Paulo"};
    var texto = JSON.stringify(objeto);
    var xhr = new XMLHttpRequest();
    xhr.open(metodo, endpoint);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(texto);
}

//EXEMPLO COM O SEGUNDO PARAMETRO DO JSON.STRINGIFY FUNCTION 'REPLACER' PARA 
// FILTRAR AS PROPRIEDADES DO OBJETO QUE SERÃO CONVERTIDAS EM TEXTO JSON (SERIALIZADAS)
// QUAIS PROPRIEDADES DO OBJETO SERÃO CONVERTIDAS EM TEXTO JSON
var objeto = {nome: "João", idade: 25, cidade: "São Paulo"};
var texto = JSON.stringify(objeto, ['nome', 'idade']);
console.log(texto);

//EXEMPLO COM O TERCEIRO PARÂMETRO DO JSON.STRINGIFY FUNCTION 'ESPACAMENTO' PARA
// FORMATAR O TEXTO JSON COM ESPAÇAMENTO
var objeto = {nome: "João", idade: 25, cidade: "São Paulo"};
var texto = JSON.stringify(objeto, null, 4); // 4 ESPAÇOS DE INDENTAÇÃO ou 
var texto = JSON.stringify(objeto, null, '\t'); // TABULAÇÃO
console.log(texto);

//==============================================================================
// CREATE OBJECT PESSOA
const pessoa = {
    nome: 'Lucas',
    idade: 28,
    cidade: 'São Paulo'
}
// PRINT OBJECT PESSOA
console.log(pessoa.nome) // Lucas
console.log(pessoa['idade']) // 28
console.log(pessoa.cidade) // São Paulo

// ADD NEW PROPERTY
pessoa.telefones = ['11999999999', '11999999998']

// SERIALIZATION OBJECT TO JSON 
const pessoaJSON = JSON.stringify(pessoa)
console.log(pessoaJSON) // {"nome":"Lucas","idade":28,"cidade":"São Paulo","telefones":["11999999999","11999999998"]}

// DESERIALIZATION JSON TO OBJECT
const pessoaJSONToObject = JSON.parse(pessoaJSON)
console.log(pessoaJSONToObject) // { nome: 'Lucas', idade: 28, cidade: 'São Paulo', telefones: [ '11999999999', '11999999998' ] }

// SERIALIZATION OBJECT TO JSON WITH PRETTY PRINT
const pessoaJSONPretty = JSON.stringify(pessoa, null, 2)
console.log(pessoaJSONPretty)

