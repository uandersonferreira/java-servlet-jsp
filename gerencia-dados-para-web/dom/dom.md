### **Métodos do DOM**

> [Document Object Model (DOM) - MDN Web Docs](https://developer.mozilla.org/pt-BR/docs/Web/API/Document_Object_Model/Introduction)

1. **`querySelector()`**  
   - **O que faz:** Seleciona o primeiro elemento que corresponde ao seletor CSS especificado.
   - **Retorna:** O elemento HTML correspondente ou `null` se não encontrar.
   - **Quando usar:** Quando você precisa manipular um único elemento, como um botão ou parágrafo específico.
   - **Exemplo:**
     ```javascript
     const elemento = document.querySelector('.minha-classe');
     console.log(elemento); // Exibe o primeiro elemento com a classe 'minha-classe'
     ```

2. **`querySelectorAll()`**  
   - **O que faz:** Seleciona todos os elementos que correspondem ao seletor CSS especificado, retornando uma NodeList.
   - **Retorna:** Uma NodeList (lista de nós) contendo todos os elementos correspondentes.
   - **Quando usar:** Para manipular um conjunto de elementos, como todas as divs ou botões de uma página.
   - **Exemplo:**
     ```javascript
     const elementos = document.querySelectorAll('.minha-classe');
     console.log(elementos); // Exibe uma NodeList com todos os elementos com a classe 'minha-classe'
     ```

3. **`createElement()`**  
   - **O que faz:** Cria um novo elemento HTML.
   - **Retorna:** O novo elemento criado.
   - **Quando usar:** Para adicionar novos elementos à página dinamicamente.
   - **Exemplo:**
     ```javascript
     const novoElemento = document.createElement('div');
     novoElemento.textContent = 'Olá, mundo!';
     document.body.appendChild(novoElemento); // Adiciona o novo elemento ao final do body
     ```

4. **`remove()`**  
   - **O que faz:** Remove um elemento do DOM.
   - **Retorna:** `undefined`
   - **Quando usar:** Para excluir elementos obsoletos ou ocultar algo da interface.
   - **Exemplo:**
     ```javascript
     const elemento = document.querySelector('.minha-classe');
     if (elemento) {
       elemento.remove();
     }
     ```

5. **`classList.add()`**  
   - **O que faz:** Adiciona uma classe CSS a um elemento.
   - **Retorna:** `undefined`
   - **Quando usar:** Para aplicar estilos dinamicamente.
   - **Exemplo:**
     ```javascript
     const elemento = document.querySelector('.minha-classe');
     if (elemento) {
       elemento.classList.add('nova-classe');
     }
     ```

6. **`textContent`**  
   - **O que faz:** Define ou obtém o conteúdo de texto de um elemento.
   - **Retorna:** O texto contido no elemento (como getter) ou `undefined` (como setter).
   - **Quando usar:** Para alterar ou ler o texto visível de um elemento.
   - **Exemplo:**
     ```javascript
     const elemento = document.querySelector('.minha-classe');
     console.log(elemento.textContent); // Exibe o texto atual do elemento
     elemento.textContent = 'Novo texto';
     ```

7. **`setAttribute()`**  
   - **O que faz:** Define um atributo em um elemento (como `id`, `src`, etc.).
   - **Retorna:** `undefined`
   - **Quando usar:** Para alterar atributos ou configurar novos valores.
   - **Exemplo:**
     ```javascript
     const imagem = document.querySelector('img');
     if (imagem) {
       imagem.setAttribute('src', 'novo-endereco.jpg');
     }
     ```

### Novos Métodos

8. **`getAttribute()`**  
   - **O que faz:** Obtém o valor de um atributo especificado de um elemento.
   - **Retorna:** O valor do atributo especificado.
   - **Quando usar:** Para obter o valor de um atributo de um elemento.
   - **Exemplo:**
     ```javascript
     const imagem = document.querySelector('img');
     if (imagem) {
       const src = imagem.getAttribute('src');
       console.log(src); // Exibe o valor do atributo src
     }
     ```

9. **`appendChild()`**  
   - **O que faz:** Adiciona um nó (elemento) como filho do elemento especificado.
   - **Retorna:** O nó adicionado.
   - **Quando usar:** Para adicionar elementos ao DOM.
   - **Exemplo:**
     ```javascript
     const lista = document.querySelector('ul');
     const novoItem = document.createElement('li');
     novoItem.textContent = 'Novo item';
     lista.appendChild(novoItem);
     ```

10. **`removeChild()`**  
    - **O que faz:** Remove um nó filho do DOM.
    - **Retorna:** O nó removido.
    - **Quando usar:** Para remover elementos filhos do DOM.
    - **Exemplo:**
      ```javascript
      const lista = document.querySelector('ul');
      const item = lista.querySelector('li');
      if (item) {
        lista.removeChild(item);
      }
      ```

11. **`innerHTML`**  
    - **O que faz:** Define ou obtém a marcação HTML dentro de um elemento.
    - **Retorna:** O conteúdo HTML como uma string (como getter) ou `undefined` (como setter).
    - **Quando usar:** Para alterar ou ler o HTML interno de um elemento.
    - **Exemplo:**
      ```javascript
      const div = document.querySelector('div');
      console.log(div.innerHTML); // Exibe o HTML interno atual do div
      div.innerHTML = '<p>Novo conteúdo</p>';
      ```

### Explicações Adicionais

- **`querySelector()` e `querySelectorAll()`**: Ambos são métodos para selecionar elementos, mas `querySelector()` seleciona o primeiro elemento que corresponde ao seletor, enquanto `querySelectorAll()` retorna todos os elementos correspondentes como uma NodeList.

- **`createElement()`**: Cria um novo elemento, mas ele não é inserido no DOM até que você o adicione explicitamente com métodos como `appendChild()`.

- **`remove()` e `removeChild()`**: Ambos são usados para remover elementos, mas `removeChild()` requer que você passe o nó pai e o nó filho que deseja remover.

- **`setAttribute()` e `getAttribute()`**: Usados para manipular atributos de elementos HTML, permitindo que você altere ou obtenha valores como `src`, `id`, `class`, etc.


## Tipos de Nós

### documentElement
- **O que faz**: Representa o nó raiz de um documento. Para HTML, é o elemento `<html>`.
- **Retorna**: O nó de elemento raiz do documento.
- **Exemplo**:
  ```javascript
  const raiz = document.documentElement;
  console.log(raiz); // Exibe o elemento <html>
  ```

### nodeName
- **O que faz**: Retorna o nome do nó. Para elementos, é o nome da tag.
- **Retorna**: Uma string com o nome do nó.
- **Exemplo**:
  ```javascript
  const elemento = document.querySelector('div');
  console.log(elemento.nodeName); // Exibe: DIV
  ```

### nodeType
- **O que faz**: Retorna o tipo do nó, representado por um número inteiro.
- **Retorna**: Um número que representa o tipo do nó (ex.: `1` para elementos, `3` para nós de texto).
- **Tipos Comuns**:
  - `1`: Elemento
  - `2`: Atributo
  - `3`: Texto
  - `8`: Comentário
  - `9`: Documento

- **Exemplo**:
  ```javascript
  const elemento = document.querySelector('div');
  console.log(elemento.nodeType); // Exibe: 1 (indicando que é um nó de elemento)
  ```

### Resumo de Tipos de Nós

1. **Element Nodes (Nós de Elemento)**
   - Nome: `nodeName` retorna o nome da tag (`DIV`, `SPAN`, etc.)
   - Tipo: `nodeType` retorna `1`

2. **Text Nodes (Nós de Texto)**
   - Nome: `#text`
   - Tipo: `nodeType` retorna `3`

3. **Comment Nodes (Nós de Comentário)**
   - Nome: `#comment`
   - Tipo: `nodeType` retorna `8`

4. **Document Nodes (Nós de Documento)**
   - Nome: `#document`
   - Tipo: `nodeType` retorna `9`

### Exemplo Completo

Vamos combinar esses conceitos em um exemplo completo:

```javascript
// Cria e manipula elementos no DOM
const div = document.createElement('div');
div.textContent = 'Olá, mundo!';
document.body.appendChild(div);

// Acessa e exibe informações sobre o nó
console.log('documentElement:', document.documentElement); // <html>...</html>
console.log('nodeName:', div.nodeName); // DIV
console.log('nodeType:', div.nodeType); // 1 (Elemento)
console.log('nodeType do texto:', div.firstChild.nodeType); // 3 (Texto)

// Cria um comentário
const comentario = document.createComment('Este é um comentário');
document.body.appendChild(comentario);
console.log('nodeName do comentário:', comentario.nodeName); // #comment
console.log('nodeType do comentário:', comentario.nodeType); // 8 (Comentário)
```

### Mais Tipos de Nós

1. **Element Nodes (Nós de Elemento)**:
   - **Descrição**: Representam as tags HTML ou XML.
   - **Exemplo**: `<div>`, `<p>`, `<span>`

   ```javascript
   const div = document.createElement('div'); // Cria um nó de elemento <div>
   console.log(div); // Exibe: <div></div>
   ```

2. **Text Nodes (Nós de Texto)**:
   - **Descrição**: Contêm o texto dentro dos elementos.
   - **Exemplo**: O texto "Olá, mundo!" dentro de `<p>Olá, mundo!</p>`

   ```javascript
   const texto = document.createTextNode('Olá, mundo!'); // Cria um nó de texto
   const p = document.createElement('p');
   p.appendChild(texto); // Adiciona o nó de texto ao elemento <p>
   console.log(p); // Exibe: <p>Olá, mundo!</p>
   ```

3. **Attribute Nodes (Nós de Atributo)**:
   - **Descrição**: Representam os atributos de um elemento.
   - **Exemplo**: `class="minha-classe"` em `<div class="minha-classe"></div>`

   ```javascript
   const div = document.createElement('div');
   div.setAttribute('class', 'minha-classe'); // Define um atributo
   console.log(div); // Exibe: <div class="minha-classe"></div>
   ```

4. **Comment Nodes (Nós de Comentário)**:
   - **Descrição**: Contêm comentários no código.
   - **Exemplo**: `<!-- Este é um comentário -->`

   ```javascript
   const comentario = document.createComment('Este é um comentário'); // Cria um nó de comentário
   console.log(comentario); // Exibe: <!-- Este é um comentário -->
   ```

5. **Document Nodes (Nós de Documento)**:
   - **Descrição**: Representam o documento inteiro.
   - **Exemplo**: `document`

   ```javascript
   console.log(document); // Exibe o nó do documento inteiro
   ```

6. **DocumentType Nodes (Nós de Tipo de Documento)**:
   - **Descrição**: Representam a declaração do tipo de documento (DOCTYPE).
   - **Exemplo**: `<!DOCTYPE html>`

   ```javascript
   console.log(document.doctype); // Exibe: <!DOCTYPE html>
   ```

### Explicações Adicionais

- **Nós de Elemento**: São os mais comuns e representam todas as tags HTML ou XML. Eles podem conter outros nós, como nós de texto ou de atributo.
- **Nós de Texto**: Contêm apenas texto e não têm tags. Eles são filhos de nós de elemento.
- **Nós de Atributo**: Associados a nós de elemento, representam atributos como `id`, `class`, `src`, etc. Embora possam ser acessados e manipulados diretamente, no DOM moderno, eles são frequentemente gerenciados por métodos do elemento.
- **Nós de Comentário**: Usados para inserir comentários no código. Eles não afetam a renderização da página e são úteis para desenvolvedores.
- **Nós de Documento**: Representam o documento inteiro e são a raiz da árvore do DOM. Todos os outros nós são descendentes deste nó.
- **Nós de Tipo de Documento**: Representam a declaração do tipo de documento, como `<!DOCTYPE html>`, que indica a versão do HTML utilizada.


### **nodeValue**

A propriedade `nodeValue` do DOM é usada para acessar ou modificar o conteúdo de **nós** (nodes) em uma árvore DOM. Ela pode ser aplicada a diferentes tipos de nós, mas seu comportamento varia dependendo do tipo de nó. Aqui está um guia detalhado:

---

#### **Para nós de texto (nodeType 3)**
Os nós de texto são aqueles que contêm somente texto puro, sem formatação. A propriedade `nodeValue`:

- Retorna o conteúdo textual do nó.
- Pode ser usada para **ler** ou **alterar** o texto diretamente.

**Exemplo:**
```javascript
const paragrafo = document.querySelector('p'); // Seleciona o primeiro parágrafo
const textoNode = paragrafo.firstChild; // Obtém o nó de texto (filho do parágrafo)
console.log(textoNode.nodeValue); // Mostra o texto atual do nó
textoNode.nodeValue = "Novo texto"; // Altera o conteúdo do texto
```

#### **Para outros tipos de nós**
1. **Elementos (nodeType 1):**
   - `nodeValue` retorna `null`.
   - Os elementos, como `<div>` ou `<p>`, não possuem valor de nó diretamente. É necessário acessar seus nós filhos de texto para usar `nodeValue`.

2. **Atributos (nodeType 2):**
   - `nodeValue` retorna o valor do atributo.

   **Exemplo:**
   ```javascript
   const link = document.querySelector('a');
   const atributo = link.getAttributeNode('href'); // Obtém o nó do atributo
   console.log(atributo.nodeValue); // Mostra o valor do atributo href
   atributo.nodeValue = "https://novo-link.com"; // Altera o valor do atributo
   ```

3. **Comentários (nodeType 8):**
   - `nodeValue` retorna o conteúdo do comentário.

   **Exemplo:**
   ```javascript
   const comentario = document.createComment('Este é um comentário');
   console.log(comentario.nodeValue); // "Este é um comentário"
   comentario.nodeValue = 'Comentário atualizado'; // Altera o conteúdo do comentário
   console.log(comentario.nodeValue); // "Comentário atualizado"
   ```

---

### **Pontos importantes sobre `nodeValue`**
- **Uso em nós de texto:** 
  O `nodeValue` é mais utilizado para manipular nós de texto puros. É útil, por exemplo, quando você quer alterar o texto sem mexer diretamente no HTML.
  
- **Alternativas mais comuns:**
  Apesar de funcional, é mais comum usar propriedades como:
  - `textContent`: Para manipular o texto diretamente.
  - `innerHTML`: Para manipular o conteúdo HTML.

  **Exemplo de comparação:**
  ```javascript
  const paragrafo = document.querySelector('p');
  console.log(paragrafo.textContent); // Retorna o texto do elemento
  console.log(paragrafo.innerHTML); // Retorna o conteúdo HTML do elemento
  ```

- **Manipulação de nós filhos:**
  Ao usar `nodeValue` em elementos que possuem filhos, você precisa acessar os **nós de texto** diretamente:
  ```javascript
  const div = document.querySelector('div');
  const textoNode = div.firstChild; // Obtém o nó de texto
  console.log(textoNode.nodeValue); // Exibe o texto
  textoNode.nodeValue = "Texto alterado"; // Altera o conteúdo do texto
  ```

- **Elementos folha:**
  Para nós que não têm filhos (nós folha), `nodeValue` pode ser usado diretamente no nó de texto.

---

### **Conclusão**
A propriedade `nodeValue` é poderosa quando você precisa trabalhar diretamente com nós de texto ou outros tipos de nós, como atributos e comentários. No entanto, para manipular elementos HTML e texto de forma mais direta e abrangente, propriedades como `textContent` e `innerHTML` geralmente são preferidas. 

### **Resumo da funcionalidade do `nodeValue`**
| **Tipo de Nó**       | **nodeType** | **Retorno de nodeValue**            |
|-----------------------|--------------|-------------------------------------|
| **Texto**            | 3            | Conteúdo textual do nó             |
| **Elemento**         | 1            | `null`                              |
| **Atributo**         | 2            | Valor do atributo                  |
| **Comentário**       | 8            | Conteúdo do comentário             |


Abaixo está um exemplo completo de manipulação dos nós usando o DOM, com base na estrutura XML que você forneceu:

### **Exemplo de Manipulação com `nodeValue`**

```javascript
// Suponha que temos este XML carregado em uma string
const xmlString = `
<Pessoa>
    <nome>José Silva</nome>
    <email>josesilva@gmail.com</email>
    <data_nascimento>10-12-2000</data_nascimento>
    <telefones>
        <celular>98123 4321</celular>
        <fixo>3223 3434</fixo>
    </telefones>
</Pessoa>`;

// Parseamos o XML para criar um documento DOM
const parser = new DOMParser();
const xmlDoc = parser.parseFromString(xmlString, "text/xml");

// 1. **Acessando a raiz (<Pessoa>):**
const raiz = xmlDoc.documentElement; // Raiz é <Pessoa>
console.log("Raiz:", raiz.nodeName); // Saída: Pessoa

// 2. **Acessando os filhos diretos da raiz:**
const filhos = raiz.childNodes; // Inclui os elementos filhos e possíveis nós de texto
filhos.forEach((filho) => {
    if (filho.nodeType === 1) {
        console.log(`Filho: ${filho.nodeName}, Valor: ${filho.firstChild.nodeValue.trim()}`);
    }
});

// 3. **Manipulando o nó folha (texto do <nome>):**
const nomeNode = xmlDoc.querySelector("nome"); // Seleciona o nó <nome>
console.log("Nome antes:", nomeNode.firstChild.nodeValue); // José Silva
nomeNode.firstChild.nodeValue = "João Santos"; // Altera o texto
console.log("Nome depois:", nomeNode.firstChild.nodeValue); // João Santos

// 4. **Acessando o pai de um nó (parentNode):**
const emailNode = xmlDoc.querySelector("email");
console.log("Pai do nó <email>:", emailNode.parentNode.nodeName); // Pessoa

// 5. **Acessando os nós folha em <telefones>:**
const telefonesNode = xmlDoc.querySelector("telefones"); // Seleciona <telefones>
telefonesNode.childNodes.forEach((telefone) => {
    if (telefone.nodeType === 1) { // Ignora nós de texto e foca em elementos
        console.log(`${telefone.nodeName}: ${telefone.firstChild.nodeValue.trim()}`);
    }
});

// 6. **Adicionando um novo filho em <telefones>:**
const novoTelefone = xmlDoc.createElement("trabalho"); // Cria um novo nó
novoTelefone.appendChild(xmlDoc.createTextNode("98765 4321")); // Adiciona o texto
telefonesNode.appendChild(novoTelefone); // Adiciona o novo nó à lista de telefones

console.log("Novo XML:");
console.log(new XMLSerializer().serializeToString(xmlDoc)); // Exibe o XML atualizado
```

---

### **Saída no Console**

1. **Raiz:**
   ```
   Raiz: Pessoa
   ```

2. **Filhos diretos da raiz:**
   ```
   Filho: nome, Valor: José Silva
   Filho: email, Valor: josesilva@gmail.com
   Filho: data_nascimento, Valor: 10-12-2000
   Filho: telefones, Valor: [Não exibido porque o nó contém elementos]
   ```

3. **Manipulação do `<nome>`:**
   ```
   Nome antes: José Silva
   Nome depois: João Santos
   ```

4. **Pai do `<email>`:**
   ```
   Pai do nó <email>: Pessoa
   ```

5. **Nós folha em `<telefones>`:**
   ```
   celular: 98123 4321
   fixo: 3223 3434
   ```

6. **XML atualizado após adicionar `<trabalho>`:**
   ```xml
   <Pessoa>
       <nome>João Santos</nome>
       <email>josesilva@gmail.com</email>
       <data_nascimento>10-12-2000</data_nascimento>
       <telefones>
           <celular>98123 4321</celular>
           <fixo>3223 3434</fixo>
           <trabalho>98765 4321</trabalho>
       </telefones>
   </Pessoa>
   ```

---

### **Explicação do Código**
1. **`documentElement`:** Obtém a raiz do documento XML (`<Pessoa>`).
2. **`childNodes`:** Lista todos os filhos diretos, incluindo nós de texto.
3. **`nodeValue`:** Manipula os textos dos nós folha diretamente.
4. **`parentNode`:** Permite acessar o pai de um nó.
5. **`createElement` e `createTextNode`:** Usados para criar novos elementos e textos, que podem ser adicionados ao DOM.
6. **`serializeToString`:** Converte o documento DOM em uma string XML.

Com esse exemplo, você pode explorar como navegar, acessar e manipular diferentes partes de uma árvore XML usando o DOM.