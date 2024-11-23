package br.com.uanderson.agendamvc.model;

import java.util.Objects;

public class Contato {
    private int codigo;
    private String nome;
    private String telefone;


    public Contato() {}

    public Contato(String nome, String telefone) { // constructor for create new contato
        this.nome = nome;
        this.telefone = telefone;
    }

    public Contato(int codigo, String nome, String telefone) { // constructor for edit contato
        this.nome = nome;
        this.telefone = telefone;
        this.codigo = codigo;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return codigo == contato.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        //create toString personalizado para o objeto Contato
        return "codigo: " + codigo + ", nome: " + nome + ", telefone: " + telefone + "\n";


    }
}//class
