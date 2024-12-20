function ajax(caminho, funcao){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = funcao;
    xhttp.open("GET", caminho, true);
    xhttp.send();
}

let doc;
function iniciarMenu(){
    if (this.readyState == 4 && this.status == 200){
        doc = this.responseXML;
        let titulos = doc.getElementsByTagName("titulo");
        let texto = "";
        for (let titulo of titulos){
            texto += 
            `<li>
                <a href=# onclick="mostrar(${titulo.parentNode.getAttribute("id")})>  
                ${titulo.firstChild.nodeValue}
                </a>
            </li>`;
        }//for
        document.getElementById("navegacao").innerHTML = texto;
        mostrar(1);
        console.log(texto);
    }
}

function mostrar(id){
    if (!doc) {
        console.error("Documento XML não carregado.");
        return;
    }
    let listas = doc.getElementsByTagName("lista");
    for (let lista of listas){
        if (lista.getAttribute("id") == id){
            document.getElementById("titulo").innerHTML = pegaTitulo(lista);
            criaListaItens(
                lista,
                document.getElementById("listanaofeito"),
                document.getElementById("listafeito")
            );
        
        }
    }
}

function pegaTitulo(lista){
    return lista.getElementsByTagName("titulo")[0].firstChild.nodeValue;
}

function criaListaItens(lista, listaNaoFeito, listaFeito){
    let itens = lista.getElementsByTagName("item");
    let textoNaoFeito = "";
    let textoFeito = "";
    for (let item of itens){
        let texto = item.firstChild.nodeValue;
        if (item.getAttribute("prioridade") == "sim"){
            texto = `<strong>${texto}</strong>`;
        }

        if (item.getAttribute("feito") == "sim"){
            textoFeito += `<li><del> ${texto} </del></li>`;
        } else {
            textoNaoFeito += `<li>${texto}</li>`;
        }
    }//for
    listaNaoFeito.innerHTML = textoNaoFeito;
    listaFeito.innerHTML = textoFeito;
}

ajax("./listas.xml", iniciarMenu);