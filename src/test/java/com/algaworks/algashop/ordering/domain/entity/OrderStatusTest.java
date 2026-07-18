package com.algaworks.algashop.ordering.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderStatusTest {

    @Test
    public void canChangeTo() {
        Assertions.assertThat(OrderStatus.DRAFT.canChangTo(OrderStatus.PLACED)).isTrue();
        Assertions.assertThat(OrderStatus.DRAFT.canChangTo(OrderStatus.CANCELD)).isTrue();

        Assertions.assertThat(OrderStatus.PAID.canChangTo(OrderStatus.DRAFT)).isFalse();
    }

    @Test
    public void canNotChangeTo() {
        Assertions.assertThat(OrderStatus.PLACED.canNotChangeTo(OrderStatus.DRAFT)).isTrue();
        Assertions.assertThat(OrderStatus.DRAFT.canNotChangeTo(OrderStatus.CANCELD)).isFalse();

        Assertions.assertThat(OrderStatus.PAID.canNotChangeTo(OrderStatus.DRAFT)).isTrue();
    }

}