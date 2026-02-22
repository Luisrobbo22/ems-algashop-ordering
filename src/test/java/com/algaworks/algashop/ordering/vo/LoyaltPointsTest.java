package com.algaworks.algashop.ordering.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LOYALTYPOINTS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO;

class LoyaltPointsTest {

    @Test
    void shouldGenerateWithValue() {
        LoyaltPoints loyaltPoints = new LoyaltPoints(10);

        Assertions.assertThat(loyaltPoints.points()).isEqualTo(10);
    }

    @Test
    void shouldAddValue() {
        LoyaltPoints loyaltPoints = new LoyaltPoints(10);

        Assertions.assertThat(loyaltPoints.add(5).points()).isEqualTo(15);
    }

    @Test
    void shouldNotAddNegativeValue() {
        LoyaltPoints loyaltPoints = new LoyaltPoints(10);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> loyaltPoints.add(-5))
                .withMessage(VALIDATION_ERROR_LOYALTYPOINTS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);

        Assertions.assertThat(loyaltPoints.points()).isEqualTo(10);
    }
}