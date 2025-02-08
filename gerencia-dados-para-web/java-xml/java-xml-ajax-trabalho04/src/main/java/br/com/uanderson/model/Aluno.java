package br.com.uanderson.model;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private int idade;
    private List<Double> notas;

    public Aluno(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
        this.notas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public List<Double> getNotas() {
        return notas;
    }

    public void adicionarNota(double nota) {
        this.notas.add(nota);
    }

    public double calcularMedia() {
        if (notas.isEmpty()) return 0;
        return notas.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", notas=" + notas +
                ", media=" + calcularMedia() +
                '}';
    }
}

