<%@ page import="br.com.uanderson.agendamvc.model.Agenda" %>
<%@ page import="br.com.uanderson.agendamvc.model.Contato" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listar Agenda</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }

        h1 {
            color: #333;
            text-align: center;
        }

        a {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        a:hover {
            background-color: #45a049;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        p {
            text-align: center;
            color: #666;
        }

        /* Estilo CSS para a classe de erro */
        .error-message {
            background-color: #f8d7da; /* Vermelho claro */
            color: #842029; /* Vermelho escuro para texto */
            padding: 15px; /* Espaçamento interno */
            margin-bottom: 20px; /* Espaçamento inferior */
            border: 1px solid #f5c2c7; /* Borda levemente mais escura */
            border-radius: 5px; /* Cantos arredondados */
            font-size: 16px; /* Tamanho da fonte */
            font-weight: bold; /* Texto em negrito */
            display: flex; /* Facilita alinhamento interno */
            align-items: center; /* Alinha texto e ícones */
            gap: 10px; /* Espaço entre elementos */
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Sombra leve */
            transition: opacity 0.5s ease-in-out; /* Suaviza mudanças de opacidade */
        }

        /* Adicione um ícone de alerta (opcional) */
        .error-message::before {
            content: '⚠️'; /* Ícone ou emoji */
            font-size: 20px; /* Tamanho do ícone */
        }

        .update-message {
            background-color: #d4edda;
            color: #155724;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #c3e6cb;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: opacity 0.5s ease-in-out;
        }

        .update-message::before {
            content: '✅';
            font-size: 20px;
        }

        .create-message {
            background-color: #cce5ff;
            color: #004085;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #b8daff;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: opacity 0.5s ease-in-out;
        }

        .create-message::before {
            content: '🆕';
            font-size: 20px;
        }

        .delete-message {
            background-color: #fff3cd;
            color: #856404;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #ffeeba;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: opacity 0.5s ease-in-out;
        }

        .delete-message::before {
            content: '🗑️';
            font-size: 20px;
        }
        .create-error-message {
            background-color: #ffe6e6;
            color: #cc0000;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #ffcccc;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: opacity 0.5s ease-in-out;
        }

        .create-error-message::before {
            content: '❌';
            font-size: 20px;
        }

        .delete-error-message {
            background-color: #fff2f2;
            color: #990000;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #ffb3b3;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: opacity 0.5s ease-in-out;
        }

        .delete-error-message::before {
            content: '⚠️'
            font-size: 20px;
        }


    </style>
</head>

<body>
<h1>Listar Agenda</h1>
<%
    // Recupera os parâmetros da request
    String mensagem = request.getParameter("mensagem");

    // Define um mapa de mensagens com seus textos e tipos (classe CSS)
    Map<String, String[]> mensagens = new HashMap<>();
    mensagens.put("naoexiste", new String[]{"Contato não existe na Agenda!", "error-message"});
    mensagens.put("sucesso-update", new String[]{"Contato atualizado com sucesso!", "update-message"});
    mensagens.put("sucesso-create", new String[]{"Contato criado com sucesso!", "create-message"});
    mensagens.put("sucesso-delete", new String[]{"Contato excluído com sucesso!", "delete-message"});
    mensagens.put("erro-create", new String[]{"Erro ao criar o contato!", "create-error-message"});
    mensagens.put("erro-delete", new String[]{"Erro ao excluir o contato!", "delete-error-message"});

    // Define o conteúdo da mensagem a ser exibida
    String mensagemTexto = null;
    String mensagemClasse = null;

    if (mensagem != null && mensagens.containsKey(mensagem)) {
        String[] mensagemInfo = mensagens.get(mensagem);
        mensagemTexto = mensagemInfo[0]; // Primeiro elemento: texto da mensagem
        mensagemClasse = mensagemInfo[1]; // Segundo elemento: classe CSS
    }
%>

<%-- Renderiza a mensagem se existir --%>
<%
    if (mensagemTexto != null && mensagemClasse != null) {
%>
<div class="alert-msg <%= mensagemClasse %>"><%= mensagemTexto %></div>
<%
    }
%>


<a href="cadastro">Cadastrar</a>
<table>
    <tr>
        <th>Código</th>
        <th>Nome</th>
        <th>Telefone</th>
        <th>Editar</th>
        <th>Deletar</th>
    </tr>
    <%
        Agenda agendaContext = (Agenda) application.getAttribute("agendaContext");
        if (agendaContext != null && !agendaContext.getContatos().isEmpty()) {
            for (Contato c : agendaContext.getContatos()) {
                out.println("<tr>");
                out.println("<td>" + c.getCodigo() + "</td>");
                out.println("<td>" + c.getNome() + "</td>");
                out.println("<td>" + c.getTelefone() + "</td>");
                out.println("<td> <a href='editar?codigo=" + c.getCodigo() + "'> Editar </a> </td>");
                out.println("<td> <a href='deletar?codigo=" + c.getCodigo() + "'> Deletar </a> </td>");
                out.println("</tr>");
            }
        } else {
            out.println("<tr><td colspan='3'><p>Lista Vazia!</p></td></tr>");
        }
    %>
</table>

<script>
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
</script>

</body>
</html>
