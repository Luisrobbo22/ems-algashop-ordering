package com.algaworks.algashop.ordering.vo;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.validator.FieldsValidations;

public record Email(String value) {
    public Email {
        FieldsValidations.requiresValidEmail(value, ErrorMessages.VALIDATION_ERROR_EMAIL_IS_NOT_VALID);
    }

    @Override
    public String toString() {
        return value;
    }
}