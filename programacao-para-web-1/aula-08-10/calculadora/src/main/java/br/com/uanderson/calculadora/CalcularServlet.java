package br.com.uanderson.calculadora;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;

@WebServlet(name = "calculadoraServlet", value = "/calcular")
public class CalcularServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        //?n1=1&n2=2&operacao=somar
        request.getParameter("n1");
        request.getParameter("n2");

        double num1 = Double.parseDouble(request.getParameter("n1"));
        double num2 = Double.parseDouble(request.getParameter("n2"));
        String operacao = request.getParameter("operacao");

        double resultado = 0;

        PrintWriter out = response.getWriter();

        switch (operacao) {
            case "somar":
                resultado = num1 + num2;
                break;
            case "subtrair":
                resultado = num1 - num2;
                break;
            case "multiplicar":
                resultado = num1 * num2;
                break;
            case "dividir":
                resultado = num1 / num2;
                break;
            default:
                out.println("<h1>Operação inválida!</h1>");
                return;
        }

        out.println("<h1>Resultado "+ formatNumber(resultado) + " </h1>");



    }

    private String formatNumber(double resultado) {
        Locale localeBR = new Locale("pt","BR");
        NumberFormat numberFormat = NumberFormat.getInstance(localeBR);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(resultado);
    }


}//class
