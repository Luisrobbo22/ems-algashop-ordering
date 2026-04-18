package com.algaworks.algashop.ordering.domain.entity;


import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.vo.Address;
import com.algaworks.algashop.ordering.vo.Document;
import com.algaworks.algashop.ordering.vo.Email;
import com.algaworks.algashop.ordering.vo.Fullname;
import com.algaworks.algashop.ordering.vo.Phone;
import com.algaworks.algashop.ordering.vo.ZipCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {


    @Test
    void given_invalid_email_when_create_then_throw_exception() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                        CustomerTestDataBuilder.brandNewCustomer()
                                .email(new Email("invalid-email")).build())
                .withMessage("Email is not valid");
    }

    @Test
    void given_invalid_email_when_update_customer_email_then_throw_exception() {

        final Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("invalid-email"))).withMessage("Email is not valid");
    }

    @Test
    void given_archived_customer_when_try_update_should_throw_exception() {
        Customer customer = CustomerTestDataBuilder.existingAnonymizedCustomer().build();

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

    @Test
    void given_unarchirvedCustomer_whenArchive_shoulAnonymize() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();

        Assertions.assertWith(customer,
                c -> assertThat(c.fullname()).isEqualTo(new Fullname("Anonymous", "Anonymous")),
                c -> assertThat(c.email()).isNotEqualTo(new Email("john.doe@gmail.com")),
                c -> assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
                c -> assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
                c -> assertThat(c.birtDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
                c -> assertThat(c.address()).isEqualTo(
                        Address.builder()
                                .state("Bourbon Street")
                                .number("Anonymized")
                                .city("York")
                                .state("South California")
                                .neighborhood("North Ville")
                                .zipCode(new ZipCode("12345"))
                                .complement(null).build()
                )
        );
    }
}