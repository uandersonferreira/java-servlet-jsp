package br.com.uanderson.jsonP.exemplo01.processor;

import br.com.uanderson.jsonP.exemplo01.model.Endereco;
import br.com.uanderson.jsonP.exemplo01.model.Pessoa;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.io.StringReader;
import java.io.StringWriter;

public class JsonPessoaManager {
    // VAMOR TER DOIS METODOS
    // 1° - Recebe uma pessoa e retorna a String no formato JSON
    // 2° - Recebe uma String no formato JSON e retorna uma Pessoa

    public String pessoaToJson(Pessoa pessoa) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("nome", pessoa.getNome())
                .add("idade", pessoa.getIdade())
                .add("email", pessoa.getEmail())
                .add("endereco", Json.createObjectBuilder()
                        .add("rua", pessoa.getEndereco().getRua())
                        .add("cidade", pessoa.getEndereco().getCidade())
                        .add("cep", pessoa.getEndereco().getCep())
                        .build())
                .build();

        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonObject);
        }
        return stringWriter.toString();
    }

    public Pessoa jsonToPessoa(String jsonString) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = reader.readObject();
            Pessoa pessoa = new Pessoa.PessoaBuilder()
                    .nome(jsonObject.getString("nome"))
                    .idade(jsonObject.getInt("idade"))
                    .email(jsonObject.getString("email"))
                    .build();

            JsonObject enderecoJsonObject = jsonObject.getJsonObject("endereco");
            Endereco endereco = new Endereco.EnderecoBuilder()
                    .rua(enderecoJsonObject.getString("rua"))
                    .cidade(enderecoJsonObject.getString("cidade"))
                    .cep(enderecoJsonObject.getString("cep"))
                    .build();

            pessoa.setEndereco(endereco);

            return pessoa;
        }
    }

    public static void main(String[] args) {
        Endereco endereco = new Endereco.EnderecoBuilder()
                .rua("Rua 10")
                .cidade("Palmas")
                .cep("77777-777")
                .build();

        Pessoa pessoa = new Pessoa.PessoaBuilder()
                .nome("Pedro")
                .idade(20)
                .email("pedro@gmail.com")
                .endereco(endereco)
                .build();

        JsonPessoaManager jsonPessoaManager = new JsonPessoaManager();
        String pessoaToJson = jsonPessoaManager.pessoaToJson(pessoa);
        System.out.println(pessoaToJson);
        System.out.println("============= JSON FOR PESSOA ===============");
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

        // Para versões do java 8, 11 (pois não suportam text block)
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


        Pessoa pessoa1 = jsonPessoaManager.jsonToPessoa(jsonString);
        Pessoa pessoa2 = jsonPessoaManager.jsonToPessoa(jsonStringComScape);
        System.out.println(pessoa1);
        System.out.println(pessoa2);

    }


}//class
