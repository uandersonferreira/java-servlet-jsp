package br.com.uanderson.jsonP.exemplo01.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class Pessoa2 {
    private String nome;
    private int idade;
    private String email;
    private Endereco endereco;
    private List<String> telefones;

    // Construtor
    private Pessoa2(String nome, int idade, String email, Endereco endereco, List<String> telefones) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.endereco = endereco;
        this.telefones = telefones;
    }

    public static final class PessoaBuilder {
        private String nome;
        private int idade;
        private String email;
        private Endereco endereco;
        private List<String> telefones;

        public PessoaBuilder() {
        }

        public PessoaBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public PessoaBuilder idade(int idade) {
            this.idade = idade;
            return this;
        }

        public PessoaBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PessoaBuilder endereco(Endereco endereco) {
            this.endereco = endereco;
            return this;
        }

        public PessoaBuilder telefones(List<String> telefones) {
            this.telefones = telefones;
            return this;
        }

        public Pessoa2 build() {
            return new Pessoa2(nome, idade, email, endereco, telefones);
        }
    }

    @Override
    public String toString() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("nome", nome)
                .add("idade", idade)
                .add("email", email)

                // Adicionando telefones com Stream API
                .add("telefones", Json.createArrayBuilder(
                        telefones.stream().map(Json::createValue).toList()
                ))

                // Endereço
                .add("endereco", Json.createObjectBuilder()
                        .add("rua", endereco.getRua())
                        .add("cidade", endereco.getCidade())
                        .add("cep", endereco.getCep())
                        .build())
                .build();

        // Configuração para JSON formatado (Pretty Print)
        Map<String, Object> config = Map.of(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(config);

        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = writerFactory.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonObject);
        }

        return stringWriter.toString();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }
}//class
