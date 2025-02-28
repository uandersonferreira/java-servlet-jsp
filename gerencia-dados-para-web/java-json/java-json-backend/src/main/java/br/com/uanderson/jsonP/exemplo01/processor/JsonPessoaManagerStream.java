package br.com.uanderson.jsonP.processor;

import br.com.uanderson.jsonP.model.Endereco;
import br.com.uanderson.jsonP.model.Pessoa2;
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonPessoaManagerStream {

    public String pessoaToJson(Pessoa2 pessoa2) {
        StringWriter stringWriter = new StringWriter();

        try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)) {
            jsonGenerator.writeStartObject()
                    .write("nome", pessoa2.getNome())
                    .write("idade", pessoa2.getIdade())
                    .write("email", pessoa2.getEmail())
                    .writeStartArray("telefones");
            pessoa2.getTelefones().stream().forEach(jsonGenerator::write);
            jsonGenerator.writeEnd()
                    .writeStartObject("endereco")
                    .write("rua", pessoa2.getEndereco().getRua())
                    .write("cidade", pessoa2.getEndereco().getCidade())
                    .write("cep", pessoa2.getEndereco().getCep())
                    .writeEnd()
                    .writeEnd();
        }

        return stringWriter.toString();
    }


    // 🔹 Convertendo JSON para Pessoa2 (Agora captura a lista de telefones)
    public Pessoa2 jsonToPessoa(String jsonString) {
        Pessoa2.PessoaBuilder pessoaBuilder = new Pessoa2.PessoaBuilder();
        Endereco.EnderecoBuilder enderecoBuilder = new Endereco.EnderecoBuilder();
        List<String> telefones = new ArrayList<>();

        try (JsonParser jsonParser = Json.createParser(new StringReader(jsonString))) {
            String key = null;
            boolean isReadingTelefones = false;

            while (jsonParser.hasNext()) {
                JsonParser.Event event = jsonParser.next();

                switch (event) {
                    case KEY_NAME -> {
                        key = jsonParser.getString();
                        if ("telefones".equals(key)) {
                            isReadingTelefones = true;
                        }
                    }
                    case VALUE_STRING -> {
                        if (isReadingTelefones) {
                            telefones.add(jsonParser.getString());
                        } else if ("nome".equals(key)) {
                            pessoaBuilder.nome(jsonParser.getString());
                        } else if ("email".equals(key)) {
                            pessoaBuilder.email(jsonParser.getString());
                        } else if ("rua".equals(key)) {
                            enderecoBuilder.rua(jsonParser.getString());
                        } else if ("cidade".equals(key)) {
                            enderecoBuilder.cidade(jsonParser.getString());
                        } else if ("cep".equals(key)) {
                            enderecoBuilder.cep(jsonParser.getString());
                        }
                    }
                    case VALUE_NUMBER -> {
                        if ("idade".equals(key)) {
                            pessoaBuilder.idade(jsonParser.getInt());
                        }
                    }
                    case END_ARRAY -> {
                        if (isReadingTelefones) {
                            isReadingTelefones = false;
                        }
                    }
                }
            }
        }

        return pessoaBuilder.telefones(telefones).endereco(enderecoBuilder.build()).build();
    }

    // 🔹 Testando a conversão
    public static void main(String[] args) {
        Endereco endereco = new Endereco.EnderecoBuilder()
                .rua("Rua 10")
                .cidade("Palmas")
                .cep("77777-777")
                .build();

        List<String> telefones = List.of("99999-1111", "88888-2222");

        Pessoa2 pessoa2 = new Pessoa2.PessoaBuilder()
                .nome("Pedro")
                .idade(20)
                .email("pedro@gmail.com")
                .telefones(telefones)
                .endereco(endereco)
                .build();

        JsonPessoaManagerStream jsonPessoaManagerStream = new JsonPessoaManagerStream();

        // Convertendo Pessoa2 para JSON Streaming
        String pessoaJson = jsonPessoaManagerStream.pessoaToJson(pessoa2);
        System.out.println("========== JSON Gerado ==========");
        System.out.println(pessoaJson);

        // Convertendo JSON para Pessoa2 Streaming
        Pessoa2 pessoaConvertida = jsonPessoaManagerStream.jsonToPessoa(pessoaJson);
        System.out.println("========== Pessoa2 Convertida ==========");
        System.out.println(pessoaConvertida);
    }
}
/*

No Java, a comparação `"idade".equals(key)` tem uma diferença importante em relação a `key.equals("idade")`. Vamos
analisar os dois casos:

---

### **1️⃣ `"idade".equals(key)`** ✅ (Mais Seguro)

- Aqui, a string literal `"idade"` é um **objeto `String` conhecido e nunca será `null`**.
- Se `key` for `null`, **não ocorre exceção**, pois `null` não pode chamar `.equals()`.
- Exemplo seguro:

```java
String key = null;
System.out.

println("idade".equals(key)); // false (não lança exceção)
```

---

### **2️⃣ `key.equals("idade")`** ⚠️ (Pode Causar `NullPointerException`)

- Aqui, `key` é quem chama o método `.equals()`.
- Se `key` for `null`, **lançará uma exceção `NullPointerException`**.
- Exemplo perigoso:

```java
String key = null;
System.out.

println(key.equals("idade")); // ❌ NullPointerException
```

---

### **🔹 Conclusão**

Sempre que houver a possibilidade de a variável ser `null`, a forma **mais segura** é usar
`"valor_fixo".equals(variavel)`, pois **evita exceções** e torna o código mais robusto. 🚀

*/