<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>

    <form action="/calcular" method="get">
        <label for="n1">Número 1: </label><br>
        <input type="number" id="n1" name="n1" step="0.1" value="0"><br><br>

        <label for="n2">Número 2: </label><br>
        <input type="number" id="n2" name="n2" step="0.1" value="0"><br><br>

        <input type="radio" id="somar" name="operacao" value="somar">
        <label for="somar"> + SOMAR</label><br>

        <input type="radio" id="subtrair" name="operacao" value="subtrair">
        <label for="subtrair"> - SUBTRAIR</label><br>

        <input type="radio" id="multiplicar" name="operacao" value="multiplicar">
        <label for="multiplicar"> * MULTIPLICAR</label><br>

        <input type="radio" id="dividir" name="operacao" value="dividir">
        <label for="dividir"> / DIVIDIR</label><br>

        <input type="submit" value="Calcular">

    </form>

</body>
</html>