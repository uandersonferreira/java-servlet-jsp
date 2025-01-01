package br.com.uanderson.jaxb.test;

import br.com.uanderson.jaxb.Nota;
import br.com.uanderson.jaxb.NotaReader;

public class Main {
    public static void main(String[] args) {
        String xmlPath = "src/main/java/br/com/uanderson/nota.xml";
        try {
            Nota nota = NotaReader.readNota(xmlPath);
            System.out.println("Carta: " + nota.getCarta());
            System.out.println("De: " + nota.getDe());
            System.out.println("Para: " + nota.getPara());
            System.out.println("Cabecalho: " + nota.getCabecalho());
            System.out.println("Corpo: " + nota.getCorpo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
