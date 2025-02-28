package br.com.uanderson.jsonP.exemplo01.processor;

import br.com.uanderson.jsonP.exemplo01.model.Endereco;
import br.com.uanderson.jsonP.exemplo01.model.Pessoa;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class JsonPessoaManager2 {

    // Método que converte um objeto Pessoa para uma String JSON formatada
    public String pessoaToJson(Pessoa pessoa) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("nome", pessoa.getNome())
                .add("idade", pessoa.getIdade())
                .add("email", pessoa.getEmail())
                .add("endereco", enderecoToJson(pessoa.getEndereco()))
                .build();

        String json = null;
        try (StringWriter stringWriter = new StringWriter();
             JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonObject);

            json = stringWriter.toString();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    // Método auxiliar para converter um Endereco em JsonObject
    private JsonObject enderecoToJson(Endereco endereco) {
        return Json.createObjectBuilder()
                .add("rua", endereco.getRua())
                .add("cidade", endereco.getCidade())
                .add("cep", endereco.getCep())
                .build();
    }

    // Método que converte uma String JSON para um objeto Pessoa
    public Pessoa jsonToPessoa(String jsonString) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = reader.readObject();
            return criarPessoa(jsonObject);
        }
    }

    // Método auxiliar para criar uma Pessoa a partir de um JsonObject
    private Pessoa criarPessoa(JsonObject jsonObject) {
        Endereco endereco = criarEndereco(jsonObject.getJsonObject("endereco"));

        return new Pessoa.PessoaBuilder()
                .nome(jsonObject.getString("nome"))
                .idade(jsonObject.getInt("idade"))
                .email(jsonObject.getString("email"))
                .endereco(endereco)
                .build();
    }

    // Método auxiliar para criar um Endereco a partir de um JsonObject
    private Endereco criarEndereco(JsonObject enderecoJson) {
        return new Endereco.EnderecoBuilder()
                .rua(enderecoJson.getString("rua"))
                .cidade(enderecoJson.getString("cidade"))
                .cep(enderecoJson.getString("cep"))
                .build();
    }

    // Método de teste
    public static void main(String[] args) {
        // Criando um objeto Endereco
        Endereco endereco = new Endereco.EnderecoBuilder()
                .rua("Rua 10")
                .cidade("Palmas")
                .cep("77777-777")
                .build();

        // Criando um objeto Pessoa
        Pessoa pessoa = new Pessoa.PessoaBuilder()
                .nome("Pedro")
                .idade(20)
                .email("pedro@gmail.com")
                .endereco(endereco)
                .build();

        JsonPessoaManager jsonPessoaManager = new JsonPessoaManager();

        // Convertendo Pessoa para JSON e imprimindo
        String pessoaJson = jsonPessoaManager.pessoaToJson(pessoa);
        System.out.println("========== JSON Gerado ==========");
        System.out.println(pessoaJson);

        // JSON de entrada para teste
        String jsonString = """
                {
                    "nome": "João Silva",
                    "idade": 25,
                    "email": "joao.silva@email.com",
                    "endereco": {
                        "rua": "Rua das Flores",
                        "cidade": "São Paulo",
                        "cep": "01010-010"
                    }
                }
                """;

        // JSON compatível com versões anteriores ao Java 17
        String jsonStringComScape = String.join("\n",
                "{",
                "    \"nome\": \"João Silva\",",
                "    \"idade\": 25,",
                "    \"email\": \"joao.silva@email.com\",",
                "    \"endereco\": {",
                "        \"rua\": \"Rua das Flores\",",
                "        \"cidade\": \"São Paulo\",",
                "        \"cep\": \"01010-010\"",
                "    }",
                "}"
        );

        // Convertendo JSON para Pessoa e imprimindo
        Pessoa pessoa1 = jsonPessoaManager.jsonToPessoa(jsonString);
        Pessoa pessoa2 = jsonPessoaManager.jsonToPessoa(jsonStringComScape);

        System.out.println("========== Pessoa 1 ==========");
        System.out.println(pessoa1);

        System.out.println("========== Pessoa 2 ==========");
        System.out.println(pessoa2);
    }
}
