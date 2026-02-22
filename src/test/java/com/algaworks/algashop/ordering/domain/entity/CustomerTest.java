package com.algaworks.algashop.ordering.domain.entity;


import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    void given_invalid_email_when_create_then_throw_exception() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1997, 12, 22),
                "invalid-email",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        )).withMessage("Email is not valid");
    }

    @Test
    void given_invalid_email_when_update_customer_email_then_throw_exception() {
        final Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1997, 12, 22),
                "john.doe@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail("invalid-email")).withMessage("Email is not valid");
    }

    @Test
    void given_archived_customer_when_try_update_should_throw_exception() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Anonumous",
                null,
                "anonymoys@anonymoys.com",
                "000-000-0000",
                "000-00-0000",
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                10);


        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive)
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications)
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications)
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail("email@gmail.com"))
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeName("John Doe"))
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone("478-256-2504"))
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

    }
}