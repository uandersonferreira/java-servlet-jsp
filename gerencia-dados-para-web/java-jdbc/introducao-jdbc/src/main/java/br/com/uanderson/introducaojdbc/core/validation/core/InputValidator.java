package br.com.uanderson.introducaojdbc;

import java.util.HashMap;
import java.util.Map;

public class InputValidator {
    private final Map<String, FieldValidator> validators = new HashMap<>();

    public InputValidator addValidator(String fieldName, FieldValidator validator) {
        validators.put(fieldName, validator);
        return this;
    }

    public ValidationResult validate(Map<String, String> fields) {
        ValidationResult result = new ValidationResult();

        fields.forEach((fieldName, value) -> {
            FieldValidator validator = validators.get(fieldName);
            if (validator != null) {
                ValidationResult fieldResult = validator.validate(fieldName, value);
                fieldResult.getErrors().forEach(result::addError);
            }
        });

        return result;
    }
}
