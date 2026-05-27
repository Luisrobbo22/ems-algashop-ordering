package com.algaworks.algashop.ordering.domain.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal value) implements Comparable<Money> {

    private final static RoundingMode roundingMode = RoundingMode.HALF_EVEN;
    private final static Money ZERO = new Money(BigDecimal.ZERO);

    public Money(String value) {
        this(new BigDecimal(value));
    }

    public Money(BigDecimal value) {
        Objects.requireNonNull(value); //TODO mensagem
        this.value = value.setScale(2, roundingMode);
        if (this.value.signum() == -1) {
            throw new IllegalArgumentException();//TODO mensagem
        }
    }

    public Money multiply(Quantity quantity) {
        Objects.requireNonNull(quantity);
        if (quantity.value() < 1) {
            throw new IllegalArgumentException();
        }
        BigDecimal multiplied = this.value.multiply(new BigDecimal(quantity.value()));
        return new Money(multiplied);
    }

    public Money add(Money money) {
        Objects.requireNonNull(money);
        return new Money(this.value.add(money.value));
    }
    @Override
    public int compareTo(Money o) {
        return this.value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Money divide(Money o) {
        return new Money(this.value.divide(o.value, roundingMode));
    }
}
