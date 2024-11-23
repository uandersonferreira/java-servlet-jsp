<%@ page import="br.com.uanderson.agendamvc.model.Agenda" %>
<%@ page import="br.com.uanderson.agendamvc.model.Contato" %>
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

    </style>
</head>
<body>
<h1>Listar Agenda</h1>

<%
    String mensagem = request.getParameter("mensagem");
    String causeerror = request.getParameter("causeerror");

    if (mensagem != null && mensagem.equals("naoexiste")) {
%>
<!-- Divs com classes e IDs apropriados -->
<div class="error-message" >Contato não existe na Agenda!</div>
<div class="error-message" ><%= causeerror %>  </div>
<%
    }
%>

<a href="cadastro">Cadastrar</a>
<table>
    <tr>
        <th>Código</th>
        <th>Nome</th>
        <th>Telefone</th>
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
        const errorMessages = document.querySelectorAll('.error-message');
        errorMessages.forEach((errorMessage) => {
            errorMessage.style.transition = 'opacity 0.5s'; // Suaviza o desaparecimento
            errorMessage.style.opacity = '0'; // Torna o elemento transparente
            setTimeout(() => errorMessage.remove(), 500); // Remove do DOM após transição
        });
    }, 5000); // 5 segundos de espera
</script>

</body>
</html>
