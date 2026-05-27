package com.algaworks.algashop.ordering.domain.vo;

import com.algaworks.algashop.ordering.domain.validator.FieldsValidations;
import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true)
public record Address(
        String street,
        String complement,
        String neighborhood,
        String number,
        String city,
        String state,
        ZipCode zipCode
) {

    public Address {
        FieldsValidations.requiresNonBlank(street);
        FieldsValidations.requiresNonBlank(neighborhood);
        FieldsValidations.requiresNonBlank(number);
        FieldsValidations.requiresNonBlank(city);
        FieldsValidations.requiresNonBlank(state);
        Objects.requireNonNull(zipCode);
    }
}
