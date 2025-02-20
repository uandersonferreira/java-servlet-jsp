package br.com.uanderson.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Noticia implements Serializable {
    private Long id;
    private String titulo;
    private String lide;
    private String corpoNoticia;
    private LocalDateTime dataPublicacao;
    private Reporter reporter; // Muitas noticias para um reporter @ManyToOne

    public Noticia() {}

    public Noticia(Long id, String titulo, String lide, String corpoNoticia, LocalDateTime dataPublicacao, Reporter reporter) {
        this.id = id;
        this.titulo = titulo;
        this.lide = lide;
        this.corpoNoticia = corpoNoticia;
        this.dataPublicacao = dataPublicacao;
        this.reporter = reporter;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLide() {
        return lide;
    }

    public void setLide(String lide) {
        this.lide = lide;
    }

    public String getCorpoNoticia() {
        return corpoNoticia;
    }

    public void setCorpoNoticia(String corpoNoticia) {
        this.corpoNoticia = corpoNoticia;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", lide='" + lide + '\'' +
                ", corpoNoticia='" + corpoNoticia + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", reporter=" + reporter +
                '}';
    }
}//class
