package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.vo.Money;
import com.algaworks.algashop.ordering.domain.vo.ProductName;
import com.algaworks.algashop.ordering.domain.vo.Quantity;
import com.algaworks.algashop.ordering.domain.vo.id.OrderId;
import com.algaworks.algashop.ordering.domain.vo.id.ProductId;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void shouldGenerate() {
        OrderItem.brandNew()
                .productId(new ProductId())
                .quantity(new Quantity(1))
                .orderId(new OrderId())
                .productName(new ProductName("Mouse Pad"))
                .price(new Money("100")).build();

    }

}