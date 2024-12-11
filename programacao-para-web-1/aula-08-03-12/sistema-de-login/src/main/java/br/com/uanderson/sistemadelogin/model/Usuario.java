package br.com.uanderson.sistemadelogin.model;

import java.util.List;
import java.util.Objects;

public class Usuario {
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private Boolean admin;
    private List<Telefone> telefones;


    public Usuario() {}

    public Usuario(Long id, String nome, String login, String senha) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Usuario(Long id, String nome, String login, String senha, Boolean admin, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.admin = admin;
        this.telefones = telefones;
    }

    public boolean isAdmin() {
       if (admin == null || admin.equals(false)){
           return false;
       }
       return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", admin=" + admin +
                ", telefones=" + telefones +
                '}';
    }
}//class
