**Trabalhando com JDBC API na Prática: Um Guia Simples para Desenvolvedores Júnior**

Olá, eu sou Uanderson, um desenvolvedor Backend Java em formação, e hoje eu quero conversar com você, que está começando
sua jornada como desenvolvedor Júnior. Vamos mergulhar juntos no mundo do JDBC API, entender seus conceitos e aprender
como colocar tudo em prática de forma eficiente e organizada. Meu objetivo aqui é resolver o mistério de como conectar
sua aplicação Java a um banco de dados – uma habilidade essencial para trabalhar em equipe e em projetos reais.

Se você está olhando para o JDBC como quem olha para um manual de micro-ondas em russo, fica tranquilo. Eu também já
estive no seu lugar, e vou te guiar passo a passo, com analogias e práticas simples. Então, bora lá!

---

### **O que é JDBC API, afinal?**

Pensa no JDBC como uma ponte entre o seu código Java e o banco de dados. Ele não é o banco em si, nem os dados, mas o
meio de transporte que faz com que as informações fluam de um lado para o outro. JDBC significa *Java Database
Connectivity*, ou Conectividade com Banco de Dados Java.

Imagine que você é um motorista e o banco de dados é o destino. O JDBC é como uma estrada que conecta você até lá. Sem
essa estrada, você não consegue entregar ou buscar informações no banco.

---

### **O que você precisa para usar JDBC?**

Agora que entendemos o conceito, vamos listar os elementos necessários para trabalhar com JDBC. Pense nisso como montar
um sanduíche: cada ingrediente tem sua função específica, e juntos, criam algo delicioso (ou funcional, no caso do seu
programa).

Aqui estão os ingredientes:

1. **Driver JDBC**  
   O driver é o motor do carro. Ele é um arquivo .jar que traduz as solicitações feitas pelo JDBC em comandos que o
   banco de dados entende. Como estamos usando PostgreSQL, o driver será o `postgresql-<versão>.jar`.

2. **Classe `DriverManager`**  
   É o motorista que encontra o caminho certo para conectar sua aplicação ao banco de dados. Ele gerencia a estrada que
   mencionamos antes.

3. **Interface `Connection`**  
   Uma vez na estrada, você precisa de uma pista para dirigir. A conexão é essa pista. É ela que mantém o link entre sua
   aplicação e o banco de dados.

4. **Interface `Statement` e suas variações (`PreparedStatement` e `CallableStatement`)**  
   Esses são os seus mensageiros. Eles carregam os comandos SQL (como SELECT, INSERT, UPDATE, DELETE) do seu código até
   o banco.

5. **Interface `ResultSet`**  
   É a caixa de correio. Quando você faz uma consulta, o banco retorna os dados, e o `ResultSet` os organiza para que
   você possa acessar e trabalhar com eles.

---

### Vamos explorar em mais detalhes o papel do **`Statement`**, **`PreparedStatement`**, e **`CallableStatement`**,
### e como escolher a melhor opção para diferentes situações. Vou usar uma analogia para tornar isso mais fácil de entender.

---

### **1. `Statement`: O Mensageiro Básico**

Imagine que você quer enviar uma carta genérica sem muitos detalhes. O `Statement` é como um mensageiro que simplesmente
pega o texto da carta (no caso, o SQL) e entrega no destino (o banco de dados).

#### **Características do `Statement`:**

- É usado para executar comandos SQL estáticos, ou seja, você escreve o SQL diretamente no código.
- Funciona bem para operações simples e únicas, como criar tabelas ou consultas rápidas.
- Não é eficiente para consultas repetitivas, pois cada vez que um comando é enviado, o banco precisa analisá-lo e
  planejá-lo do zero.

#### **Exemplo de uso:**

```java
Statement stmt = conexao.createStatement();
String sql = "SELECT * FROM pessoa";
ResultSet rs = stmt.executeQuery(sql);
```

**Problema:** Com o `Statement`, você precisa concatenar valores manualmente no SQL, o que pode abrir brechas para
ataques de *SQL Injection*. Além disso, ele não reutiliza o comando, tornando-o mais lento para consultas repetitivas.

---

### **2. `PreparedStatement`: O Mensageiro Precavido**

Agora imagine que você quer enviar uma carta padrão, mas com espaços para preencher algumas informações específicas. O
`PreparedStatement` é como um mensageiro que pega esse modelo e permite que você preencha os detalhes (os parâmetros)
antes de enviá-lo.

#### **Por que usar `PreparedStatement`?**

- **Segurança:** Ele evita *SQL Injection*, pois os parâmetros são tratados de forma segura pelo driver JDBC.
- **Desempenho:** Quando você usa o mesmo comando várias vezes (com parâmetros diferentes), ele é pré-compilado e
  reutilizado, o que economiza tempo.
