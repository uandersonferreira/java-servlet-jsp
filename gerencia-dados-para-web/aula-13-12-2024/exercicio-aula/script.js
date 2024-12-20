const formContainer = document.querySelector('#form-container');


formContainer.addEventListener('submit', (event) => {

    let nome = document.getElementById('nome');
    let ingredientesList = document.getElementById('ingredientesSelection');
    let urlImage = document.getElementById('urlImage');
    let preco = document.getElementById('preco');
    

    let xmlDoc;
    let cardapio = localStorage.getItem('cardapio');

    if (cardapio) {
        let analisador = new DOMParser();
        xmlDoc = analisador.parseFromString(cardapio, 'text/xml');
    }else  {
        xmlDoc = document.implementation.createDocument(null, "cardapio");
    }
    

    let prato = xmlDoc.createElement('prato');
    let nomePrato = xmlDoc.createElement('nome');
    let ingredientes = xmlDoc.createElement('ingredientes');


    for (let i = 0; i < ingredientesList.options.length; i++) {
        if (ingredientesList.options[i].selected) {
            let item = xmlDoc.createElement('item');
            item.textContent = ingredientesList.options[i].text;
            ingredientes.appendChild(item);
        }
    }

    let imagem = xmlDoc.createElement('imagem');
    imagem.textContent = urlImage.value;

    let precoPrato = xmlDoc.createElement('preco');
    precoPrato.textContent = preco.value;

    nomePrato.textContent = nome.value;
    prato.appendChild(nomePrato);
    prato.appendChild(ingredientes);
    prato.appendChild(imagem);
    prato.appendChild(precoPrato);

    xmlDoc.documentElement.appendChild(prato);


    let serializer = new XMLSerializer();

    let xmlString = serializer.serializeToString(xmlDoc);

    localStorage.setItem('cardapio', xmlString);

});


