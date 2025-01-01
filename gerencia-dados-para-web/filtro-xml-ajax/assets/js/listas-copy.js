function ajax(caminho, funcao){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = funcao;
    xhttp.open('GET', caminho, true);
    xhttp.send();
}

let xmlDoc;
function iniciarMenu(){
    if(this.readyState == 4 && this.status == 200){
        xmlDoc = this.responseXML;
        let titulos = xmlDoc.getElementsByTagName('titulo'); // retorna um array de titulos
        let texto = '';
        for(let titulo of titulos){
            texto += `<a href=# onclick="mostrar(${titulo.parentNode.getAttribute('id')})"> 
                ${titulo.firstChild.nodeValue}
            </a>`;
        }

        document.getElementById('navegacao').innerHTML = texto;
        mostrar(1);
    }
}


function mostrar(id){
    let listas = xmlDoc.getElementsByTagName('lista');
    for(let lista of listas){
        if(lista.getAttribute('id') == id){
            document.getElementById('titulo').innerHTML = pegaTitulo(lista);
            criaListaItens(
                lista,//A lista de fato com os itens vinda do XML
                document.getElementById('listanaofeito'),//tag 'ul' do HTML
                document.getElementById('listafeito')//tag 'ul' do HTML
            );
        }
    }
}

function pegaTitulo(lista){
    return lista.getElementsByTagName('titulo')[0].firstChild.nodeValue;
    //[0] pois de fato só existe uma tag titulo para cada lista dentros de listas.
}

function criaListaItens(listaXML, ul_listanaofeito, ul_listafeito){
    let itens = listaXML.getElementsByTagName('item');
    let textoNaoFeito = '';
    let textoFeito = '';
    for(let item of itens){
        let texto = item.firstChild.nodeValue;
        if(item.getAttribute('prioridade') == 'sim'){
            texto = `<strong>${texto}</strong>`;
        }

        if(item.getAttribute('feito') == 'sim'){
            textoFeito += `<li><del>${texto}</del></li>`;
        }else{
            textoNaoFeito += `<li>${texto}</li>`;
        }

        ul_listafeito.innerHTML = textoFeito;
        ul_listanaofeito.innerHTML = textoNaoFeito;
    }
}

ajax('./assets/xml/listas.xml', iniciarMenu);

