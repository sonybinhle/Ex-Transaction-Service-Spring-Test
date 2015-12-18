package xeasony.exceptions;

import java.util.List;

public class ErrorResource {
    private String url;
    private String code;
    private List<FieldErrorResource> fieldErrors;

    public ErrorResource(String url, String code, List<FieldErrorResource> fieldErrors) {
        this.url = url;
        this.code = code;
        this.fieldErrors = fieldErrors;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<FieldErrorResource> getFieldErrors() {
        return this.fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}