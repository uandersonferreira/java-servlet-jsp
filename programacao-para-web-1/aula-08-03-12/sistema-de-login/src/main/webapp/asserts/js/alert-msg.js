// JavaScript para remover todas as mensagens de erro após 5 segundos
setTimeout(() => {
    // Seleciona todos os elementos com a classe "error-message"
    const errorMessages = document.querySelectorAll('.alert-msg');
    errorMessages.forEach((errorMessage) => {
        errorMessage.style.transition = 'opacity 0.5s'; // Suaviza o desaparecimento
        errorMessage.style.opacity = '0'; // Torna o elemento transparente
        setTimeout(() => errorMessage.remove(), 500); // Remove do DOM após transição
    });
}, 5000); // 5 segundos de espera