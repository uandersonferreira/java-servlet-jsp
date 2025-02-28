package br.com.uanderson.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.StringReader;
import java.io.StringWriter;

@XmlRootElement(name = "Usuario") // Define um nome personalizado para o elemento raiz no XML
@XmlAccessorType(XmlAccessType.FIELD) // Faz o JAXB usar diretamente os atributos da classe, ignorando os getters
public class Pessoa {

    @XmlElement(name = "nomeCompleto") // Define um nome personalizado para o atributo "nome" no XML
    private String nome;
    private int idade;

    // Construtor padrão exigido pelo JAXB para desserialização
    public Pessoa() {
    }

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }

    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa("Uanderson", 22);

        String stringXml = """
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <Usuario>
                    <nomeCompleto>Uanderson</nomeCompleto>
                    <idade>22</idade>
                </Usuario>
                """;

        System.out.println(objectToXmlWithJaxb(pessoa)); // Serializa objeto para XML
        System.out.println(stringXmlToObjectWithJaxb(stringXml)); // Desserializa XML para objeto

        /*
           O JAXB exige a anotação @XmlRootElement para identificar a classe como um elemento raiz no XML.
           Além disso, é necessário um construtor sem parâmetros para que a desserialização funcione corretamente.

           🔹 COMPORTAMENTO PADRÃO:
           - Se @XmlRootElement não especificar um nome, ele usará o nome da própria classe.
           - Por padrão, a conversão de objeto para XML utiliza os métodos **getters públicos**.
           - Se não houver um getter para um atributo, ele será ignorado na conversão para XML.
           - Caso a lógica dos getters seja alterada, o valor serializado também será alterado.

           🔹 COMPORTAMENTO PERSONALIZADO:
           - @XmlRootElement(name = "Usuario") -> Define um nome personalizado para o elemento raiz no XML.
           - @XmlAccessorType(XmlAccessType.FIELD) -> Faz com que JAXB use diretamente os atributos do objeto,
             ignorando os getters na conversão.
           - @XmlElement(name = "novoNome") -> Permite renomear atributos no XML sem mudar o nome real do campo na classe.
        */
    }

    /**
     * Converte um objeto Pessoa para uma string XML formatada usando JAXB.
     *
     * @param pessoa Objeto Pessoa a ser convertido
     * @return Representação XML do objeto ou null em caso de erro
     */
    private static String objectToXmlWithJaxb(Pessoa pessoa) {
        String stringXml = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Pessoa.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Formatar XML para melhor legibilidade

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(pessoa, stringWriter);
            stringXml = stringWriter.toString(); // Retorna XML formatado
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return stringXml;
    }

    /**
     * Converte uma string XML para um objeto Pessoa usando JAXB.
     *
     * @param stringXml String XML representando um objeto Pessoa
     * @return Objeto Pessoa desserializado ou null em caso de erro
     */
    private static Pessoa stringXmlToObjectWithJaxb(String stringXml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Pessoa.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader stringReader = new StringReader(stringXml);
            return (Pessoa) unmarshaller.unmarshal(stringReader); // Retorna o objeto desserializado
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null; // Retorna null caso ocorra erro
    }
}
