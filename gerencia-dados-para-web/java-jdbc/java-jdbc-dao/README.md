### Como Trabalhar com JDBC API Utilizando o Padrão DAO na Prática

Olá! Eu sou Uanderson, um desenvolvedor Backend Java em formação, e hoje vou te ajudar a entender como utilizar o JDBC
API com o padrão DAO (Data Access Object) de forma clara e prática. Se você é um desenvolvedor Júnior, sem muita
experiência em projetos reais, este guia é perfeito para você.

Imagine o padrão DAO como um “garçom” que gerencia todas as suas interações com o banco de dados. Ele organiza pedidos (
consultas SQL), entrega para a cozinha (o banco de dados) e traz o prato pronto (os resultados). Vamos explorar cada
detalhe para você entender como construir esse sistema.

---

### **Por que usar o padrão DAO com JDBC?**

Sem DAO, você teria que escrever códigos JDBC misturados em todas as partes do seu sistema. Isso seria como tentar
cozinhar no meio da sala: bagunçado e nada organizado! Com DAO, você separa o código que acessa o banco de dados em uma
camada específica, deixando seu projeto mais organizado, fácil de manter e testável.

#### **Benefícios principais:**

- **Organização:** Todo o acesso ao banco fica centralizado.
- **Reutilização de código:** Você reaproveita os DAOs em várias partes do sistema.
- **Facilidade para evoluir:** Mudanças no banco (ex.: migrar para outro SGBD) impactam apenas os DAOs.

---

### **Elementos Necessários para Usar JDBC com DAO**

Agora, vamos listar o que você precisa:

1. **Banco de Dados**
   Vamos usar um container PostgreSQL com Docker. Certifique-se de que o container está configurado corretamente (veja
   nosso tutorial anterior).

2. **Tabela Pessoa**
   Criaremos a tabela `pessoa`, que terá os seguintes campos:
    - `id` (auto incremento): Identificador único.
    - `nome`: Nome da pessoa.
    - `idade`: Idade da pessoa.

3. **Configuração JDBC**
   Você precisará:
    - Driver do PostgreSQL.
    - URL de conexão.
    - Usuário e senha do banco.

4. **Classes Java**
   Aqui estão os pilares do padrão DAO que vamos implementar:

    - **Entidade (`Pessoa`)**: Representa uma tabela no banco.
    - **DAO Interface (`PessoaDAO`)**: Define as operações que podem ser realizadas.
    - **DAO Implementação (`PessoaDAOImpl`)**: Implementa os métodos definidos na interface.
    - **Fábrica de Conexão (`ConnectionFactory`)**: Gerencia as conexões com o banco de dados, utilizando o padrão
      Singleton.

---

### **Mãos na Massa: Implementando o DAO com JDBC**

#### **1. Configuração do Banco de Dados**

No terminal, crie a tabela `pessoa`:

```sql
CREATE TABLE pessoa
(
    id    SERIAL PRIMARY KEY,
    nome  VARCHAR(100) NOT NULL,
    idade INT          NOT NULL
);
```

#### **2. Criando a Classe de Conexão (`ConnectionFactory`)**

Essa classe gerencia as conexões com o banco. Vamos aplicar o padrão Singleton para garantir que apenas uma instância
seja criada durante o ciclo de vida da aplicação.

```java
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_jdbc_example";
    private static final String USUARIO = "test";
    private static final String SENHA = "test123";
    private static ConnectionFactory instance;

    // Construtor privado para evitar instanciações externas
    private ConnectionFactory() {
    }

    // Método para retornar a instância única
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}
```

#### **3. Criando a Interface DAO (`PessoaDAO`)**

A interface define os métodos que serão implementados pela classe concreta. Isso garante que o código dependa de
abstrações e não de implementações.

```java
import java.util.List;

public interface PessoaDAO {
    void salvar(Pessoa pessoa);

    List<Pessoa> listar();
}
```

#### **4. Implementando a Classe DAO (`PessoaDAOImpl`)**

