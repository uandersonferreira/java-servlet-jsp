package br.com.uanderson.model;

import java.util.Arrays;

public class Aluno {
    private int id;
    private String nome;
    private double[] notas;

    public Aluno(int id, String nome, double[] notas) {
        this.id = id;
        this.nome = nome;
        this.notas = notas;
    }

    public Aluno(String nome, double[] notas) {
        this.nome = nome;
        this.notas = notas;
    }

    public Aluno() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double[] getNotas() {
        return notas;
    }

    public void setNotas(double[] notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + Arrays.toString(notas) +
                '}';
    }
}//class
