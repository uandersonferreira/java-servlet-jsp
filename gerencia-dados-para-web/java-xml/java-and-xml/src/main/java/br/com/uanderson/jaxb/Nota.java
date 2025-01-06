package br.com.uanderson.jaxb;


import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "nota")
@XmlAccessorType(XmlAccessType.FIELD)
public class Nota {

    @XmlAttribute(name = "carta")
    private String carta;

    @XmlElement(name = "de")
    private String de;

    @XmlElement(name = "para")
    private String para;

    @XmlElement(name = "cabecalho")
    private String cabecalho;

    @XmlElement(name = "corpo")
    private String corpo;

    // Getters and Setters
    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }
}
