### SQL para Criar a Tabela `aluno`

```sql
CREATE TABLE aluno
(
    id    SERIAL PRIMARY KEY,           -- ID único e auto-incrementado
    nome  VARCHAR(100)     NOT NULL,    -- Nome da aluno (máximo de 100 caracteres)
    nota1 DOUBLE PRECISION NOT NULL     -- Nota1 da aluno
        nota2 DOUBLE PRECISION NOT NULL -- Nota1 da aluno
);
```
