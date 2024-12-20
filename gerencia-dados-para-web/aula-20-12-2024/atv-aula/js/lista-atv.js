function carregarArquivo(caminho, callback) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            callback(this.responseXML);
        }
    };
    xhttp.open("GET", caminho, true);
    xhttp.send();
}

let documentoXML;

function inicializarMenu(xml) {
    documentoXML = xml;
    const listas = documentoXML.getElementsByTagName("lista");
    const menuNavegacao = document.getElementById("menuNavegacao");

    const linkMostrarTodosItens = document.createElement("a");
    linkMostrarTodosItens.textContent = "Mostrar todos os itens";
    linkMostrarTodosItens.href = "mostrar-todos.html";
   
    const itemMenuOutraPagina = document.createElement("li");
    itemMenuOutraPagina.appendChild(linkMostrarTodosItens);
    menuNavegacao.appendChild(itemMenuOutraPagina);
    
    for (let lista of listas) {
        const idLista = lista.getAttribute("id");
        const tituloLista = lista.getElementsByTagName("titulo")[0].textContent;

        const link = document.createElement("a");
        link.textContent = tituloLista;
        link.href = "#";
        link.addEventListener("click", function () {
            exibirLista(idLista);
        });

        const itemMenu = document.createElement("li");
        itemMenu.appendChild(link);

        menuNavegacao.appendChild(itemMenu);
    }

    // Exibe a primeira lista por padrão
    if (listas.length > 0) {
        exibirLista(listas[0].getAttribute("id"));
    }
}

function exibirLista(id) {
    const listas = documentoXML.getElementsByTagName("lista");
    for (let lista of listas) {
        if (lista.getAttribute("id") === id) {
            const tituloLista = lista.getElementsByTagName("titulo")[0].textContent;
            document.getElementById("tituloLista").textContent = tituloLista;

            const listaNaoConcluidos = document.getElementById("itensNaoConcluidos");
            const listaConcluidos = document.getElementById("itensConcluidos");

            listaNaoConcluidos.innerHTML = ""; // Limpa as listas
            listaConcluidos.innerHTML = "";

            adicionarItens(lista, listaNaoConcluidos, listaConcluidos);
        }
    }
}

function adicionarItens(lista, listaNaoConcluidos, listaConcluidos) {
    const itens = lista.getElementsByTagName("item");

    for (let item of itens) {
        const textoItem = item.textContent.trim();
        const itemElemento = document.createElement("li");
        const textoElemento = document.createTextNode(textoItem);

        if (item.getAttribute("prioridade") === "Sim") {
            const textoNegrito = document.createElement("strong");
            textoNegrito.appendChild(textoElemento);
            itemElemento.appendChild(textoNegrito);
        } else {
            itemElemento.appendChild(textoElemento);
        }

        if (item.getAttribute("feito") === "Sim") {
            const textoRiscado = document.createElement("del");
            textoRiscado.appendChild(itemElemento);
            listaConcluidos.appendChild(textoRiscado);
        } else {
            listaNaoConcluidos.appendChild(itemElemento);
        }
    }
}

carregarArquivo("./listas.xml", inicializarMenu);