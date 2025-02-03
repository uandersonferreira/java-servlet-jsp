document.addEventListener('DOMContentLoaded', () => {

    const ENDPOINT_MENU = 'http://localhost:8080/MenuServlet';
    const ENDPOINT_FIND_BY_ID = 'http://localhost:8080/ListaServlet?id=';

    // Função para carregar dados via AJAX
    function carregarAjax(url, callback) {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                callback(xhr.responseXML);
            }
        };
        xhr.open('GET', url, true);
        xhr.send();
    }

    // Função para carregar o menu
    function carregarMenu() {
        carregarAjax(ENDPOINT_MENU, (xmlDoc) => {
            const menu = document.getElementById('menu');
            const titles = xmlDoc.getElementsByTagName('titulo');

            const ul = document.createElement('ul');
            for (let title of titles) {
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = ENDPOINT_FIND_BY_ID + title.getAttribute('id');
                a.textContent = title.textContent;
                a.addEventListener('click', carregarListaTarefas);
                li.appendChild(a);
                ul.appendChild(li);
            }
            menu.appendChild(ul);
        });
    }

    // Função para carregar lista de tarefas
    function carregarListaTarefas(event) {
        event.preventDefault();
        const url = event.target.getAttribute('href');

        carregarAjax(url, (xmlDoc) => {
            const listaTarefas = document.getElementById('listaTarefas');
            listaTarefas.innerHTML = ''; // Limpar lista anterior

            // Título
            const title = xmlDoc.querySelector('titulo');
            const titleEl = document.createElement('h2');
            titleEl.textContent = title.textContent;
            listaTarefas.appendChild(titleEl);

            // Listas de tarefas
            const tarefasNaoFeitas = document.createElement('ul');
            const tarefasFeitas = document.createElement('ul');

            const items = xmlDoc.querySelectorAll('item');
            items.forEach(item => {
                const li = document.createElement('li');
                li.textContent = item.textContent;

                // Verificar prioridade
                if (item.getAttribute('prioridade') === 'sim') {
                    li.classList.add('priority');
                }

                // Verificar status
                if (item.getAttribute('feito') === 'sim') {
                    li.classList.add('done');
                    tarefasFeitas.appendChild(li);
                } else {
                    tarefasNaoFeitas.appendChild(li);
                }
            });

            // Adicionar listas ao artigo
            if (tarefasNaoFeitas.children.length > 0) {
                const tituloNaoFeitas = document.createElement('h3');
                tituloNaoFeitas.textContent = 'Tarefas Pendentes';
                listaTarefas.appendChild(tituloNaoFeitas);
                listaTarefas.appendChild(tarefasNaoFeitas);
            }

            if (tarefasFeitas.children.length > 0) {
                const tituloFeitas = document.createElement('h3');
                tituloFeitas.textContent = 'Tarefas Concluídas';
                listaTarefas.appendChild(tituloFeitas);
                listaTarefas.appendChild(tarefasFeitas);
            }
        });
    }

    // Carregar menu inicial
    carregarMenu();
});