package com.algaworks.algashop.ordering.vo;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_DOCUMENT_CANNOT_BE_BLANK;

public record Document(String value) {

    public Document {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_DOCUMENT_CANNOT_BE_BLANK);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}