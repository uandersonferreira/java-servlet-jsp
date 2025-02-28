package br.com.uanderson.jsonB;

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
            Pessoa pessoa = new Pessoa(
                    jsonObject.getString("nome"),
                    jsonObject.getInt("idade")
            );
            return pessoa;
        }
    }

    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa("Uanderson", 22);
        JsonPessoaManager jsonPessoaManager = new JsonPessoaManager();
        String pessoaToJson = jsonPessoaManager.pessoaToJson(pessoa);
        System.out.println(pessoaToJson);
        System.out.println("============= JSON FOR PESSOA ===============");
        String jsonString = """
                {
                    "nome": "João Silva",
                     "idade": 25
                }
                """;

        // Para versões do java 8, 11 (pois não suportam text block)
        String jsonStringComScape = String.join("\n",
                "{",
                "    \"nome\": \"João Silva\",",
                "    \"idade\": 25,",
                "    }",
                "}"
        );

        Pessoa pessoa1 = jsonPessoaManager.jsonToPessoa(jsonString);
        Pessoa pessoa2 = jsonPessoaManager.jsonToPessoa(jsonStringComScape);
        System.out.println(pessoa1);
        System.out.println(pessoa2);

    }


}//class
