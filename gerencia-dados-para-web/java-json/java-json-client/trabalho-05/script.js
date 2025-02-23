document
  .getElementById("form")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // evita o comportamento padrão do form que é recarregar a página
    
    // Verifica se estamos editando ou criando
    const idEdicao = document.getElementById("idEdicao")?.value;
    
    if (idEdicao) {
      // Atualiza pessoa existente
      atualizarPessoa(parseInt(idEdicao));
    } else {
      // Cria nova pessoa
      let pessoa = pegarDadosPessoa();
      var listaPessoas = []; // cria um array vazio para armazenar as pessoas
      
      if (localStorage.getItem("pessoas") != null) {
        // verifica se já existe pessoas no local storage
        listaPessoas = JSON.parse(localStorage.getItem("pessoas")); // pega as pessoas do local storage e transforma em um array de objetos
      }
      
      // Adiciona id à pessoa (Adicional)
      pessoa.id = listaPessoas.length > 0 ? Math.max(...listaPessoas.map(p => p.id)) + 1 : 1;
      
      listaPessoas.push(pessoa);
      localStorage.setItem("pessoas", JSON.stringify(listaPessoas)); // salva a string JSON no local storage
    }
    
    reiniciarFormulario();
    mostrarPessoasRelatorio(); // mostra as pessoas na tela
  });

function pegarDadosPessoa() {
  let pessoa = {};
  pessoa.nome = document.getElementById("nome").value;
  pessoa.idade = parseInt(document.getElementById("idade").value);
  
  // Pega os números de telefone
  pessoa.telefones = [];
  const camposTelefone = document.querySelectorAll('.campo-telefone');
  camposTelefone.forEach(input => {
    if (input.value.trim() !== '') {
      pessoa.telefones.push(input.value.trim());
    }
  });
  
  return pessoa;
}

function atualizarPessoa(id) {
  let listaPessoas = [];
  if (localStorage.getItem("pessoas") != null) {
    listaPessoas = JSON.parse(localStorage.getItem("pessoas"));
  }
  
  const indice = listaPessoas.findIndex(p => p.id === id);
  
  if (indice !== -1) {
    const pessoaAtualizada = pegarDadosPessoa();
    pessoaAtualizada.id = id;
    listaPessoas[indice] = pessoaAtualizada;
    localStorage.setItem("pessoas", JSON.stringify(listaPessoas));
  }
}

function mostrarPessoasRelatorio() {
  let pessoasJson = localStorage.getItem("pessoas");
  let pessoas = [];
  
  if (pessoasJson) {
    pessoas = JSON.parse(pessoasJson); // transforma a string JSON em um array de objetos
  }
  
  let tbody = document.getElementById("relatorio"); // pega o tbody da tabela
  tbody.innerHTML = ""; // limpa a tabela
  
  // EXEMPLO USANDO TEMPLATE STRING
  pessoas.forEach(function (pessoa) {
    const listaTelefones = pessoa.telefones ? 
      pessoa.telefones.map(telefone => `<li>${telefone}</li>`).join('') : '';
    
    tbody.innerHTML += `
      <tr data-id="${pessoa.id}">
        <td>${pessoa.nome}</td>
        <td>${pessoa.idade}</td>
        <td>
          <ul>${listaTelefones}</ul>
        </td>
        <td>
          <button onclick="editarPessoa(${pessoa.id})">Editar</button>
          <button onclick="removerPessoa(${pessoa.id})">Remover</button>
        </td>
      </tr>
    `;
  });
}

function removerPessoa(id) {
  if (confirm('Tem certeza que deseja remover esta pessoa?')) {
    let listaPessoas = [];
    if (localStorage.getItem("pessoas") != null) {
      listaPessoas = JSON.parse(localStorage.getItem("pessoas"));
    }
    
    const listaFiltrada = listaPessoas.filter(p => p.id !== id);
    localStorage.setItem("pessoas", JSON.stringify(listaFiltrada));
    mostrarPessoasRelatorio();
  }
}

