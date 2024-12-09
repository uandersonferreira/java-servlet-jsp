<%@include file="WEB-INF/cabecalho.jsp"%>
<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>


<fieldset style="min-height: 100vh">
  <legend>Editar</legend>
  <form action="editarusuario" method="post">
    <input type="text" readonly name="id" value="<%= request.getParameter("id")%>">
    <input type="text" name="nome" placeholder="Nome" value="<%= request.getParameter("nome") %>">
    <input type="text" name="login" placeholder="Login" value="<%= request.getParameter("login") %>">
    <input type="password" name="senha" placeholder="Senha" value="<%= request.getParameter("senha") %>">
    <input type="submit" value="Editar">
  </form>
</fieldset>

<%@include file="WEB-INF/rodape.jsp"%>