- **Flexibilidade:** Permite passar valores dinamicamente, tornando-o mais prático para consultas com parâmetros.

#### **Exemplo de uso:**

Vamos inserir uma pessoa no banco de dados:

```java
String sql = "INSERT INTO pessoa (nome, idade) VALUES (?, ?)";
PreparedStatement stmt = conexao.prepareStatement(sql);
stmt.

setString(1,"João Silva");  // Preenche o primeiro parâmetro (nome)
stmt.

setInt(2,25);               // Preenche o segundo parâmetro (idade)
stmt.

executeUpdate();
```

- **`?`**: Um marcador de posição que será substituído pelos valores reais.
- **`setString` e `setInt`**: Definem os valores dos parâmetros com segurança.

**Vantagem prática:** Se você precisa inserir várias pessoas, pode usar o mesmo `PreparedStatement` várias vezes,
mudando apenas os parâmetros.

---

### **3. `CallableStatement`: O Mensageiro Especializado**

Agora imagine que você precisa fazer uma entrega VIP – algo mais complexo que exige acessar uma área exclusiva, como
uma "sala de controle" do banco. O `CallableStatement` é usado para executar *Stored Procedures* (procedimentos
armazenados), que são blocos de código SQL pré-definidos e armazenados diretamente no banco de dados.

#### **Por que usar `CallableStatement`?**

- **Execução de lógica complexa no banco:** Se você tem lógica de negócios no banco, como cálculos ou manipulação de
  dados em várias tabelas, pode encapsular isso em uma *Stored Procedure*.
- **Desempenho:** Procedimentos armazenados geralmente executam mais rápido, porque já estão compilados no banco.
- **Organização:** Ajuda a centralizar a lógica no banco, reduzindo a complexidade no lado da aplicação.

#### **Exemplo de uso:**

Digamos que há uma *Stored Procedure* chamada `inserir_pessoa` que insere uma pessoa no banco:

```sql
CREATE
OR REPLACE PROCEDURE inserir_pessoa(nome_pessoa VARCHAR, idade_pessoa INT)
AS $$
BEGIN
INSERT INTO pessoa (nome, idade)
VALUES (nome_pessoa, idade_pessoa);
END;
$$
LANGUAGE plpgsql;
```

No Java, você chama essa procedure assim:

```java
CallableStatement stmt = conexao.prepareCall("{CALL inserir_pessoa(?, ?)}");
stmt.

setString(1,"Carlos Santos");  // Primeiro parâmetro
stmt.

setInt(2,40);                  // Segundo parâmetro
stmt.

execute();
```

- **`{CALL ...}`**: Sintaxe usada para chamar a *Stored Procedure*.
- **Vantagem:** O código Java fica mais limpo, enquanto a lógica complexa fica no banco.

---

### **Quando usar cada um?**

| **Tipo**                | **Quando Usar**                                                                             |
|-------------------------|---------------------------------------------------------------------------------------------|
| **`Statement`**         | Para comandos SQL simples e únicos, como criar tabelas ou consultas rápidas sem parâmetros. |
| **`PreparedStatement`** | Para consultas e operações que usam parâmetros, garantindo segurança e eficiência.          |
| **`CallableStatement`** | Quando você precisa executar *Stored Procedures* ou lógica complexa armazenada no banco.    |

---

### **Resumo em Analogia**

- **`Statement`**: O mensageiro básico que leva cartas simples, mas sem muita proteção.
- **`PreparedStatement`**: O mensageiro precavido que leva cartas padrão com espaços para preencher e proteção extra
  contra ataques.
- **`CallableStatement`**: O mensageiro especializado que acessa áreas VIP do banco para executar operações complexas.

---

### **Preparando o Terreno com o PostgreSQL**

Vamos usar um container Docker para subir o banco de dados PostgreSQL. Isso deixa tudo mais prático e elimina a
necessidade de instalar o banco localmente. Se você ainda não tem o Docker instalado, recomendo dar uma olhada na
documentação oficial.

Aqui está o comando para criar e subir o container:

```bash
docker run --name meu_postgres -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=meubanco -p 5432:5432 -d postgres
```

- **POSTGRES_USER**: Define o usuário do banco.
- **POSTGRES_PASSWORD**: Define a senha do banco.
- **POSTGRES_DB**: Nome do banco que será criado.
- **-p 5432:5432**: Mapeia a porta local para a porta do container.

---

Aqui está a versão do arquivo `docker-compose.yml` adaptada para configurar um ambiente PostgreSQL
com volumes para persistência de dados e backup.
Com isso, você pode gerenciar melhor os dados, tanto dentro do container quanto no host.

