<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>JSP - Hello World</title>
	<link rel = "stylesheet" href = "css/style.css">
</head>
<body>

	<h1>Teste JSP:Include</h1>
	
	<jsp:include page = "boas_vindas.jsp">
		<jsp:param name = "nome" value = "Uanderson"/>
	</jsp:include>
	
	<h1>Teste JSP:Forward</h1>
	<%
		String erro = request.getParameter("erro");
        if (erro != null) {
				out.println("<div class=error-msg>" + erro + "</div>");
        }
	%>
	
	<h1>Teste JSP:useBean</h1>
	<article>
		<jsp:useBean id="contato" class="br.com.uanderson.jspacaopadrao.modelo.Contato" />
		<jsp:setProperty name="contato" property="nome" value="Uanderson"/>
		<jsp:setProperty name="contato" property="fone" value="99999-9999"/>
		
		Nome: <jsp:getProperty name="contato" property="nome"/> <br>
		Telefone: <jsp:getProperty name="contato" property="fone"/>
		
	</article>


	
</body>
</html>