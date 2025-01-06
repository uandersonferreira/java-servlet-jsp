function carregarDadosAjax(caminho, filtros, callback) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = callback;
    xhttp.open('GET', caminho + "?" + filtros, true);
    xhttp.send();
    alert(caminho + "?" + filtros);
}

document.getElementById("button-pesquisa").addEventListener("click", function () {

    const nomeTitulo = document.getElementById("titulo").value;

    carregarDadosAjax("http://localhost:8080/livraria-xml", "titulo=" +nomeTitulo, mostrarDados);
});

function mostrarDados() {
    if (this.readyState === 4 && this.status === 200) {
        const raiz = this.responseXML.documentElement;
        const livros = raiz.getElementsByTagName("livro");
        let texto = "";

        for (let livro of livros) {
            texto += pegaLivro(livro);
        }
        document.getElementById("resultado").innerHTML = texto;
    }
}//function

function pegaLivro(livro) {
    let texto = "<div class='livro'>"
    let filhos = livro.childNodes;
    for (let filho of filhos) {
        if (filho.nodeType === 1){
            texto += `<p> <strong>${filho.nodeName}:</strong> ${filho.textContent}</p>`;
        }
    }
    return texto + "</div>";
}