```yaml
version: '3'
services:

  ### PostgreSQL database for JDBC example
  db-jdbc:
    container_name: db_jdbc
    image: postgres:latest # Usando a imagem oficial do PostgreSQL
    environment:
      POSTGRES_DB: db_jdbc_example # Nome do banco de dados
      POSTGRES_USER: test # Usuário do banco
      POSTGRES_PASSWORD: test123 # Senha do banco
    ports:
      - "5432:5432" # Mapeamento de porta do host para o container
    expose:
      - 5432 # Expondo a porta para outros serviços dentro do Docker Compose
    command: -p 5432 # Configurando a porta padrão do PostgreSQL
    volumes:
      - jdbc-data:/var/lib/postgresql/data # Volume para persistência dos dados

volumes:
  jdbc-data: # Volume nomeado para persistência de dados do PostgreSQL
```

### **Explicação dos Componentes**:

1. **`version: '3'`**: Define a versão do arquivo `docker-compose`. A versão 3 é uma das mais usadas, compatível com o
   Docker atual.

2. **`services`**: Contém os serviços que serão gerenciados pelo `docker-compose`.

3. **`db-jdbc`**: O nome do serviço, que representa o PostgreSQL.
    - **`container_name`**: Nome do container, que facilita identificação ao executar comandos no Docker.
    - **`image`**: Especifica a imagem oficial do PostgreSQL, usando a última versão disponível.
    - **`environment`**: Variáveis de ambiente que configuram o banco:
        - `POSTGRES_DB`: Nome do banco a ser criado.
        - `POSTGRES_USER` e `POSTGRES_PASSWORD`: Credenciais para acessar o banco.
    - **`ports`**: Expõe a porta 5432 do container para o host, permitindo conexões externas.
    - **`expose`**: Torna a porta 5432 acessível a outros containers gerenciados pelo mesmo `docker-compose`.
    - **`command`**: Define a porta que o PostgreSQL usará.
    - **`volumes`**:
        - Primeiro volume: Persiste os dados no volume nomeado `jdbc-data`, que fica no diretório do Docker.

4. **`volumes`**:
    - **`jdbc-data`**: Define um volume nomeado para armazenar os dados do banco. Assim, mesmo que o container seja
      removido, os dados permanecerão.

---

### **Como usar o arquivo**:

1. Salve o conteúdo acima em um arquivo chamado `docker-compose.yml`.
2. Certifique-se de que o Docker e o Docker Compose estejam instalados no seu sistema.
3. No terminal, vá até o diretório onde o arquivo está salvo e execute:
   ```bash
   docker-compose up -d
   ```
4. Isso criará e iniciará o serviço PostgreSQL no container. O banco estará disponível na porta `5432` do host.

### **Dica**:

Para verificar se o container está rodando corretamente, use:

```bash
docker ps
```

E para acessar o banco de dados via terminal, rode:

```bash
docker exec -it db_jdbc psql -U test -d db_jdbc_example
```

---

### **Passo a Passo para Conectar com JDBC**

Agora que temos o banco no ar, vamos ao código Java para conectar e realizar algumas operações.

#### **1. Adicione o Driver JDBC ao Projeto**

Se você está usando Maven, adicione a dependência ao `pom.xml`:

