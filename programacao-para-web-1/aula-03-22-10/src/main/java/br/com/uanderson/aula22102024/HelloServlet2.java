package br.com.uanderson.aula22102024;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "helloServlet2", value = "/resultados2")
public class HelloServlet2 extends HttpServlet {

    private static final double CORRECT_SCORE_ANIMAL = 0.66;
    private static final double WRONG_SCORE_ANIMAL = -0.25;
    private static final double CORRECT_SCORE_SOFTWARE = 0.66;
    private static final double WRONG_SCORE_SOFTWARE = -0.4;
    private static final double CORRECT_SCORE_METAL = 2.0;
    private static final double CORRECT_SCORE_MATH = 2.0;
    private static final double CORRECT_SCORE_DATE = 2.0;
    private static final String CORRECT_DATE = "1914-07-28";
    private static final int CORRECT_MATH_RESULT = 19;

    private static final Set<String> CORRECT_ANIMALS = new HashSet<>(Arrays.asList("golfinho", "onca", "mico-leao"));
    private static final Set<String> CORRECT_SOFTWARES = new HashSet<>(Arrays.asList("sistema operacional", "planilha eletronica", "compilador"));
    private static final String CORRECT_METAL = "mercurio";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String nomeAluno = request.getParameter("nomeAluno");
        String data = request.getParameter("data");
        String[] opcoesMamiferos = request.getParameterValues("opcoesMamiferos");
        String[] softwares = request.getParameterValues("software");
        String[] metals = request.getParameterValues("metal");
        String resultadoMatematicoStr = request.getParameter("resultadoMatematico");

        double somaQuestionOne = Math.round(calculateAnimalScore(opcoesMamiferos));
        double somaQuestionTwo = Math.round(calculateSoftwareScore(softwares));
        double somaQuestionThree = calculateDateScore(data);
        double somaQuestionFour = calculateMetalScore(metals);
        double somaQuestionFive = calculateMathScore(resultadoMatematicoStr);

        double notaFinal = somaQuestionOne + somaQuestionTwo + somaQuestionThree + somaQuestionFour + somaQuestionFive;

        String resultHTML = generateResultHTML(nomeAluno, notaFinal, somaQuestionOne, somaQuestionTwo, somaQuestionThree, somaQuestionFour, somaQuestionFive);

        out.print(resultHTML);
    }

    private double calculateAnimalScore(String[] opcoesMamiferos) {
        double score = 0;
        if (opcoesMamiferos != null) {
            for (String op : opcoesMamiferos) {
                score += CORRECT_ANIMALS.contains(op.toLowerCase()) ? CORRECT_SCORE_ANIMAL : WRONG_SCORE_ANIMAL;
            }
        }
        return score;
    }

    private double calculateSoftwareScore(String[] softwares) {
        double score = 0;
        if (softwares != null) {
            for (String op : softwares) {
                score += CORRECT_SOFTWARES.contains(op.toLowerCase()) ? CORRECT_SCORE_SOFTWARE : WRONG_SCORE_SOFTWARE;
            }
        }
        return score;
    }

    private double calculateDateScore(String data) {
        return CORRECT_DATE.equals(data) ? CORRECT_SCORE_DATE : 0;
    }

    private double calculateMetalScore(String[] metals) {
        double score = 0;
        if (metals != null) {
            for (String metal : metals) {
                score += CORRECT_METAL.equalsIgnoreCase(metal) ? CORRECT_SCORE_METAL : -0.5;
            }
        }
        return score;
    }

    private double calculateMathScore(String resultadoMatematicoStr) {
        try {
            int resultadoMatematico = Integer.parseInt(resultadoMatematicoStr);
            return resultadoMatematico == CORRECT_MATH_RESULT ? CORRECT_SCORE_MATH : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String generateResultHTML(String nomeAluno, double notaFinal, double somaQuestionOne, double somaQuestionTwo, double somaQuestionThree, double somaQuestionFour, double somaQuestionFive) {
        StringBuilder html = new StringBuilder();

        html.append("<p> Aluno: ").append(nomeAluno != null ? nomeAluno : "N/A").append("</p><br>");
        html.append("<p> Nota obtida: ").append(notaFinal).append("</p><br>");
        html.append(notaFinal >= 6 ? "<p> Parabéns aprovado! </p>" : "<p> Reprovado tente novamente!! </p>");
        html.append("<hr>");
        html.append("<p>").append(somaQuestionOne).append(" </p>");
        html.append("<p>").append(somaQuestionTwo).append(" </p>");
        html.append("<p>").append(somaQuestionThree).append(" </p>");
        html.append("<p>").append(somaQuestionFour).append(" </p>");
        html.append("<p>").append(somaQuestionFive).append(" </p>");

        return html.toString();
    }
}
