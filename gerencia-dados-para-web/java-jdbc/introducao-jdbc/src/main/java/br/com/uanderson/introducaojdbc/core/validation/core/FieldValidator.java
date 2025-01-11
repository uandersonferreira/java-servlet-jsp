package br.com.uanderson.introducaojdbc.core.validation.core;

public interface FieldValidator {
    ValidationResult validate(String fieldName, String value);
}
