function carregarXML(caminho, callback) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4) {
            if (this.status === 200) {
                callback(this.responseXML);
            } else {
                console.error('Erro ao carregar o XML');
                document.getElementById('conteudoListas').innerHTML = 
                    '<div class="no-results">Erro ao carregar as listas de exercícios.</div>';
            }
        }
    };
    xhttp.open("GET", caminho, true);
    xhttp.send();
}

let documentoXML;
const conteudoListas = document.getElementById("conteudoListas");

function criarElementoLista(lista) {
    const titulo = lista.getElementsByTagName("titulo")[0].textContent;
    const itens = lista.getElementsByTagName("item");

    const secao = document.createElement("section");
    const tituloElemento = document.createElement("h2");
    tituloElemento.textContent = titulo;
    secao.appendChild(tituloElemento);

    const listaItens = document.createElement("ul");

    Array.from(itens).forEach(item => {
        const texto = item.textContent.trim();
        const feito = item.getAttribute("feito") || "não";
        const prioridade = item.getAttribute("prioridade") || "não";

        const itemElemento = document.createElement("li");
        itemElemento.textContent = texto;

        if (feito.toLowerCase() === "sim") {
            itemElemento.classList.add("feito");
        }
        if (prioridade.toLowerCase() === "sim") {
            itemElemento.classList.add("prioridade");
        }

        listaItens.appendChild(itemElemento);
    });

    secao.appendChild(listaItens);
    return secao;
}

function exibirTodasAsListas(xml) {
    documentoXML = xml;
    const listas = documentoXML.getElementsByTagName("lista");
    conteudoListas.innerHTML = "";

    if (listas.length === 0) {
        conteudoListas.innerHTML = '<div class="no-results">Nenhuma lista encontrada.</div>';
        return;
    }

    Array.from(listas).forEach(lista => {
        conteudoListas.appendChild(criarElementoLista(lista));
    });
}

function filtrarListas() {
    const tipoFiltro = document.getElementById("filtroTipo").value;
    const valorFiltro = document.getElementById("filtroValor").value.toLowerCase();

    if (!documentoXML) return;

    const listas = documentoXML.getElementsByTagName("lista");
    conteudoListas.innerHTML = "";

    let encontrouResultados = false;

    Array.from(listas).forEach(lista => {
        let mostrarLista = false;
        const titulo = lista.getElementsByTagName("titulo")[0].textContent.toLowerCase();

        if (tipoFiltro === "titulo") {
            mostrarLista = titulo.includes(valorFiltro);
        } else {
            const itens = lista.getElementsByTagName("item");
            mostrarLista = Array.from(itens).some(item => {
                const valorAtributo = (item.getAttribute(tipoFiltro) || "não").toLowerCase();
                return valorAtributo === valorFiltro.toLowerCase();
            });
        }

        if (mostrarLista) {
            encontrouResultados = true;
            conteudoListas.appendChild(criarElementoLista(lista));
        }
    });

    if (!encontrouResultados) {
        conteudoListas.innerHTML = '<div class="no-results">Nenhuma lista encontrada com o filtro aplicado.</div>';
    }
}

function limparFiltro() {
    document.getElementById("filtroValor").value = "";
    exibirTodasAsListas(documentoXML);
}

// Event Listeners
document.getElementById("aplicarFiltro").addEventListener("click", filtrarListas);
document.getElementById("limparFiltro").addEventListener("click", limparFiltro);
document.getElementById("filtroTipo").addEventListener("change", function() {
    const filtroValor = document.getElementById("filtroValor");
    if (this.value === "feito" || this.value === "prioridade") {
        filtroValor.placeholder = "Digite 'sim' ou 'não'";
    } else {
        filtroValor.placeholder = "Digite o valor do filtro";
    }
});

// Carrega o XML inicial
carregarXML("listas.xml", exibirTodasAsListas);

/**
fluxo do código passo a passo:

1. Inicialização e Carregamento:
- O código começa executando `carregarXML("listas.xml", exibirTodasAsListas)`
- Esta função usa XMLHttpRequest para buscar o arquivo listas.xml
- Se o arquivo for carregado com sucesso (status 200), chama a função `exibirTodasAsListas`
- Se houver erro, mostra uma mensagem de erro na tela

2. Exibição Inicial das Listas (`exibirTodasAsListas`):
- Armazena o XML carregado na variável global `documentoXML`
- Obtém todas as tags `<lista>` do XML
- Limpa o conteúdo atual da página (`conteudoListas.innerHTML = ""`)
- Se não encontrar listas, mostra mensagem "Nenhuma lista encontrada"
- Para cada lista encontrada, chama `criarElementoLista` para criar sua representação HTML

3. Criação dos Elementos HTML (`criarElementoLista`):
- Recebe uma lista do XML como parâmetro
- Extrai o título da lista da tag `<titulo>`
- Cria uma seção (`<section>`) para a lista
- Cria um cabeçalho (`<h2>`) com o título
- Para cada item da lista:
   - Obtém o texto do item
   - Verifica os atributos "feito" e "prioridade" (usa "não" como padrão se não existirem)
   - Cria um elemento `<li>` com o texto
   - Adiciona classes CSS "feito" e/ou "prioridade" se necessário
- Retorna a seção completa com todos os itens

4. Sistema de Filtros:
- O usuário pode selecionar um tipo de filtro (título, feito, prioridade)
- Quando o tipo de filtro muda, atualiza o placeholder do campo de texto:
   - Para "feito" ou "prioridade": mostra "Digite 'sim' ou 'não'"
   - Para "título": mostra "Digite o valor do filtro"

5. Aplicação do Filtro (`filtrarListas`):
- Obtém o tipo e valor do filtro
- Verifica cada lista do XML:
   - Para filtro por título: verifica se o título contém o texto digitado
   - Para filtros "feito"/"prioridade": verifica se algum item da lista tem o valor correspondente
- Se encontrar correspondências:
   - Cria e exibe as listas que correspondem ao filtro
- Se não encontrar nada:
   - Mostra mensagem "Nenhuma lista encontrada com o filtro aplicado"

6. Limpeza do Filtro (`limparFiltro`):
- Limpa o campo de texto do filtro
- Chama `exibirTodasAsListas` para mostrar todas as listas novamente

O código usa event listeners para responder a três ações do usuário:
- Clique em "Aplicar Filtro": executa a função `filtrarListas`
- Clique em "Limpar Filtro": executa a função `limparFiltro`
- Mudança no tipo de filtro: atualiza o placeholder do campo de texto

Esta estrutura permite uma experiência interativa onde o usuário pode:
- Ver todas as listas inicialmente
- Filtrar por diferentes critérios
- Limpar os filtros facilmente
- Receber feedback visual sobre o estado dos itens (feitos/prioritários) e resultados da filtragem

 */