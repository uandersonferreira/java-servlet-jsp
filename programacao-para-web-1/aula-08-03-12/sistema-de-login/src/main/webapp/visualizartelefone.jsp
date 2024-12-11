<%@ page import = "java.util.List" %>
<%@ page import = "br.com.uanderson.sistemadelogin.model.Usuario" %><%@ page
    import = "br.com.uanderson.sistemadelogin.model.Telefone" %>
<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>

<%@include file = "WEB-INF/cabecalho.jsp" %>

<div class = "container">
    <h2>Lista de Telefones</h2>
    <table class = "user-table">
        <thead>
            <tr>
                <th>Id</th>
                <th>Tipo</th>
                <th>Numero</th>
                <th>Acoes</th>
            </tr>
        </thead>
        <tbody>
            <%
              response.setCharacterEncoding("UTF-8");
              Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
              if (usuarioLogado == null){
                response.sendRedirect("index.jsp?mensagem=erro-session-invalidate");
                return;
              }
              
              List<Telefone> telefones = usuarioLogado.getTelefones();
              
              
              for (Telefone t : telefones) {
            %>
                <tr>
                    <td><%= t.getId() %></td>
                    <td><%= t.getTipo() %></td>
                    <td><%= t.getNumero() %></td>
                    
                    <td>
                        <form action = "deletartelefone" method = "post" class = "action-form">
                            <input type = "hidden" name = "idTelefone" value = "<%= t.getId() %>">
                            <input type = "hidden" name = "idUsuario" value = "<%= usuarioLogado.getId() %>">
                          <button type = "submit" class = "btn btn-delete">Deletar</button>
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
