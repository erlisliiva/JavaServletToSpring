package com.erlis.model;

import java.util.LinkedList;
import java.util.List;

public class ValidationErrors {
    private List<ValidationError> errors;

    public List<ValidationError> getErrors() {
        if (errors == null) {
            errors = new LinkedList<>();
        }
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "{ "
                + " \"errors\":" + errors
                + "}";
    }
}

