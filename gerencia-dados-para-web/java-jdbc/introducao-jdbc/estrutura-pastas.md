## Estrutura de Pastas
```text
src/main/java/br/com/uanderson/
└── core/
    └── validation/
        ├── annotations/              # Anotações customizadas de validação
        │   └── Required.java
        ├── validators/              # Implementações específicas
        │   ├── pessoa/             # Validadores específicos por domínio
        │   │   ├── NomeValidator.java
        │   │   └── IdadeValidator.java
        │   └── common/             # Validadores genéricos reutilizáveis
        │       ├── EmailValidator.java
        │       └── NumericValidator.java
        ├── core/                   # Classes principais de validação
        │   ├── ValidationResult.java
        │   ├── ValidationError.java
        │   └── FieldValidator.java
        ├── exceptions/             # Exceções específicas de validação
        │   └── ValidationException.java
        └── factory/                # Factories para criar validadores
            └── ValidatorFactory.java

```

### Explicação das pastas principais da estrutura:

```text
core/validation/ - É a pasta raiz que contém toda a lógica de validação do sistema

annotations/ - Guarda anotações personalizadas para validação, úteis para validar campos usando decoradores Java
```
```java 
@Required
private String nome;
```




``` text
validators/ - Contém duas subpastas importantes:

pessoa/ - Validadores específicos do seu negócio (NomeValidator, IdadeValidator)
common/ - Validadores genéricos que podem ser usados em qualquer parte do sistema (EmailValidator, CPFValidator)
core/ - Classes fundamentais do sistema de validação:

ValidationResult - Armazena resultados da validação
ValidationError - Representa erros de validação
FieldValidator - Interface base para todos validadores
exceptions/ - Exceções customizadas para tratamento de erros de validação

factory/ - Classes responsáveis por criar instâncias dos validadores
```

Esta organização permite encontrar rapidamente qualquer componente de validação e facilita a manutenção do código.