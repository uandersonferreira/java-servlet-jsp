<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1> <br/>

<h1>Teste JSP:Include</h1>
<jsp:include page = "boas_vindas.jsp">
	<jsp:param name = "nome" value = "Uanderson"/>
</jsp:include>
	
	
	<p>
		lorem ipsum dolor sit amet, consectetur adipiscing elit.
		lorem ipsum dolor sit amet, consectetur adipiscing elit.
		lorem ipsum dolor sit amet, consectetur adipiscing elit.
		lorem ipsum dolor sit amet, consectetur adipiscing elit.
		Donec nec semper
		
	</p>
	
	<button> teste </button>
	
</body>
</html>