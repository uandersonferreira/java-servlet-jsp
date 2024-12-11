function displaySummary() {
    const salesHistory = localStorage.getItem('salesHistory');
    const saleList = document.getElementById('sale-list');
  
    if (!salesHistory) {
      saleList.textContent = 'Nenhuma venda foi finalizada!';
      return;
    }
  
    const allSales = JSON.parse(salesHistory);
    saleList.innerHTML = '';
  
    allSales.forEach((sale, index) => {
      const orderSection = document.createElement('li');
      orderSection.innerHTML = `<h3>Pedido ${index + 1}</h3>`;
      
      const itemList = document.createElement('ul');
      sale.forEach(item => {
        const itemLi = document.createElement('li');
        itemLi.textContent = `${item.name} - Quantidade: ${item.quantity}`;
        itemList.appendChild(itemLi);
      });
  
      orderSection.appendChild(itemList);
      saleList.appendChild(orderSection);
    });
  }
  
  displaySummary();
  