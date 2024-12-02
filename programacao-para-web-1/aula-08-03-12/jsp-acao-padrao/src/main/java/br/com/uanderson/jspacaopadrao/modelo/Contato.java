package br.com.uanderson.jspacaopadrao.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Contato implements Serializable {
    private int codigo;
    private String nome;
    private String fone;

    public Contato() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
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
        return "Contato{" +
                "nome='" + nome + '\'' +
                ", fone='" + fone + '\'' +
                ", codigo=" + codigo +
                '}';
    }
}