Essa classe implementa os métodos definidos na interface.

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAOImpl implements PessoaDAO {

    private final ConnectionFactory connectionFactory;

    // Injetando a fábrica de conexão para maior flexibilidade
    public PessoaDAOImpl() {
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    @Override
    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, idade) VALUES (?, ?)";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pessoa> listar() {
        String sql = "SELECT * FROM pessoa";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setIdade(rs.getInt("idade"));
                pessoas.add(pessoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pessoas;
    }
}
```

#### **5. Testando Tudo no `main`**

Agora, vamos criar uma classe de teste para verificar se tudo está funcionando.

```java
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PessoaDAO pessoaDAO = new PessoaDAOImpl();

        // Inserindo uma nova pessoa
        Pessoa novaPessoa = new Pessoa("João Silva", 25);
        pessoaDAO.salvar(novaPessoa);

        // Listando todas as pessoas
        List<Pessoa> pessoas = pessoaDAO.listar();
        for (Pessoa p : pessoas) {
            System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Idade: " + p.getIdade());
        }
    }
}
```
---
# Configuração de Logs com Log4j2

Este guia explica como configurar e utilizar logs em um projeto Java usando Log4j2 com suporte ao Lombok.

## 1. Dependências Maven

Adicione as seguintes dependências ao seu `pom.xml`:

```xml
<!-- Log4j2 Core -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.24.3</version>
</dependency>

<!-- Lombok (para anotações) -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.36</version>
    <scope>provided</scope>
</dependency>
```

## 2. Configuração do Log4j2

Crie o arquivo `src/main/resources/log4j2.xml` com a seguinte configuração:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="INFO" monitorInterval="30">
    <Properties>
        <Property name="LOG_PATTERN">%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight{[%-5level]} [%t] %style{%c{1.}}{bright,cyan} - %msg%n</Property>
        <Property name="APP_LOG_ROOT">logs</Property>
    </Properties>

    <Appenders>
        <!-- Console Appender -->
        <Console name="ConsoleAppender" target="SYSTEM_OUT">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>

        <!-- Rolling File Appender para logs gerais -->
        <RollingFile name="FileAppender"
                     fileName="${APP_LOG_ROOT}/application.log"
                     filePattern="${APP_LOG_ROOT}/application-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>

        <!-- Rolling File Appender para erros -->
        <RollingFile name="ErrorFileAppender"
                     fileName="${APP_LOG_ROOT}/error.log"
                     filePattern="${APP_LOG_ROOT}/error-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
    </Appenders>

    <Loggers>
        <!-- Logger específico para seu pacote -->
        <Logger name="br.com.uanderson" level="debug" additivity="false">
            <AppenderRef ref="ConsoleAppender"/>
            <AppenderRef ref="FileAppender"/>
            <AppenderRef ref="ErrorFileAppender" level="error"/>
        </Logger>

        <!-- Root Logger -->
        <Root level="info">
            <AppenderRef ref="ConsoleAppender"/>
            <AppenderRef ref="FileAppender"/>
            <AppenderRef ref="ErrorFileAppender" level="error"/>
        </Root>
    </Loggers>
</Configuration>
```

## 3. Estrutura de Pastas

Crie uma pasta `logs` na raiz do seu projeto para armazenar os arquivos de log:

```
seu-projeto/
├── logs/                    # Pasta para armazenar os logs
├── src/
│   └── main/
│       └── resources/
│           └── log4j2.xml   # Arquivo de configuração
└── pom.xml
```

## 4. Como Usar

### Opção 1: Usando Lombok (Recomendado)

```java
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExemploService {
    
    public void metodoExemplo() {
        log.debug("Mensagem de debug");
        log.info("Operação realizada com sucesso");
        log.warn("Alerta: recurso está acabando");
        log.error("Erro ao processar operação", new Exception("Exemplo de erro"));
    }
}
```

### Opção 2: Configuração Manual

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExemploService {
    private static final Logger logger = LogManager.getLogger(ExemploService.class);
    
    public void metodoExemplo() {
        logger.debug("Mensagem de debug");
        logger.info("Operação realizada com sucesso");
        logger.warn("Alerta: recurso está acabando");
        logger.error("Erro ao processar operação", new Exception("Exemplo de erro"));
    }
}
```

## 5. Níveis de Log

Os logs são divididos nos seguintes níveis, em ordem de severidade:

- **DEBUG**: Informações detalhadas para desenvolvimento
- **INFO**: Informações gerais sobre o fluxo da aplicação
- **WARN**: Situações inesperadas mas não críticas
- **ERROR**: Erros que precisam de atenção
- **FATAL**: Erros graves que podem comprometer a aplicação

## 6. Arquivos de Log Gerados

- `logs/application.log`: Contém todos os logs da aplicação
- `logs/error.log`: Contém apenas os logs de nível ERROR e FATAL
- Arquivos são rotacionados diariamente ou quando atingem 10MB
- São mantidos até 10 arquivos de backup

## 7. Formatação dos Logs

O padrão de log inclui:
- Data e hora com milissegundos
- Nível do log (colorido no console)
- Thread atual
- Nome da classe (em cyan no console)
- Mensagem do log

Exemplo de saída:
```
2024-01-13 10:15:30.123 [INFO] [main] ExemploService - Operação realizada com sucesso
```