function editarPessoa(id) {
  let listaPessoas = [];
  if (localStorage.getItem("pessoas") != null) {
    listaPessoas = JSON.parse(localStorage.getItem("pessoas"));
  }
  
  const pessoa = listaPessoas.find(p => p.id === id);
  
  if (pessoa) {
    // Preenche o formulário com os dados da pessoa
    document.getElementById('nome').value = pessoa.nome;
    document.getElementById('idade').value = pessoa.idade;
    
    // Limpa os campos de telefone existentes
    const containerTelefones = document.getElementById('container-telefones');
    containerTelefones.innerHTML = '';
    
    // Adiciona campos de telefone se a pessoa tiver telefones
    if (pessoa.telefones && pessoa.telefones.length > 0) {
      pessoa.telefones.forEach(telefone => {
        adicionarCampoTelefone(telefone);
      });
    } else {
      adicionarCampoTelefone(); // Adiciona pelo menos um campo vazio
    }
    
    // Adiciona campo oculto para o id de edição
    if (!document.getElementById('idEdicao')) {
      const campoOculto = document.createElement('input');
      campoOculto.type = 'hidden';
      campoOculto.id = 'idEdicao';
      document.getElementById('form').appendChild(campoOculto);
    }
    
    document.getElementById('idEdicao').value = pessoa.id;
    
    // Muda o texto do botão
    document.getElementById('buttonCadastrar').value = 'Atualizar';
  }
}

function reiniciarFormulario() {
  document.getElementById('form').reset();
  
  // Limpa os campos de telefone existentes
  const containerTelefones = document.getElementById('container-telefones');
  containerTelefones.innerHTML = '';
  adicionarCampoTelefone();
  
  // Remove o ID de edição, se existir
  const campoIdEdicao = document.getElementById('idEdicao');
  if (campoIdEdicao) {
    campoIdEdicao.value = '';
  }
  
  // Restaura o texto do botão
  document.getElementById('buttonCadastrar').value = 'Cadastrar';
}

function adicionarCampoTelefone(valor = '') {
  const containerTelefones = document.getElementById('container-telefones');
  const divTelefone = document.createElement('div');
  divTelefone.className = 'campo-telefone-container';
  
  divTelefone.innerHTML = `
    <input type="tel" class="campo-telefone" placeholder="Telefone" value="${valor}">
    <button type="button" class="remover-telefone">-</button>
  `;
  
  containerTelefones.appendChild(divTelefone);
  
  // Adiciona ouvinte de evento para o botão de remoção
  divTelefone.querySelector('.remover-telefone').addEventListener('click', function() {
    containerTelefones.removeChild(divTelefone);
  });
}

// Inicializa na carga da página
function inicializar() {
  // Cria contêiner para campos de telefone, se não existir
  if (!document.getElementById('container-telefones')) {
    const containerTelefones = document.createElement('div');
    containerTelefones.id = 'container-telefones';
    document.getElementById('form').insertBefore(containerTelefones, document.getElementById('buttonCadastrar'));
  }
  
  // Adiciona botão para adicionar mais campos de telefone
  const botaoAdicionarTelefone = document.createElement('button');
  botaoAdicionarTelefone.type = 'button';
  botaoAdicionarTelefone.id = 'adicionar-telefone';
  botaoAdicionarTelefone.textContent = 'Adicionar Telefone';
  botaoAdicionarTelefone.addEventListener('click', function() {
    adicionarCampoTelefone();
  });
  
  document.getElementById('form').insertBefore(botaoAdicionarTelefone, document.getElementById('buttonCadastrar'));
  
  // Adiciona campo de telefone inicial
  adicionarCampoTelefone();
  
  // Mostra pessoas existentes
  mostrarPessoasRelatorio();
}

window.onload = inicializar;