package com.erlis.model;

import java.util.List;

public class ValidationError {
    private String code;
    private List<String> arguments;

    public ValidationError(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "{ "
                + "\"code\": \"" + code + "\""
                + " }";
    }
}

