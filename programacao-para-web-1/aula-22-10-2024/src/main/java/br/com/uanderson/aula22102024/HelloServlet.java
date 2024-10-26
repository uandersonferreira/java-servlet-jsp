package br.com.uanderson.aula22102024;

import java.io.*;
import java.sql.Date;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/resultados")
public class HelloServlet extends HttpServlet {

    //http://localhost:8080/aula_22_10_2024_war_exploded/?opcoesMamiferos=tubarao&opcoesMamiferos=golfinho&software=sistema+operacional&software=planilha+eletronica&software=compilador&data=2024-10-15&metal=mercurio&resultadoMatematico=19
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();


        String nomeAluno = request.getParameter("nomeAluno");
        String[] opcoesMamiferos = request.getParameterValues("opcoesMamiferos");
        String[] softwares = request.getParameterValues("software");
        String data = request.getParameter("data");
        Date date = Date.valueOf(data);
        String[] metals = request.getParameterValues("metal");
        int resultadoMatematico = Integer.parseInt(request.getParameter("resultadoMatematico"));

        double notaFinal = 0;

        out.println("<p> Aluno: " + nomeAluno + "</p> <br> ");

        double somaQuestionOne = 0;

        for (String op : opcoesMamiferos){
            if (op.equalsIgnoreCase("golfinho")){
                somaQuestionOne += 0.66;
            } else if (op.equalsIgnoreCase("onca")) {
                somaQuestionOne += 0.66;
            } else if (op.equalsIgnoreCase("mico-leao")){
                somaQuestionOne += 0.66;
            }else {
                somaQuestionOne -= 0.25;
            }
        }

        double somaQuestionTwo = 0;
        for (String op : softwares){
            if (op.equalsIgnoreCase("sistema operacional")){
                somaQuestionTwo += 0.66;
            } else if (op.equalsIgnoreCase("planilha eletronica")) {
                somaQuestionTwo += 0.66;
            } else if (op.equalsIgnoreCase("compilador")){
                somaQuestionTwo += 0.66;
            }else {
                somaQuestionTwo -= 0.4;
            }
        }


        double somaQuestionThree = 0;
        //28 de julho de 1914
        if (data.equals("1914-07-28")){
            somaQuestionThree = 2;
        }

        double somaQuestionFour = 0;
        for (String op : metals){
            if (op.equalsIgnoreCase("mercurio")){
                somaQuestionFour = 2;
            }else {
                somaQuestionFour -= 0.5;
            }
        }

        double somaQuestionFive = 0;
        if (resultadoMatematico == 19){
            somaQuestionFive = 2;
        }

        notaFinal = somaQuestionOne + somaQuestionTwo + somaQuestionThree + somaQuestionFour + somaQuestionFive;

        out.println("<p> Nota obtida: " + notaFinal + "</p> <br> ");

        if (notaFinal >= 6){
            out.println("<p> Parabéns aprovado! </p>");
        }else {
            out.println("<p> Reprovado tente novamente!! </p>");
        }

        out.println("<hr>");

        out.println("<p>" +  somaQuestionOne + " </p>");
        out.println("<p>" +  somaQuestionTwo + " </p>");
        out.println("<p>" +  somaQuestionThree + " </p>");
        out.println("<p>" +  somaQuestionFour + " </p>");
        out.println("<p>" +  somaQuestionFive + " </p>");




    }


}//class