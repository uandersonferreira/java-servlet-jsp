<%@ page import = "java.util.List" %>
<%@ page import = "br.com.uanderson.sistemadelogin.model.Usuario" %>
<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>

<%@include file = "WEB-INF/cabecalho.jsp" %>

<div class = "container">
    <h2>Lista de Usuários</h2>
    <table class = "user-table">
        <thead>
            <tr>
                <th>Id</th>
                <th>Nome</th>
                <th>Login</th>
                <th>Senha</th>
                <th>Acoes</th>
            </tr>
        </thead>
        <tbody>
            <%
	            response.setCharacterEncoding("UTF-8");
	            List<Usuario> usuarios = (List<Usuario>) application.getAttribute("usersContext");
	            Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
                if (usuarioLogado == null){
                    response.sendRedirect("index.jsp?mensagem=erro-session-invalidate");
                    return;
                }
	            for (Usuario u : usuarios) {
            %>
                <tr>
                    <td><%= u.getId() %></td>
                    <td><%= u.getNome() %></td>
                    <td><%= u.getLogin() %></td>
                    <td>
                        <% if (u.equals(usuarioLogado)) { %>
                            <div class = "password-wrapper">
                                <input type = "password" id = "senha-<%= u.getId() %>" value = "<%= u.getSenha() %>" readonly>
                                <button type = "button" class = "toggle-password"
                                    onclick = "togglePassword('senha-<%= u.getId() %>', this)">
                                     Mostrar
                                </button>
                            </div>
                        <% } else { %>
                            ****
                        <% } %>
                    </td>
                    
                    <td>
                        <% if (u.equals(usuarioLogado)) { %>
                            <form action = "editar.jsp" class = "action-form" method = "post">
                                <input type = "hidden" name = "id" value = "<%= u.getId() %>">
                                <input type = "hidden" name = "nome" value = "<%= u.getNome() %>">
                                <input type = "hidden" name = "login" value = "<%= u.getLogin()%>">
                                <input type = "hidden" name = "senha"
	                                value = "<%= u.equals(usuarioLogado) ? u.getSenha() : "" %>">
                                <button type = "submit" class = "btn btn-edit">Editar</button>
                            </form>
                        <% } %>
                        
                        <% if (!u.equals(usuarioLogado)) { %>
                             <button class = "btn disabled-action" disabled>Editar</button>
                        <% } %>
                        
                        <form action = "deletarusuario" method = "post" class = "action-form">
                            <input type = "hidden" name = "id" value = "<%= u.getId() %>">
                            <% if (u.equals(usuarioLogado) || usuarioLogado.isAdmin()) { %>
                                <button type = "submit" class = "btn btn-delete">Deletar</button>
                            <% } else { %>
                                 <button class = "btn disabled-action" disabled>Deletar</button>
                            <% } %>
                        </form>
                        
                        <form action = "adicionartelefone.jsp" method = "post" class = "action-form">
                            <input type = "hidden" name = "id" value = "<%= u.getId() %>">
                            <% if (u.equals(usuarioLogado)) { %>
                                <button type = "submit" class = "btn btn-fone">+ Fone</button>
                            <% } else { %>
                                 <button class = "btn disabled-action" disabled>+ Fone</button>
                            <% } %>
                        </form>
                        
                        <form action = "visualizartelefone.jsp" method = "post" class = "action-form">
                            <input type = "hidden" name = "id" value = "<%= u.getId() %>">
                            <% if (u.equals(usuarioLogado)) { %>
                                <button type = "submit" class = "btn btn-fone">Visualizar Fone</button>
                            <% } else { %>
                                 <button class = "btn disabled-action" disabled>Visualizar Fone</button>
                            <% } %>
                        </form>
                        
                        
                        
                    </td>
                </tr>
            <%
	            }
            %>
        </tbody>
    </table>
</div>

<%@include file = "WEB-INF/rodape.jsp" %>
