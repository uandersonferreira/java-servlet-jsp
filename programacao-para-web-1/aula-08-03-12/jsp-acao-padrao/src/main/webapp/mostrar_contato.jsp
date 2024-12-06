<%@ page import = "br.com.uanderson.jspacaopadrao.modelo.Contato" %>
<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<html>
  <head>
    <title>Mostrar Contatos</title>
   <link rel = "stylesheet" href = "css/mostrar_contato.css">
  </head>
  
<body>
    <h1>Teste: JSP:useBeans + Property </h1>
<%--
    <jsp:setProperty name = "contato" property = "*"/>
    Só funciona porque o nome do atributo do objeto Contato é igual ao nome do campo do formulário
    de cadastro.
--%>
    
    <jsp:useBean id = "contato" class = "br.com.uanderson.jspacaopadrao.modelo.Contato">
        <jsp:setProperty name = "contato" property = "*"/>
    </jsp:useBean>

    <a href = "cadastrar_contato.html">Cadastrar</a>

<table>
    <tr>
        <th>Código</th>
        <th>Nome</th>
        <th>Telefone</th>
    </tr>
    <tr>
      <th> <%= contato.getCodigo() %> </th>
      <th> <%= contato.getNome() %> </th>
      <th> <%= contato.getFone() %> </th>
    </tr>

</table>
  
  </body>
</html>
