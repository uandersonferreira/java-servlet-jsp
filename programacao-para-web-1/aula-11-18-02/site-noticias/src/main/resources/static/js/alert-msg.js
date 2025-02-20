document.addEventListener("DOMContentLoaded", function () {
    const alerts = document.querySelectorAll(".alerts-container");

    alerts.forEach(alert => {
        setTimeout(() => {
            alert.classList.add("hidden"); // Adiciona a classe para fade-out
            setTimeout(() => alert.remove(), 600); // Remove do DOM após a animação
        }, 6000);
    });
});


// recuperar o formulario e evitar que recarrege a página quando apertar f5 ou enter
// document.querySelector('form').addEventListener('submit', function(e) {
//     e.preventDefault();
//
//     const formData = {
//         nome: document.getElementById('nome').value,
//         login: document.getElementById('login').value,
//         senha: document.getElementById('senha').value
//     };
//
//     fetch('/reporters/registro', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(formData)
//     })
//         .then(response => response.json())
//         .then(data => {
//             if (data.success) {
//                 window.location.href = '/reporters/login';
//             } else {
//                 // Handle error response
//                 document.querySelector('.error-message').textContent = data.error;
//             }
//         });
// });
