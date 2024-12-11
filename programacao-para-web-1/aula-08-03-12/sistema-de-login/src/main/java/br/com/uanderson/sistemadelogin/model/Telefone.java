package br.com.uanderson.sistemadelogin.model;

import java.util.Objects;

public class Telefone {
    private Long id;
    private String tipo;
    private String numero;

    public Telefone() {
    }

    public Telefone(Long id, String tipo, String numero) {
        this.id = id;
        this.tipo = tipo;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return id == telefone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}//class
