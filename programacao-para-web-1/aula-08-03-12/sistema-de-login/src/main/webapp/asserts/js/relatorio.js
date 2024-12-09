function togglePassword(inputId, button) {
    const input = document.getElementById(inputId);

    if (input.type === "password") {
        input.type = "text";
        button.textContent = "Ocultar"; // Ícone para "ocultar senha"
    } else {
        input.type = "password";
        button.textContent = "Mostrar"; // Ícone para "mostrar senha"
    }
}
