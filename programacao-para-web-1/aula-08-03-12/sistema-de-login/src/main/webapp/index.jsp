<%@ page contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@include file="WEB-INF/cabecalho.jsp"%>

    <h1>Sistema de Login</h1>

    <fieldset>
        <legend>Cadastro</legend>
        <form action="cadastrarusuario" method="post">
            <input type="text" name="nome" placeholder="Nome">
            <input type="text" name="login" placeholder="Login">
            <input type="password" name="senha" placeholder="Senha">
            <input type="submit" value="Cadastrar">
        </form>
    </fieldset>

    <fieldset>
        <legend>Login</legend>
        <form action="login" method="post">
            <input type="text" name="login" placeholder="Login">
            <input type="password" name="senha" placeholder="Senha">
            <input type="submit" value="Logar">
        </form>
    </fieldset>

<%@include file="WEB-INF/rodape.jsp"%>