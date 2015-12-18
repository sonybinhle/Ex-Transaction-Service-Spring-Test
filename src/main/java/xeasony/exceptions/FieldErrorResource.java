package xeasony.exceptions;

public class FieldErrorResource {
    private String resource;
    private String field;
    private String code;
    private String message;

    public FieldErrorResource(String resource, String field, String code,
                              String message) {
        this.resource = resource;
        this.field = field;
        this.code = code;
        this.message = message;
    }

    public String getResource() {
        return this.resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}