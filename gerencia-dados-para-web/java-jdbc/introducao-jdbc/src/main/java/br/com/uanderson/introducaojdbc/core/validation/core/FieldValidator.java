package br.com.uanderson.introducaojdbc;

public interface FieldValidator {
    ValidationResult validate(String fieldName, String value);
}
