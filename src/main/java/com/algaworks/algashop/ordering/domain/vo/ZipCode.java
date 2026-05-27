package com.algaworks.algashop.ordering.domain.vo;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_ZIPCODE_CANNOT_BE_NULL;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_ZIPCODE_MUST_BE_5_CHARACTERS;


public record ZipCode(String value) {

    public ZipCode {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_ZIPCODE_CANNOT_BE_NULL);
        }

        if (value.length() != 5) {
            throw new IllegalArgumentException(VALIDATION_ERROR_ZIPCODE_MUST_BE_5_CHARACTERS);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
