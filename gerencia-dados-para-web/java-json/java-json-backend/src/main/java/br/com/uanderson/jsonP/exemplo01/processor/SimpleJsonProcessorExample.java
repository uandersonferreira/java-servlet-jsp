package br.com.uanderson.jsonP.processor;

import jakarta.json.*;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleJsonProcessorExample {

    public static void main(String[] args) {
        // Criando um objeto de exemplo
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria Santos");
        pessoa.setIdade(28);

        List<String> telefones = new ArrayList<>();
        telefones.add("11-99876-5432");
        telefones.add("11-3456-7890");
        pessoa.setTelefones(telefones);

        // 1. Objeto para JSON usando Tree Model
        String jsonTreeModel = pessoaToJsonTreeModel(pessoa);
        System.out.println("JSON (Tree Model):\n" + jsonTreeModel);

        // 2. Objeto para JSON usando Streaming Model
        String jsonStreaming = pessoaToJsonStreaming(pessoa);
        System.out.println("\nJSON (Streaming Model):\n" + jsonStreaming);

        // 3. JSON para objeto usando Tree Model
        Pessoa pessoaFromJsonTree = jsonToPessoaTreeModel(jsonTreeModel);
        System.out.println("\nPessoa convertida (Tree Model): " + pessoaFromJsonTree);

        // 4. JSON para objeto usando Streaming Model
        Pessoa pessoaFromJsonStreaming = jsonToPessoaStreaming(jsonStreaming);
        System.out.println("\nPessoa convertida (Streaming Model): " + pessoaFromJsonStreaming);
    }

    /**
     * Converter Pessoa para JSON usando o Tree Model
     */
    public static String pessoaToJsonTreeModel(Pessoa pessoa) {
        try {
            // 1. Criar JsonObjectBuilder e adicionar atributos
            JsonObjectBuilder builder = Json.createObjectBuilder()
                    .add("nome", pessoa.getNome())
                    .add("idade", pessoa.getIdade());

            // 2. Criar JsonArrayBuilder para a lista de telefones
            JsonArrayBuilder telefonesBuilder = Json.createArrayBuilder();
            for (String telefone : pessoa.getTelefones()) {
                telefonesBuilder.add(telefone);
            }
            builder.add("telefones", telefonesBuilder);

            // 3. Construir o JsonObject final
            JsonObject jsonObject = builder.build();

            // 4. Configurar formatação para saída formatada
            Map<String, Object> config = new HashMap<>();
            config.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(config);

            // 5. Criar StringWriter e JsonWriter
            StringWriter stringWriter = new StringWriter();
            try (JsonWriter jsonWriter = writerFactory.createWriter(stringWriter)) {
                jsonWriter.write(jsonObject);
            }

            // 6. Retornar a string JSON formatada
            return stringWriter.toString();
        } catch (JsonException e) {
            throw new RuntimeException("Erro ao converter pessoa para JSON (Tree Model)", e);
        }
    }

    /**
     * Converter Pessoa para JSON usando o Streaming Model
     */
    public static String pessoaToJsonStreaming(Pessoa pessoa) {
        try {
            // 1. Criar StringWriter para armazenar a saída
            StringWriter stringWriter = new StringWriter();

            // 2. Configurar formatação
            Map<String, Object> config = new HashMap<>();
            config.put(JsonGenerator.PRETTY_PRINTING, true);

            // 3. Criar JsonGenerator
            try (JsonGenerator generator = Json.createGeneratorFactory(config).createGenerator(stringWriter)) {
                // 4. Construir o JSON
                generator.writeStartObject()
                        .write("nome", pessoa.getNome())
                        .write("idade", pessoa.getIdade());

                // 5. Adicionar array de telefones
                generator.writeStartArray("telefones");
                for (String telefone : pessoa.getTelefones()) {
                    generator.write(telefone);
                }
                generator.writeEnd(); // Fim do array

                generator.writeEnd(); // Fim do objeto principal
            }

            // 6. Retornar a string JSON gerada
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter pessoa para JSON (Streaming Model)", e);
        }
    }

    /**
     * Converter JSON para Pessoa usando o Tree Model
     */
    public static Pessoa jsonToPessoaTreeModel(String jsonStr) {
        try {
            // 1. Criar JsonReader para ler a string JSON
            try (JsonReader reader = Json.createReader(new StringReader(jsonStr))) {
                // 2. Converter a string JSON em um JsonObject
                JsonObject jsonObject = reader.readObject();

                // 3. Extrair valores e criar objeto Pessoa
                Pessoa pessoa = new Pessoa();

                // 4. Obter valores simples
                if (jsonObject.containsKey("nome")) {
                    pessoa.setNome(jsonObject.getString("nome"));
                }

                if (jsonObject.containsKey("idade")) {
                    pessoa.setIdade(jsonObject.getInt("idade"));
                }

                // 5. Obter e converter lista de telefones
                if (jsonObject.containsKey("telefones")) {
                    JsonArray telefonesArray = jsonObject.getJsonArray("telefones");
                    List<String> telefones = new ArrayList<>();

                    for (JsonValue telefoneValue : telefonesArray) {
                        telefones.add(telefoneValue.toString().replace("\"", ""));
                    }

                    pessoa.setTelefones(telefones);
                }

                // 6. Retornar o objeto Pessoa
                return pessoa;
            }
        } catch (JsonException e) {
            throw new RuntimeException("Erro ao converter JSON para pessoa (Tree Model)", e);
        }
    }

    /**
     * Converter JSON para Pessoa usando o Streaming Model
     */
    public static Pessoa jsonToPessoaStreaming(String jsonStr) {
        try {
            // 1. Criar StringReader
            StringReader stringReader = new StringReader(jsonStr);

            // 2. Criar JsonParser
            try (JsonParser parser = Json.createParser(stringReader)) {
                // 3. Inicializar variáveis
                Pessoa pessoa = new Pessoa();
                List<String> telefones = new ArrayList<>();

                String chaveAtual = null;
                boolean emTelefones = false;

                // 4. Iterar sobre eventos do parser
                while (parser.hasNext()) {
                    Event evento = parser.next();

                    switch (evento) {
                        case KEY_NAME:
                            chaveAtual = parser.getString();
                            if ("telefones".equals(chaveAtual)) {
                                emTelefones = true;
                            }
                            break;

                        case VALUE_STRING:
                            if (chaveAtual != null) {
                                if (!emTelefones && "nome".equals(chaveAtual)) {
                                    pessoa.setNome(parser.getString());
                                } else if (emTelefones) {
                                    telefones.add(parser.getString());
                                }
                            }
                            break;

                        case VALUE_NUMBER:
                            if (chaveAtual != null && "idade".equals(chaveAtual)) {
                                pessoa.setIdade(parser.getInt());
                            }
                            break;

                        case START_ARRAY:
                            if (chaveAtual != null && "telefones".equals(chaveAtual)) {
                                // Iniciando array de telefones
                                telefones = new ArrayList<>();
                            }
                            break;

                        case END_ARRAY:
                            if (emTelefones) {
                                emTelefones = false;
                                pessoa.setTelefones(telefones);
                            }
                            break;

                        default:
                            break;
                    }
                }

                // 5. Retornar objeto Pessoa
                return pessoa;
            }
        } catch (JsonException e) {
            throw new RuntimeException("Erro ao converter JSON para pessoa (Streaming Model)", e);
        }
    }

    // Classe de modelo
    public static class Pessoa {
        private String nome;
        private int idade;
        private List<String> telefones;

        // Getters e Setters
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

        public List<String> getTelefones() {
            return telefones;
        }

        public void setTelefones(List<String> telefones) {
            this.telefones = telefones;
        }

        @Override
        public String toString() {
            return "Pessoa [nome=" + nome + ", idade=" + idade + ", telefones=" + telefones + "]";
        }
    }
}
/*


### **📌 Passo a Passo para Implementar Conversão de JSON Usando a API JSON-P**

(Independente da Classe/Objeto)

---

## **1️⃣ Convertendo um Objeto para JSON** (`objetoToJson`)

### 🔹 **Usando o Modelo de Objetos (Tree Model)**

1. **Criar um `JsonObjectBuilder`** e adicionar os atributos do objeto.
2. **Se houver listas ou arrays, criar um `JsonArrayBuilder`** e adicionar os elementos.
3. **Se houver objetos aninhados, criar um `JsonObjectBuilder` separado** e adicioná-lo ao principal.
4. **Criar um `JsonObject` final** chamando `.build()`.
5. **Se precisar de saída formatada**, criar um `JsonWriterFactory` com a configuração `PRETTY_PRINTING`.
6. **Criar um `StringWriter` e usar `JsonWriter` para escrever o JSON como string**.
7. **Retornar a string JSON gerada**.

### 🔹 **Usando o Modelo de Streaming (Event-Based Streaming)**

1. **Criar um `StringWriter`** para armazenar a saída.
2. **Criar um `JsonGenerator` a partir de `Json.createGenerator(writer)`**.
3. **Usar métodos como `.writeStartObject()`, `.write()` e `.writeEnd()` para construir o JSON**.
4. **Se houver listas, usar `.writeStartArray()` e `.writeEnd()`** para encapsular os itens.
5. **Fechar o `JsonGenerator` para garantir que todos os dados sejam escritos corretamente**.
6. **Retornar a string JSON gerada**.

---

## **2️⃣ Convertendo um JSON para Objeto** (`jsonToObjeto`)

### 🔹 **Usando o Modelo de Objetos (Tree Model)**

1. **Criar um `JsonReader` para ler a string JSON**.
2. **Converter a string JSON em um `JsonObject`** usando `reader.readObject()`.
3. **Obter os valores individuais usando `jsonObject.getString()`, `jsonObject.getInt()` etc.**
4. **Se houver listas ou arrays, usar `jsonObject.getJsonArray()` e converter para `List<>`**.
5. **Se houver objetos aninhados, usar `jsonObject.getJsonObject()` e extrair os valores**.
6. **Criar e retornar o objeto Java populado com os dados extraídos**.

### 🔹 **Usando o Modelo de Streaming (Event-Based Streaming)**

1. **Criar um `StringReader` para ler o JSON de forma eficiente**.
2. **Criar um `JsonParser` usando `Json.createParser(reader)`**.
3. **Iterar sobre os eventos do `JsonParser`** (como `START_OBJECT`, `KEY_NAME`, `VALUE_STRING`, etc.).
4. **Armazenar os valores lidos em variáveis enquanto percorre o JSON**.
5. **Se encontrar uma lista (`START_ARRAY`), iterar sobre os valores até `END_ARRAY`**.
6. **Se encontrar um objeto aninhado (`START_OBJECT` dentro de outro), fazer parsing recursivo**.
7. **Finalizar a leitura e retornar um objeto Java com os valores extraídos**.

---

## **⚠️ Cuidados Importantes**

✅ **Escolher o modelo certo**:

- **Tree Model** → Melhor para manipulação complexa e fácil de entender.
- **Streaming** → Melhor para arquivos grandes ou quando o desempenho/memória for crítico.

✅ **Fechar os recursos corretamente**:

- `JsonReader`, `JsonWriter`, `JsonParser` e `JsonGenerator` devem ser fechados para evitar vazamentos de memória.

✅ **Verificar a existência de chaves antes de acessá-las** para evitar exceções (`jsonObject.containsKey("campo")`).

✅ **Tratar exceções de parsing (`JsonException`)** para capturar erros em JSONs malformados.

✅ **Lidar com listas corretamente**, garantindo que os dados sejam convertidos corretamente para `List<>` ou arrays.

 */