/*
ESSE É O HTML DO PROJETO:

<body>
    <h1>JSON CLIENT INTRODUCTION</h1>
    
    <!-- CRIAR UM FORM PARA CADASTRAR UMA PESSOA -->
    <form id="form">
        <input type="text" id="nome" name="nome" placeholder="Nome">
        <input type="number" id="idade" name="idade" min="0" placeholder="Idade">
        <input type="submit" value="Cadastrar" id="buttonCadastrar">
       
    </form>


    <!-- CRIAR UMA TABELA PARA EXIBIR AS PESSOAS CADASTRADAS -->
    <table id="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>Idade</th>
            </tr>
        </thead>
        <tbody id="relatorio">

        </tbody>
    
    <script src="script.js"></script>
</body>
</html>

    - REQUISITOS A SEREM IMPLEMENTADOS NO PROJETO EM ANDAMENTO:

    1. Implementar a funcionalidade de remover uma pessoa da lista.
    2. A possibilidade de editar uma pessoa da lista.
    3. A possibilidade da pessoa adicionar um array de telefones para ela. (Os campos devem ser dinamicos um para cada entrada de telefone podendo ter N entradas)

    Basicamente e fazer o CRUD, mas com a possibilidade de adicionar um array de telefones para a pessoa,
    analise o código abaixo e der continuidade ao projeto. Não precisa adiconar novas propriedades ao objeto
     pessoa a não ser o array de telefones do requisito 3.
*/

document
  .getElementById("buttonCadastrar")
  .addEventListener("click", function (event) {
    event.preventDefault(); // evita o comportamento padrão do form que é recarregar a página

    let pessoa = pegarDadosPessoa();

    var pessoasList = []; // cria um array vazio para armazenar as pessoas
    if (localStorage.getItem("pessoas") != undefined) {
      // verifica se já existe pessoas no local storage
      pessoasList = JSON.parse(localStorage.getItem("pessoas")); // pega as pessoas do local storage e transforma em um array de objetos
    }

    pessoasList.push(pessoa);
    let pessoasListJson = JSON.stringify(pessoasList); // transforma o array de objetos em uma string JSON
    localStorage.ssetItem("pessoas", pessoasListJson); // salva a string JSON no local storage
    mostrarPessoasRelatorio(); // mostra as pessoas na tela
  });

  
function pegarDadosPessoa() {
  let pessoa = {};
  pessoa.nome = document.getElementById("nome").value;
  pessoa.idade = parseInt(document.getElementById("idade").value);
  return pessoa;
}


function mostrarPessoasRelatorio() {
    let pessoasJson = localStorage.getItem("pessoas");
    let pessoas = JSON.parse(pessoasJson); // transforma a string JSON em um array de objetos

    let tbody = document.getElementById("relatorio"); // pega o tbody da tabela
    tbody.innerHTML = ""; // limpa a tabela

    // percorre o array de objetos e adiciona uma linha na tabela para cada objeto
    // pessoas.forEach(function (pessoa) {
    //   let tr = document.createElement("tr");
    //   let tdNome = document.createElement("td");
    //   let tdIdade = document.createElement("td");
    //   tdNome.textContent = pessoa.nome;
    //   tdIdade.textContent = pessoa.idade;
    //   tr.appendChild(tdNome);
    //   tr.appendChild(tdIdade);
    //   tbody.appendChild(tr);
    // });

        
    // EXEMPLO USANDO TEMPLATE STRING
    pessoas.forEach(function (pessoa) {
      tbody.innerHTML += `
        <tr>
            <td>${pessoa.nome}</td>
            <td>${pessoa.idade}</td>
        </tr>
        `;
    });


  }

  onload = mostrarPessoasRelatorio(); // mostra as pessoas na tela quando a página carrega