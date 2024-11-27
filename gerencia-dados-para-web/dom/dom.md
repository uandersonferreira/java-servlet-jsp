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
  - `3`: Texto
  - `8`: Comentário
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

