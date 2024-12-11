<%@include file="WEB-INF/cabecalho.jsp"%>
<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>


<fieldset style="min-height: 100vh">
  <legend>Adicionar Telefone</legend>
  <form action="adicionar-telefone" method="post">
    <input readonly hidden="hidden" type="text" name="id" value="<%= request.getParameter("id")%>">
    
     <label for="tipo">Tipo:</label>
      <select id="tipo" name="tipo">
        <option label="Residencial" value="Residencial">Residencial</option>
        <option label="Trabalho" value="Trabalho">Trabalho</option>
        <option label="Faculdade" value="Faculdade">Faculdade</option>
    </select>
    
    <input type="text" name="numero" placeholder="Número">
    
    <input type="submit" value="Cadastrar">
    
  </form>
</fieldset>

<%@include file="WEB-INF/rodape.jsp"%>