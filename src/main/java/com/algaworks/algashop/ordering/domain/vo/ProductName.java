package com.algaworks.algashop.ordering.domain.vo;

import com.algaworks.algashop.ordering.domain.validator.FieldsValidations;

public record ProductName(String value) {

    public ProductName {
        FieldsValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }

}