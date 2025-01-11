package br.com.uanderson.introducaojdbc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    private Map<String, String> errors = new HashMap<>();//< field(key), message(value) >

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    /*
      Basicamente, a classe ValidationResult é usada para armazenar os erros de validação.
      - Ela tem um método addError para adicionar um erro,
      - Um método hasErrors para verificar se há erros
      - E um método getErrors para obter os erros.
     */
}
