<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Aula JSTL</title>
</head>
<body>
    
    <h1>Exemplos de Uso do JSTL Core</h1>
    
     <h2>Tag c:out</h2>
    <p>Mensagem: <c:out value = "Olá mundo, dentro da tag html p"/></p>
     <c:out value = "<p>Olá mundo, com escapeXML = true </p>" escapeXml = "true"/>
     <c:out value = "<p>Olá mundo, com escapeXML = false</p>" escapeXml = "false"/>
    
    <h2>Tag c:set</h2>
    <c:set var = "nome" value = "João" scope = "request"/>
    <p>Variável 'nome': <c:out value = "${nome}"/> </p
     <hr>
    
    <h2>Tag c:if</h2>
    <c:if test = "${param.nome != null}">
        Bem-vindo, ${param.nome}!
    </c:if>
    
    <h2>Tag c:choose, c:when, c:otherwise</h2>
    <c:choose>
        <c:when test = "${param.idade > 18}"> <!-- case 1:   -->
            <p>Você é maior de idade, têm ${param.idade}</p>
        </c:when>
        
        <c:when test = "${param.idade < 0}">
            <p>Você é um alien, uma espécie rara que ainda não existe :) </p>
        </c:when>
        
        <c:otherwise>   <!-- conteúdo padrão - default do switch case -->
                <p>Você é menor de idade, têm ${param.idade}</p>
        </c:otherwise>
    </c:choose>
        <hr>

    <h2>Tag c:forEach</h2>
    <c:forEach var = "i" begin = "1" end = "10" step = "2">
        <p>Número: ${i}</p>
    </c:forEach>
    
<%--  form de Estados and show with JSTL --%>
    <h2>Form de Estados</h2>
    <form action = "index.jsp" method = "get">
        <label for = "estado">Estado:</label>
        <select name = "estados" id = "estado" multiple>
            <option value = "SP">São Paulo</option>
            <option value = "RJ">Rio de Janeiro</option>
            <option value = "MG">Minas Gerais</option>
            <option value = "ES">Espírito Santo</option>
            <option value = "RS">Rio Grande do Sul</option>
        </select>
        <input type = "submit" value = "Enviar">
    </form>
    
    <h3>Estados Selecionados:</h3>
    <c:forEach var = "estado" items = "${paramValues.estados}">
        ${estado} |
    </c:forEach>
    
    <hr>
    <h2>Tag c:forTokens para interar sobre strings</h2>
    <c:forTokens var = "num" items = "1,2,3,4,5" delims = ",">
        <p>Número: ${num}</p>
    </c:forTokens>
    <hr>
    <h2>Tag c:forTokens para interar sobre strings</h2>
    <c:set var = "frutas" value = "maçã,banana,laranja,uva,manga |bebidas de maça|bebidade de banana|bebidas de manga"/>
    <c:forTokens var = "fruta" items = "${frutas}" delims = ",|">
        <p>Fruta: ${fruta}</p>
    </c:forTokens>
    
    <hr>
    <h2>Tag c:catch para tratar erros da página</h2>
    <c:catch var = "error">
        <%= 10 / 0 %>
    </c:catch>
    <c:if test = "${error != null}">
        <p>Ocorreu um erro: ${error}</p>
   </c:if>
    <hr>
    
    <h2>Tag c:import </h2>
    <%--  carrega os dadso da página e mostra na página que o importou--%>
    <c:import url = "https://uandersonferreira.github.io/projetos-web-html-css-js/projetos-youtube/projeto01/"/>
    <hr>
    <%--  carrega os dadso da página e guarda numa variavel para poder usar/chamar na onde quiser --%>
    <c:import url = "outraPagina.jsp" var = "outraPagina">
        <c:param name = "nome" value = "João"/>
    </c:import>
    ${outraPagina}
    <hr>
    
        <%
	        //Somente um exemplo, não usado diretamente na pagina jsp
	        session.setAttribute("dados", "Meus dados secretos -> ********");
        %>
        <h2>Tag c:url com Session</h2>
        <p>
            Quando os cookies estão desabilitados, o identificador de sessão (JSESSIONID) será adicionado à URL automaticamente:
        </p>
        <p>
            Link para outra página (com identificador de sessão embutido na URL):
            <a href = "<c:url value = "mostraSession.jsp"/>">Mostrar dados da session</a>
        
        </p>
    <hr>
    
    <h2>Tag c:redirect</h2>
<%--    <c:redirect url = "outraPagina.jsp"/>--%>
    <hr>
    
    
    
    
    
    
    
    
    
    
</body>
</html>