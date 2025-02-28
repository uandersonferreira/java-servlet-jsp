package br.com.uanderson.jsonP.exemplo02;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Classe responsável por converter objetos da classe Pessoa para JSON e vice-versa
 * utilizando a API JSON-P (Jakarta JSON Processing).
 */
public class JsonPPessoaManager {

    /**
     * Converte um objeto Pessoa para uma string JSON formatada.
     *
     * @param pessoa Objeto Pessoa a ser convertido
     * @return String JSON representando o objeto Pessoa
     */
    public String pessoaToJson(Pessoa pessoa) {
        // Criando um objeto JSON utilizando JsonObjectBuilder
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("nome", pessoa.getNome())  // Adiciona o nome ao JSON
                .add("idade", pessoa.getIdade()) // Adiciona a idade ao JSON
                .build(); // Finaliza a construção do objeto JSON

        // Usa StringWriter para armazenar o JSON em formato de string
        StringWriter stringWriter = new StringWriter();

        // Usando JsonWriter para escrever o JSON formatado
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonObject); // Escreve o JSON no StringWriter
        }
        return stringWriter.toString(); // Retorna a string JSON gerada
    }

    /**
     * Converte uma string JSON para um objeto Pessoa.
     *
     * @param jsonString String JSON representando um objeto Pessoa
     * @return Objeto Pessoa desserializado
     */
    public Pessoa jsonToPessoa(String jsonString) {
        // Usando JsonReader para ler a string JSON e convertê-la em JsonObject
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = reader.readObject();
            // Criando um novo objeto Pessoa a partir dos valores extraídos do JSON
            return new Pessoa(jsonObject.getString("nome"), jsonObject.getInt("idade"));
        }
    }

    public static void main(String[] args) {
        // Criando um objeto Pessoa para teste
        Pessoa pessoa = new Pessoa("Uanderson", 22);

        // Criando uma instância da classe JsonPPessoaManager para manipulação do JSON
        JsonPPessoaManager jsonPPessoaManager = new JsonPPessoaManager();

        // Convertendo o objeto Pessoa para uma string JSON
        String pessoaToJson = jsonPPessoaManager.pessoaToJson(pessoa);
        System.out.println("Objeto Pessoa convertido para JSON:");
        System.out.println(pessoaToJson);

        System.out.println("============= JSON PARA OBJETO PESSOA ===============");

        // Exemplo de uma string JSON válida para teste de desserialização
        String jsonString = """
                {
                    "nome": "João Silva",
                    "idade": 25
                }
                """;

        // Para versões do Java 8 e 11 que não suportam text blocks (String multi-linha)
        String jsonStringComEscape = String.join("\n",
                "{",
                "    \"nome\": \"João Silva\",",
                "    \"idade\": 25",
                "}"
        );

        // Convertendo JSON para objetos Pessoa
        Pessoa pessoa1 = jsonPPessoaManager.jsonToPessoa(jsonString);
        Pessoa pessoa2 = jsonPPessoaManager.jsonToPessoa(jsonStringComEscape);

        // Exibindo os objetos Pessoa criados a partir do JSON
        System.out.println("Objeto Pessoa desserializado do JSON:");
        System.out.println(pessoa1);
        System.out.println(pessoa2);
    }
}
