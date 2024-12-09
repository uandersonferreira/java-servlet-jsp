### Introdução à Expression Language (EL) no Java e JSP

Olá! Eu sou o **Uanderson**, um desenvolvedor Backend Java em formação, e hoje vou ajudar você, **desenvolvedor júnior**, a entender de forma descomplicada como funciona a **Expression Language (EL)** em aplicações web com **Java e JSP**.

Se você já ouviu falar em EL, mas achou a explicação parecida com tentar montar um quebra-cabeça sem a imagem da caixa, calma! Vou te guiar por cada peça: sintaxe, operadores, objetos implícitos e muito mais. E prometo exemplos práticos para te ajudar a visualizar como isso se encaixa no desenvolvimento web.

---

### O que é Expression Language?

Expression Language (EL) é como um "atalho de conversa" entre o **JSP** e os dados da aplicação. Em vez de escrever código Java diretamente no JSP (o que não é recomendado), usamos EL para acessar atributos e objetos de forma limpa, legível e simples.

### Sintaxe Básica
A sintaxe de EL é super amigável. Ela utiliza `${}` para delimitar as expressões. Pense nisso como um detector de metal que encontra valores escondidos em seus objetos ou contextos.

**Exemplo:**
```jsp
<p>Olá, ${usuario.nome}!</p>
```
Se `usuario.nome` for `"João"`, o navegador exibirá:  
**Olá, João!**

---

### Literais

Os **literais** são valores fixos, como números, textos ou booleanos. Eles são usados exatamente como aparecem.

**Exemplos:**
```jsp
<p>Número: ${42}</p>
<p>Texto: ${'Java é incrível!'}</p>
<p>Booleano: ${true}</p>
```

Isso é útil para testes rápidos ou comparações dentro da página.

---

### Operadores

A EL suporta operadores comuns que você já conhece do Java. É como se fosse a mesma ferramenta, mas adaptada para o mundo JSP.

#### Aritméticos
```jsp
<p>Soma: ${3 + 5}</p>
<p>Subtração: ${10 - 2}</p>
```

#### Relacionais
```jsp
<p>Maior que: ${10 > 5}</p>
<p>Igual: ${'Java' == 'Java'}</p>
```

#### Lógicos
```jsp
<p>And: ${true && false}</p>
<p>Or: ${true || false}</p>
```

---

### Operador `.` (ponto) e `[]` (colchete)

Esses dois operadores são como duas formas diferentes de abrir portas. O `.` é o jeito mais comum, mas o `[]` é útil quando você lida com chaves dinâmicas.

**Exemplo com ponto:**
```jsp
<p>Nome: ${usuario.nome}</p>
```

**Exemplo com colchetes:**
```jsp
<p>Nome: ${usuario['nome']}</p>
```

Quando usar um ou outro? Se a propriedade tiver caracteres inválidos no nome (como um espaço), use colchetes.

---

### Operador `empty`

Imagine que você está checando se sua geladeira está vazia. O operador `empty` faz exatamente isso com variáveis ou listas.

**Exemplo:**
```jsp
<p>Está vazio? ${empty listaDeItens}</p>
```
Se `listaDeItens` for `null` ou estiver sem elementos, o resultado será `true`.

---

### Objetos Implícitos

A EL te dá acesso direto a **objetos implícitos** (como o "camarote VIP" do seu contexto JSP). Aqui estão alguns dos mais usados:

- **`pageScope`**: Atributos no escopo da página.
- **`requestScope`**: Atributos na requisição.
- **`sessionScope`**: Atributos na sessão.
- **`applicationScope`**: Atributos na aplicação.

**Exemplo:**
```jsp
<p>Usuário da sessão: ${sessionScope.usuario}</p>
```

---

### EL e JavaBeans

Se você tem um JavaBean (uma classe com atributos privados e métodos getters/setters), o EL acessa os atributos diretamente via getters.

**JavaBean:**
```java
public class Usuario {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
```

**JSP:**
```jsp
<p>Nome do usuário: ${usuario.nome}</p>
```

---

### Desabilitar Expression Language

Se, por algum motivo, você não quiser que a EL funcione no seu JSP (spoiler: raramente acontece), você pode desabilitá-la.

**No JSP:**
```jsp
<%@ page isELIgnored="true" %>
```

Desabilita a **Expression Language (EL)** de forma global para todas as páginas JSP no projeto que correspondem ao padrão definido no **`<url-pattern>`**.

### Como funciona?

No arquivo **`web.xml`** (o arquivo de configuração da aplicação web), você pode usar o elemento **`<jsp-config>`** para definir grupos de propriedades JSP, e uma dessas propriedades é **`el-ignored`**.

#### Configuração de exemplo:
```xml
<jsp-config>
    <jsp-property-group>
        <url-pattern>*.jsp</url-pattern>
        <el-ignored>true</el-ignored>
    </jsp-property-group>
</jsp-config>
```

### O que acontece aqui?
1. **`<url-pattern>*.jsp</url-pattern>`**  
   Define que essa configuração será aplicada a **todas as páginas JSP** no projeto.

2. **`<el-ignored>true</el-ignored>`**  
   Indica que a **Expression Language (EL)** será ignorada nessas páginas. Ou seja, qualquer expressão como `${}` será tratada como texto literal e não será avaliada.

### Quando usar isso?

Desabilitar a EL globalmente pode ser útil em situações como:
- Você tem páginas JSP que não devem processar EL por razões de segurança ou compatibilidade.
- Está trabalhando em um projeto legado onde a EL não é utilizada e quer evitar problemas por configurações acidentais.

### Observação:
Se você precisar habilitar a EL apenas para **páginas específicas**, pode ajustar o **`<url-pattern>`** para corresponder apenas a essas páginas.

#### Exemplo:
```xml
<jsp-config>
    <jsp-property-group>
        <url-pattern>/legacy/*.jsp</url-pattern>
        <el-ignored>true</el-ignored>
    </jsp-property-group>
</jsp-config>
```

Aqui, a EL será desabilitada apenas para as páginas dentro do diretório `legacy`.

---


