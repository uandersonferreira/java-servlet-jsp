function carregarDadosAjax(caminho, callback) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = callback;
    xhttp.open('GET', caminho, true);
    xhttp.send();
}

let xmlDoc;

function carregarMenuNavegacao() {
    if (this.readyState === 4 && this.status === 200) {
        xmlDoc = this.responseXML;

        const titulos = xmlDoc.getElementsByTagName('titulo');
        let texto = '';

        for (const titulo of titulos) {
            texto += `<a href="#" onclick="exibirListas([xmlDoc.getElementById('${titulo.parentNode.getAttribute('id')}')])">
                ${titulo.textContent}
            </a>`;
        }

        atualizarDOM('navegacao', texto);

        const listas = xmlDoc.getElementsByTagName('lista');
        exibirListas(listas);
    }
}

function exibirListas(listas) {
    atualizarDOM('mostrarListas', '');

    for (const lista of listas) {
        const elementDiv = criarTemplateLista();
        const titulo = elementDiv.querySelector('h1');

        document.getElementById('mostrarListas').appendChild(elementDiv);

        extrairTitulo(lista, titulo);
        criarItensLista(
            lista,
            elementDiv.querySelector('.listafeito'),
            elementDiv.querySelector('.listanaofeito')
        );
    }
}

function criarTemplateLista() {
    const div = document.createElement('div');
    div.classList.add('listas');

    const header = document.createElement('header');
    const titulo = document.createElement('h1');
    titulo.classList.add('titulo');

    const ulFeito = document.createElement('ul');
    ulFeito.classList.add('listafeito');

    const ulNaoFeito = document.createElement('ul');
    ulNaoFeito.classList.add('listanaofeito');

    header.appendChild(titulo);
    div.appendChild(header);
    div.appendChild(ulFeito);
    div.appendChild(ulNaoFeito);

    return div;
}

function extrairTitulo(lista, tituloElemento) {
    tituloElemento.innerHTML = lista.getElementsByTagName('titulo')[0].textContent;
}

function criarItensLista(listaXML, ulNaoFeitos, ulFeitos) {
    const itens = listaXML.getElementsByTagName('item');
    let naoFeitosHTML = '';
    let feitosHTML = '';

    for (const item of itens) {
        let texto = item.textContent;
        if (item.getAttribute('prioridade') === 'sim') {
            texto = `<strong>${texto}</strong>`;
        }

        if (item.getAttribute('feito') === 'sim') {
            feitosHTML += `<li><del>${texto}</del></li>`;
        } else {
            naoFeitosHTML += `<li>${texto}</li>`;
        }
    }

    ulFeitos.innerHTML = feitosHTML;
    ulNaoFeitos.innerHTML = naoFeitosHTML;
}

function aplicarFiltro(atributo, valorEsperado) {
    const listas = xmlDoc.getElementsByTagName('lista');
    const listasFiltradas = [];

    for (const lista of listas) {
        const itens = lista.getElementsByTagName('item');
        const novaLista = lista.cloneNode(true);
        const novosItens = novaLista.querySelector('itens');

        novosItens.innerHTML = '';

        for (const item of itens) {
            if ((item.getAttribute(atributo) || 'nao') === valorEsperado) {
                novosItens.appendChild(item.cloneNode(true));
            }
        }

        if (novosItens.children.length > 0) {
            listasFiltradas.push(novaLista);
        }
    }

    if (listasFiltradas.length > 0) {
        exibirListas(listasFiltradas);
    } else {
        exibirMensagemFeedback(`Nenhum item encontrado para o filtro "${atributo} = ${valorEsperado}".`);
    }
}

function pesquisarTitulo() {
    const inputTitulo = document.getElementById('inputTitulo').value.toLowerCase();
    const titulos = xmlDoc.getElementsByTagName('titulo');
    const listasEncontradas = [];

    for (const titulo of titulos) {
        const textoTitulo = titulo.textContent.toLowerCase();
        if (textoTitulo.includes(inputTitulo)) {
            listasEncontradas.push(titulo.parentNode);
        }
    }

    if (listasEncontradas.length > 0) {
        exibirListas(listasEncontradas);
    } else {
        exibirMensagemFeedback(`Nenhuma lista encontrada com o título: "${inputTitulo}".`);
    }
}

function atualizarDOM(idElemento, conteudoHTML) {
    document.getElementById(idElemento).innerHTML = conteudoHTML;
}

function exibirMensagemFeedback(mensagem) {
    atualizarDOM('mostrarListas', `
        <p style="text-align: center; color: red; font-weight: bold;">
            ${mensagem}
        </p>
    `);
}

// Configurar eventos
document.getElementById('btnTitulo').onclick = pesquisarTitulo;

document.getElementById('btnFeito').onclick = function () {
    aplicarFiltro('feito', 'sim');
};

document.getElementById('btnNaoFeito').onclick = function () {
    aplicarFiltro('feito', 'nao');
};

document.getElementById('btnPrioridade').onclick = function () {
    aplicarFiltro('prioridade', 'sim');
};

document.getElementById('btnSemPrioridade').onclick = function () {
    aplicarFiltro('prioridade', 'nao');
};


carregarDadosAjax('./assets/xml/listas.xml', carregarMenuNavegacao);