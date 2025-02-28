### Como Trabalhar com Java API for JSON Processing na Prática

Olá, meu nome é Uanderson, e estou trilhando minha jornada como desenvolvedor Backend Java. Se você é um desenvolvedor
júnior e quer entender como manipular JSON de forma eficiente no Java, está no lugar certo! Hoje vamos desvendar os
segredos do Java API for JSON Processing (ou simplesmente **Jakarta JSON-P**) e entender como aplicá-lo em projetos
reais para trabalhar bem em equipe e construir APIs robustas.

---

### O que é JSON e por que ele é tão importante?

Antes de mergulharmos no código, vamos entender rapidamente o que é JSON. Pense no JSON como uma espécie de planilha
organizada que seu código pode ler e escrever com facilidade. Ele é o formato padrão para troca de informações entre
sistemas hoje em dia. APIs REST, configuração de aplicações, integração entre serviços – tudo usa JSON!

Se você pretende trabalhar em projetos reais, dominar JSON no Java não é opcional, é essencial. E é aqui que entra a *
*Java API for JSON Processing**.

---

### O que é a Java API for JSON Processing?

O Java API for JSON Processing (JSON-P) é uma API oficial do Java (presente desde a versão 7 com a JSR-353 e depois
evoluída no Jakarta EE) que permite:

- Criar e manipular JSON de forma programática
- Ler e escrever JSON com Streams (ideal para alto desempenho)
- Trabalhar com modelos de objetos JSON (útil para manipulação mais simples)

O grande diferencial dessa API é que ela é **leve e integrada ao próprio Java**, sem precisar de bibliotecas externas
como Jackson ou Gson.

---

### Criando e Manipulando JSON com JSON-P

> DOCUMENTATION: https://docs.oracle.com/javaee/7/tutorial/jsonp002.htm#BABDFHHD

Agora vamos para a parte prática! Primeiro, precisamos adicionar a dependência, caso esteja usando um projeto Maven:

```xml

<dependency>
    <groupId>jakarta.json</groupId>
    <artifactId>jakarta.json-api</artifactId>
    <version>2.0.1</version>
</dependency>

<dependency>
<groupId>org.glassfish</groupId>
<artifactId>jakarta.json</artifactId>
<version>2.0.1</version>
</dependency>
```

Agora, vamos criar um JSON na mão, como se estivéssemos escrevendo um e-mail bem estruturado:

```java
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class JsonExample {
    public static void main(String[] args) {
        JsonObject person = Json.createObjectBuilder()
                .add("nome", "João Silva")
                .add("idade", 25)
                .add("email", "joao.silva@email.com")
                .add("endereco", Json.createObjectBuilder()
                        .add("rua", "Rua das Flores")
                        .add("cidade", "São Paulo")
                        .add("cep", "01010-010")
                        .build())
                .build();

        System.out.println(person);
    }
}
```

Resultado:

```json
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
```

Repare que usamos **Json.createObjectBuilder()** para montar nosso JSON como se estivéssemos montando um Lego, peça por
peça.

---

### Lendo JSON com JSON-P

Agora, imagine que recebemos esse JSON de uma API. Como fazemos para ler seus valores? Veja só:

```java
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;

public class JsonReaderExample {
    public static void main(String[] args) {
        String jsonString = "{" nome ":" João Silva "," idade ":25," email ":" joao.silva @email.com "}";

        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();
        reader.close();

        String nome = jsonObject.getString("nome");
        int idade = jsonObject.getInt("idade");

        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
    }
}
```

Esse código lê o JSON e extrai os valores usando métodos como **getString()** e **getInt()**. Bem simples, né?

---

### Utilizando JSON-P no Modelo de Streaming

Se estiver lidando com grandes volumes de dados, a API JSON-P permite trabalhar com **Streaming**, o que significa que
podemos processar o JSON em partes sem carregá-lo completamente na memória.

Para escrever JSON de forma eficiente usando Streaming:

```java
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;

public class JsonStreamingExample {
    public static void main(String[] args) {
        StringWriter writer = new StringWriter();
        JsonGenerator generator = Json.createGenerator(writer);

        generator.writeStartObject()
                .write("nome", "Ana Souza")
                .write("idade", 30)
                .writeStartObject("endereco")
                .write("cidade", "Rio de Janeiro")
                .write("estado", "RJ")
                .writeEnd()
                .writeEnd()
                .close();

        System.out.println(writer.toString());
    }
}
```

E para ler JSON de maneira eficiente usando Streaming:

```java
import jakarta.json.stream.JsonParser;

import java.io.StringReader;

public class JsonStreamingReader {
    public static void main(String[] args) {
        String jsonString = "{" nome ":" Ana Souza "," idade ":30," endereco ":{" cidade ":" Rio de Janeiro "," estado
        ":" RJ "}}";
        JsonParser parser = Json.createParser(new StringReader(jsonString));

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            System.out.println("Evento: " + event);
        }
    }
}
```

Esse modelo é ideal quando lidamos com **grandes arquivos JSON ou streams de dados**, pois evita sobrecarregar a memória
do sistema.

---

### Trabalhando com JSON de forma eficiente em projetos reais

Agora que você já sabe criar e ler JSON, é importante pensar em boas práticas para usá-lo em projetos reais:

1. **Evite criar JSON manualmente se estiver lidando com muitos objetos**
    - Em projetos grandes, bibliotecas como **Jackson** podem simplificar a conversão entre objetos Java e JSON.

2. **Use Streams para grandes volumes de dados**
    - Se estiver processando arquivos JSON enormes, o uso de streams pode evitar problemas de consumo excessivo de
      memória.

3. **Valide JSON antes de processá-lo**
    - JSON malformado pode quebrar sua aplicação, então sempre valide os dados antes de usá-los.

4. **Facilite a manutenção do código**
    - Comente seu código e crie métodos bem organizados para evitar confusão ao manipular JSON.

---

### Conclusão

Dominar a Java API for JSON Processing vai te ajudar a criar APIs robustas, trabalhar melhor em equipe e entender como
manipular dados estruturados em projetos reais. Comece brincando com JSON em pequenos projetos e logo estará aplicando
isso no dia a dia de trabalho.

Agora é sua vez! Teste os códigos, explore outras funcionalidades da API e continue sua jornada como desenvolvedor Java!

Se tiver dúvidas, me chama nos comentários! 🚀

---
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

----

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

---

Com esse passo a passo, você poderá implementar a conversão de JSON para qualquer objeto de forma eficiente e segura,
seja usando **Tree Model** ou **Streaming**! 🚀