> Acesso o site do maven repository e pesquise pelo driver
> postgresql (https://mvnrepository.com/artifact/org.postgresql/postgresql)

```xml
<!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.4</version>
</dependency>
```

#### **2. Código Básico de Conexão**

Aqui está um exemplo simples de como conectar ao banco:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:postgresql://localhost:5432/meubanco";
    private static final String USUARIO = "admin";
    private static final String SENHA = "admin";

    public static void main(String[] args) {
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            System.out.println("Conexão bem-sucedida!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

- **`URL`**: O endereço do banco no formato `jdbc:postgresql://host:porta/banco`.
- **`DriverManager.getConnection`**: Cria a conexão usando a URL, o usuário e a senha.
- **`try-with-resources`**: Fecha a conexão automaticamente, mesmo que ocorra um erro.

#### **3. Executando Comandos SQL**

Vamos inserir e buscar dados usando JDBC. Primeiro, crie uma tabela no banco (você pode usar o terminal ou uma
ferramenta como DBeaver):

```sql
CREATE TABLE usuarios
(
    id    SERIAL PRIMARY KEY,
    nome  VARCHAR(50),
    email VARCHAR(50)
);
```

Agora, insira dados pelo código Java:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InserirDados {
    private static final String URL = "jdbc:postgresql://localhost:5432/meubanco";
    private static final String USUARIO = "admin";
    private static final String SENHA = "admin";

    public static void main(String[] args) {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";

        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "João Silva");
            stmt.setString(2, "joao@email.com");
            stmt.executeUpdate();

            System.out.println("Dados inseridos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

- **`PreparedStatement`**: Evita injeção de SQL e melhora o desempenho ao reutilizar consultas.
- **`stmt.setString`**: Define os valores dos parâmetros `?`.

---

### **Boas Práticas**

1. **Use try-with-resources**: Garante que conexões e outros recursos sejam fechados corretamente.
2. **Prefira `PreparedStatement`**: Mais seguro e eficiente do que `Statement`.
3. **Organize o Código**: Separe a lógica de conexão em uma classe utilitária para reaproveitamento.
4. **Valide os Dados**: Sempre valide os dados antes de enviá-los ao banco.

---

Vamos configurar o cenário da aula com o banco de dados PostgreSQL e criar a tabela `pessoa` com as colunas `id`, `nome`
e `idade`. Usaremos o container PostgreSQL configurado no arquivo `docker-compose.yml` para facilitar.

> ACESSE O MEU DIRETÓRIO DO GITHUB PARA CONHECER UM POUCO MAIS SOBRE OS COMANDOS DO TERMINAL SOBRE O POSTGRESQL
> https://github.com/uandersonferreira/postgresql-database-relational/blob/main/001-comandos-basicos-psql.md

### **Passo 1: Subir o container do PostgreSQL**

Certifique-se de que o arquivo `docker-compose.yml` está configurado corretamente (veja o exemplo anterior) e execute o
comando:

```bash
docker-compose up -d
```

Isso iniciará o container com o banco de dados configurado.

---

### **Passo 2: Acessar o banco de dados**

Acesse o container para interagir com o PostgreSQL. Execute o comando abaixo para abrir o terminal dentro do container:

```bash
docker exec -it db_jdbc psql -U test -d db_jdbc_example
```

- **`-U test`**: Define o usuário do banco como `test`.
- **`-d db_jdbc_example`**: Define o banco de dados como `db_jdbc_example`.

---

### **Passo 3: Criar a tabela `pessoa`**

Execute o seguinte comando no terminal do PostgreSQL para criar a tabela:

```sql
CREATE TABLE pessoa
(
    id    SERIAL PRIMARY KEY,    -- Coluna ID com auto incremento
    nome  VARCHAR(100) NOT NULL, -- Coluna Nome, obrigatória, com limite de 100 caracteres
    idade INT          NOT NULL  -- Coluna Idade, obrigatória, tipo inteiro
);
```

#### **Explicação**:

- **`id SERIAL PRIMARY KEY`**: Cria uma coluna `id` com auto incremento (similar ao `AUTO_INCREMENT` no MySQL) e define
  como chave primária.
- **`nome VARCHAR(100) NOT NULL`**: Coluna para armazenar o nome da pessoa, com limite de 100 caracteres e obrigatório.
- **`idade INT NOT NULL`**: Coluna para a idade da pessoa, obrigatória, tipo inteiro.

---

### **Passo 4: Inserir dados na tabela**

Insira alguns registros na tabela para que os alunos possam trabalhar com dados reais:

```sql
INSERT INTO pessoa (nome, idade)
VALUES ('João Silva', 25);
INSERT INTO pessoa (nome, idade)
VALUES ('Maria Oliveira', 30);
INSERT INTO pessoa (nome, idade)
VALUES ('Carlos Santos', 40);
INSERT INTO pessoa (nome, idade)
VALUES ('Ana Costa', 22);
```

---

### **Passo 5: Consultar os dados**

Verifique se os dados foram inseridos corretamente:

```sql
SELECT *
FROM pessoa;
```

O resultado deve ser algo assim:

```
 id |      nome       | idade 
----+-----------------+-------
  1 | João Silva      |    25
  2 | Maria Oliveira  |    30
  3 | Carlos Santos   |    40
  4 | Ana Costa       |    22
(4 rows)
```

---

### **Passo 6: Configurar o JDBC no Java**

Agora que o banco de dados e a tabela estão prontos, podemos usar o JDBC no Java para acessar e manipular a tabela
`pessoa`.

Aqui está um exemplo básico de como conectar e consultar os dados:

#### Código Java para listar as pessoas no banco:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ListaPessoas {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_jdbc_example";
    private static final String USUARIO = "test";
    private static final String SENHA = "test123";

    public static void main(String[] args) {
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pessoa")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");

                System.out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **O que foi abordado nesse cenário?**

1. **Subir e configurar um ambiente PostgreSQL usando Docker Compose.**
2. **Criar tabelas e trabalhar com comandos SQL básicos.**
3. **Conectar uma aplicação Java ao banco de dados usando JDBC.**
4. **Executar operações básicas (CRUD) em um banco de dados relacional.**

---

### **Conclusão**

JDBC pode parecer complicado à primeira vista, mas como qualquer ferramenta, fica mais fácil com a prática. Pense nele
como uma ponte que você aprende a atravessar com confiança. No final das contas, conectar um sistema ao banco de dados é
uma das tarefas mais comuns (e satisfatórias) no desenvolvimento backend.

E aí, conseguiu entender melhor como usar JDBC na prática? Qualquer dúvida, estou por aqui para ajudar! 🚀