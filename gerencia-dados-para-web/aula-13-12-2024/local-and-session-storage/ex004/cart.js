// Seleciona os elementos do DOM
const productList = document.getElementById('product-list');
const cartList = document.getElementById('cart');
const clearCartButton = document.getElementById('clear-cart');
const finalizeButton = document.getElementById('finalize-button');

// Função para recuperar o carrinho do LocalStorage
function getCart() {
  const cart = localStorage.getItem('cart');
  return cart ? JSON.parse(cart) : [];
}

// Função para salvar o carrinho no LocalStorage
function saveCart(cart) {
  localStorage.setItem('cart', JSON.stringify(cart));
}

// Função para recuperar o histórico de vendas do LocalStorage
function getSalesHistory() {
  const salesHistory = localStorage.getItem('salesHistory');
  return salesHistory ? JSON.parse(salesHistory) : [];
}

// Função para salvar o histórico de vendas no LocalStorage
function saveSalesHistory(salesHistory) {
  localStorage.setItem('salesHistory', JSON.stringify(salesHistory));
}

// Função para exibir os itens no carrinho
function displayCart() {
  const cart = getCart();
  cartList.innerHTML = '';

  if (cart.length === 0) {
    const emptyMessage = document.createElement('li');
    emptyMessage.textContent = 'O carrinho está vazio';
    cartList.appendChild(emptyMessage);
    return;
  }

  cart.forEach((item, index) => {
    const li = document.createElement('li');
    li.innerHTML = `
      ${item.name} - Quantidade: ${item.quantity}
      <button class="increase-qty" data-index="${index}">+</button>
      <button class="decrease-qty" data-index="${index}">-</button>
      <button class="remove-item" data-index="${index}">Remover</button>
    `;
    cartList.appendChild(li);
  });
}

// Função para adicionar um item ao carrinho
function addToCart(product) {
  const cart = getCart();
  const existingItem = cart.find(item => item.name === product);

  if (existingItem) {
    existingItem.quantity += 1;
  } else {
    cart.push({ name: product, quantity: 1 });
  }

  saveCart(cart);
  displayCart();
}

// Função para aumentar a quantidade de um item
function increaseQuantity(index) {
  const cart = getCart();
  cart[index].quantity += 1;
  saveCart(cart);
  displayCart();
}

// Função para diminuir a quantidade de um item
function decreaseQuantity(index) {
  const cart = getCart();

  if (cart[index].quantity > 1) {
    cart[index].quantity -= 1;
  } else {
    cart.splice(index, 1); // Remove o item se a quantidade for 1
  }

  saveCart(cart);
  displayCart();
}

// Função para remover um item do carrinho
function removeItem(index) {
  const cart = getCart();
  cart.splice(index, 1);
  saveCart(cart);
  displayCart();
}

// Função para limpar o carrinho
function clearCart() {
  localStorage.removeItem('cart');
  displayCart();
}

// Adiciona eventos aos botões de "Adicionar ao Carrinho"
productList.addEventListener('click', event => {
  if (event.target.classList.contains('add-to-cart')) {
    const product = event.target.getAttribute('data-product');
    addToCart(product);
  }
});

// Adiciona eventos aos botões de ações no carrinho
cartList.addEventListener('click', event => {
  const index = event.target.getAttribute('data-index');

  if (event.target.classList.contains('increase-qty')) {
    increaseQuantity(index);
  } else if (event.target.classList.contains('decrease-qty')) {
    decreaseQuantity(index);
  } else if (event.target.classList.contains('remove-item')) {
    removeItem(index);
  }
});

// Adiciona evento ao botão de "Limpar Carrinho"
clearCartButton.addEventListener('click', clearCart);

// Adiciona evento ao botão de "Finalizar Compra"
finalizeButton.addEventListener('click', () => {
  const cart = getCart();
  if (cart.length === 0) {
    alert('O carrinho está vazio!');
    return;
  }

  // Recupera o histórico de vendas e adiciona o pedido atual
  const salesHistory = getSalesHistory();
  salesHistory.push(cart); // Adiciona o carrinho atual como um novo pedido
  saveSalesHistory(salesHistory);

  // Limpa o carrinho atual
  clearCart();

  // Redireciona para a tela de resumo
  window.location.href = 'summary.html';
});

// Exibe o carrinho ao carregar a página
displayCart();
