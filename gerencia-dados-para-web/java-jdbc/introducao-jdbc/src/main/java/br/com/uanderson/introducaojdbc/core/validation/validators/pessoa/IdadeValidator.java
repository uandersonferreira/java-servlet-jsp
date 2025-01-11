package br.com.uanderson.introducaojdbc.core.validation.validators.pessoa;

import br.com.uanderson.introducaojdbc.core.validation.core.FieldValidator;
import br.com.uanderson.introducaojdbc.core.validation.core.ValidationResult;

public class IdadeValidator implements FieldValidator {
    @Override
    public ValidationResult validate(String fieldName, String value) {
        ValidationResult result = new ValidationResult();
        if (value == null || value.trim().isEmpty()) {
            result.addError(fieldName, "Idade não pode ser nula ou vazia");
        } else {
            try {
                int idade = Integer.parseInt(value);
                if (idade < 0 || idade > 150) {
                    result.addError(fieldName, "Idade deve estar entre 0 e 150");
                }
            } catch (NumberFormatException e) {
                result.addError(fieldName, "Idade deve ser um número inteiro");
            }
        }
        return result;
    }
}
