package com.algaworks.algashop.ordering.domain.entity;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELD(PAID, READY, PLACED, DRAFT);

    OrderStatus(OrderStatus... previousStatuses) {
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    private final List<OrderStatus> previousStatuses;

    public boolean canChangTo(OrderStatus newStatus) {
        return newStatus.previousStatuses.contains(this);
    }

    public boolean canNotChangeTo(OrderStatus newStatus) {
        return !canChangTo(newStatus);
    }

}
