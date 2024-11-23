<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <style>
        label {
            display: block;
        }

        fieldset {
            margin: 2em 1em;
        }
    </style>

</head>
<body>



<form action="resultados" method="get">
    <h1>Prova</h1>
    <label>Aluno <input type="text" placeholder="Infome seu nome" name="nomeAluno"></label>
    <br><br>

    <fieldset>
        <legend>01) Quais dos seguintes animais são mamíferos</legend>
        <select size="8" multiple="" name="opcoesMamiferos">
            <option value="tubarao">Tubarão</option>
            <option value="golfinho">Golfinho</option>
            <option value="tartaruga">Tartaruga</option>
            <option value="onca">Onça</option>
            <option value="sapo">Sapo</option>
            <option value="largato">Largato</option>
            <option value="mico-leao">Mico Leão</option>
            <option value="garca">Garça</option>
        </select>
    </fieldset>
    <fieldset>
        <legend>02) Selecione todos os itens que sejam software</legend>
        <label><input type="checkbox" name="software" value="sistema operacional">Sistema Operacional</label>
        <label><input type="checkbox" name="software" value="memmoria ram">Memôria RAM</label>
        <label><input type="checkbox" name="software" value="gpu">GPU</label>
        <label><input type="checkbox" name="software" value="planilha eletronica">Planilha Eletrônica</label>
        <label><input type="checkbox" name="software" value="compilador">Compilador</label>
    </fieldset>
    <fieldset>
        <legend>03) Em qual data iniciou a Primeira Guerra Mundial</legend>
        <input type="date" name="data">
    </fieldset>
    <fieldset>
        <legend>04) Qual dos seguintes metais é liquido em temperatura ambiente</legend>
        <label><input type="radio" name="metal" value="cobre">Cobre</label>
        <label><input type="radio" name="metal" value="ferro">Ferro</label>
        <label><input type="radio" name="metal" value="mercurio">Mercúrio</label>
        <label><input type="radio" name="metal" value="ouro">Ouro</label>
    </fieldset>
    <fieldset>
        <legend>05) Qual o resultado da seguinte expressão: 2+4*5-3</legend>
        <input type="number" name="resultadoMatematico">
    </fieldset>

    <input type="submit" value="Enviar">



</form>

</body>
</html>






