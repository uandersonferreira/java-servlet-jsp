# LocalStorage and SessionStorage

## Introdução

`LocalStorage` e `SessionStorage` são mecanismos de armazenamento na web que permitem salvar dados no navegador do usuário. Ambos fazem parte da API Web Storage e são amplamente utilizados para armazenar informações que podem ser recuperadas posteriormente sem a necessidade de acessar um servidor.

### Diferenças principais:

- **LocalStorage:**

  - Os dados persistem mesmo após o navegador ser fechado e reaberto.
  - Ideal para armazenar informações que precisam ser mantidas a longo prazo.

- **SessionStorage:**
  - Os dados são apagados quando a aba ou janela do navegador é fechada.
  - Útil para informações que só precisam durar durante a sessão do usuário.

## Métodos de LocalStorage e SessionStorage

### 1. `setItem(key, value)`

Armazena um dado associado a uma chave específica.

```javascript
localStorage.setItem("name", "Uanderson");
sessionStorage.setItem("number", "12345");
```

### 2. `getItem(key)`

Recupera o valor armazenado associado a uma chave.

```javascript
const name = localStorage.getItem("name");
console.log(name); // Uanderson

const number = sessionStorage.getItem("number");
console.log(number); // 12345
```

### 3. `removeItem(key)`

Remove um dado específico associado a uma chave.

```javascript
localStorage.removeItem("name");
sessionStorage.removeItem("number");
```

### 4. `clear()`

Remove todos os dados armazenados.

```javascript
localStorage.clear();
sessionStorage.clear();
```

### 5. Manipulação de objetos JSON

Como o Web Storage só aceita strings, objetos precisam ser convertidos em JSON.

- **Salvar um objeto:**

```javascript
const user = { name: "Uanderson", age: 20, city: "São Paulo" };
localStorage.setItem("user", JSON.stringify(user));
```

- **Recuperar um objeto:**

```javascript
const userData = JSON.parse(localStorage.getItem("user"));
console.log(userData.name); // Uanderson
```
