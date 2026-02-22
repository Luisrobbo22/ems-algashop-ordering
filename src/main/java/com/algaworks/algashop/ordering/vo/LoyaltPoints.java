package com.algaworks.algashop.ordering.vo;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LOYALTYPOINTS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO;

public record LoyaltPoints(Integer points) implements Comparable<LoyaltPoints> {

    public static final LoyaltPoints ZERO = new LoyaltPoints();

    public LoyaltPoints() {
        this(0);
    }

    public LoyaltPoints(Integer points) {
        Objects.requireNonNull(points);
        if (points < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTYPOINTS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
        }
        this.points = points;
    }

    public LoyaltPoints add(Integer points) {
        return add(new LoyaltPoints(points));
    }

    public LoyaltPoints add(LoyaltPoints loyaltPoints) {
        Objects.requireNonNull(points);
        if (loyaltPoints.points() < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTYPOINTS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
        }

        return new LoyaltPoints(this.points + loyaltPoints.points());
    }

    @Override
    public String toString() {
        return points.toString();
    }

    @Override
    public int compareTo(LoyaltPoints o) {
        return this.points.compareTo(o.points());
    }
}
