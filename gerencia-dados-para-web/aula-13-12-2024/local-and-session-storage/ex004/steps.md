Para implementar um carrinho de compras que salva os itens e exibe os detalhes da venda em outra tela, siga os passos abaixo:

---

### **Passo 1: Estruturar as telas (HTML)**  
Crie duas páginas HTML: uma para o carrinho e outra para o resumo da compra.  
1. **Carrinho de compras (cart.html):** Exibe os produtos disponíveis, o carrinho e um botão para finalizar a compra.
2. **Resumo da venda (summary.html):** Exibe os itens salvos do carrinho, simulando o resumo da venda.

---

### **Passo 2: Criar um fluxo básico no JavaScript**
Implemente as seguintes funcionalidades no JavaScript para a primeira tela:
1. **Adicionar ao carrinho:** Permitir que os itens sejam adicionados ao LocalStorage.
2. **Exibir itens no carrinho:** Ler os itens do LocalStorage e exibir na tela.
3. **Finalizar a compra:** Ao clicar em um botão, salve os itens do carrinho em uma nova chave do LocalStorage, como `lastSale`, e redirecione para a tela de resumo da venda.

No JavaScript da segunda tela:
1. **Recuperar os itens salvos:** Leia os dados de `lastSale` no LocalStorage.
2. **Exibir o resumo da compra:** Renderize os dados na página.

---

### **Passo 3: Manipular o LocalStorage para armazenar os itens**  
Salve os itens no formato de lista no LocalStorage e utilize funções como `JSON.stringify()` e `JSON.parse()` para gerenciar os dados.

---

### **Passo 4: Implementar o código**

#### **Arquivo `cart.html` (Carrinho de Compras)**
Crie uma interface com produtos disponíveis e o carrinho, incluindo um botão para finalizar a compra.

#### **JavaScript para `cart.html`**
1. Use o código que você já tem para adicionar ao carrinho e exibir os itens.
2. Implemente o botão **Finalizar Compra**, que salvará os itens e redirecionará para `summary.html`:

```javascript
function finalizePurchase() {
  const cart = getCart(); // Recupera os itens do carrinho
  if (cart.length === 0) {
    alert('O carrinho está vazio!');
    return;
  }

  // Salva os itens do carrinho em uma nova chave no LocalStorage
  localStorage.setItem('lastSale', JSON.stringify(cart));

  // Redireciona para a página de resumo
  window.location.href = 'summary.html';
}
document.getElementById('finalize-button').addEventListener('click', finalizePurchase);
```

---

#### **Arquivo `summary.html` (Resumo da Compra)**  
Esta página exibirá os itens salvos como resumo da venda.

#### **JavaScript para `summary.html`**
1. Leia os dados da chave `lastSale` do LocalStorage.
2. Renderize os itens no DOM:

```javascript
function displaySummary() {
  const sale = localStorage.getItem('lastSale');
  const saleList = document.getElementById('sale-list');

  if (!sale) {
    saleList.textContent = 'Nenhuma venda foi finalizada!';
    return;
  }

  const items = JSON.parse(sale);
  saleList.innerHTML = '';
  items.forEach(item => {
    const li = document.createElement('li');
    li.textContent = item;
    saleList.appendChild(li);
  });
}
displaySummary();
```

---

### **Passo 5: Estilizar (CSS)**  
Adicione estilos simples para manter a interface organizada e responsiva.

---

### **Resumo**
- **Página 1:** Carrinho com produtos, funcionalidades de adicionar/limpar carrinho e finalizar a compra.
- **Página 2:** Resumo da compra exibindo os itens salvos no LocalStorage.
- Utilize o DOM para manipular as telas e transições de maneira dinâmica.

