package com.algaworks.algashop.ordering.domain.entity;


import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.vo.Address;
import com.algaworks.algashop.ordering.vo.BirthDate;
import com.algaworks.algashop.ordering.vo.CustomerId;
import com.algaworks.algashop.ordering.vo.Document;
import com.algaworks.algashop.ordering.vo.Email;
import com.algaworks.algashop.ordering.vo.Fullname;
import com.algaworks.algashop.ordering.vo.LoyaltPoints;
import com.algaworks.algashop.ordering.vo.Phone;
import com.algaworks.algashop.ordering.vo.ZipCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    void given_invalid_email_when_create_then_throw_exception() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Customer.brandNew()
                .fullname(new Fullname("John", "Doe"))
                .birtDate(new BirthDate(LocalDate.of(1997, 12, 22)))
                .email(new Email("invalid-email"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .city("New York")
                        .state("South California")
                        .neighborhood("North Ville")
                        .complement("Apt. 114")
                        .zipCode(new ZipCode("12345"))
                        .build()).build()).withMessage("Email is not valid");
    }

    @Test
    void given_invalid_email_when_update_customer_email_then_throw_exception() {

        final Customer customer = Customer.brandNew()
                .fullname(new Fullname("John", "Doe"))
                .birtDate(new BirthDate(LocalDate.of(1997, 12, 22)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .city("New York")
                        .state("South California")
                        .neighborhood("North Ville")
                        .complement("Apt. 114")
                        .zipCode(new ZipCode("12345"))
                        .build()).build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("invalid-email"))).withMessage("Email is not valid");
    }

    @Test
    void given_archived_customer_when_try_update_should_throw_exception() {
        Customer customer = Customer.existing()
                .id(new CustomerId())
                .fullname(new Fullname("Anonumous", "Anonymoys"))
                .birtDate(null)
                .email(new Email("anonymoys@anonymoys.com"))
                .phone(new Phone("000-000-0000"))
                .document(new Document("000-00-0000"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltPoints(10))
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .city("New York")
                        .state("South California")
                        .neighborhood("North Ville")
                        .complement("Apt. 114")
                        .zipCode(new ZipCode("12345"))
                        .build()).build();

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
                .isThrownBy(() -> customer.changeEmail(new Email("email@gmail.com")))
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeName(new Fullname("John", "Doe")))
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone(new Phone("478-256-2504")))
                .withMessage(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);

    }
}