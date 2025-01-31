// Remove as mensagens de alerta após 5 segundos
document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
        var alertElements = document.getElementsByClassName('alert');
        if (alertElements.length > 0) {
            alertElements[0].style.display = 'none';
        }
    }, 5000);
});