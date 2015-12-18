package xeasony.exceptions;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class DataValidationException extends Exception {
    private List<FieldErrorResource> fieldErrorResources;
    private static final String code = "VALIDATION_ERROR";

    public DataValidationException(Errors errors) {
        super(code);
        setFieldErrorResources(errors);
    }

    public void setFieldErrorResources(Errors errors) {
        fieldErrorResources = new ArrayList<>();

        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource(
                    fieldError.getObjectName(), fieldError.getField(),
                    fieldError.getCode(), fieldError.getDefaultMessage()
            );

            fieldErrorResources.add(fieldErrorResource);
        }
    }

    public List<FieldErrorResource> getFieldErrorResources() {
        return fieldErrorResources;
    }
}