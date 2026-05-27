package com.algaworks.algashop.ordering.domain.vo;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_FIRST_NAME_CANNOT_BE_BLANK;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LAST_NAME_CANNOT_BE_BLANK;

public record FullName(String firstName, String lastName) {

    public FullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);

        if (firstName.isBlank())
            throw new IllegalArgumentException(VALIDATION_ERROR_FIRST_NAME_CANNOT_BE_BLANK);
        if (lastName.isBlank())
            throw new IllegalArgumentException(VALIDATION_ERROR_LAST_NAME_CANNOT_BE_BLANK);

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
