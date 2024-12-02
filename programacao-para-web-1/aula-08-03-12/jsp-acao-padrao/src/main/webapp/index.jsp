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
	

	
</body>
</html>