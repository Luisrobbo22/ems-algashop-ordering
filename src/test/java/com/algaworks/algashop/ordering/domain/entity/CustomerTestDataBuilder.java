package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.vo.Address;
import com.algaworks.algashop.ordering.domain.vo.BirthDate;
import com.algaworks.algashop.ordering.domain.vo.CustomerId;
import com.algaworks.algashop.ordering.domain.vo.Document;
import com.algaworks.algashop.ordering.domain.vo.Email;
import com.algaworks.algashop.ordering.domain.vo.FullName;
import com.algaworks.algashop.ordering.domain.vo.LoyaltPoints;
import com.algaworks.algashop.ordering.domain.vo.Phone;
import com.algaworks.algashop.ordering.domain.vo.ZipCode;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {
    private CustomerTestDataBuilder() {
    }

    public static Customer.BrandNewCustomerBuild brandNewCustomer() {
        return Customer.brandNew()
                .fullname(new FullName("John", "Doe"))
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
                        .build());
    }

    public static Customer.ExistingCustomerBuild existingCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .registeredAt(OffsetDateTime.now())
                .archived(false)
                .archivedAt(null)
                .fullname(new FullName("John", "Doe"))
                .birtDate(new BirthDate(LocalDate.of(1997, 12, 22)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .loyaltyPoints(new LoyaltPoints(10))
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .city("New York")
                        .state("South California")
                        .neighborhood("North Ville")
                        .complement("Apt. 114")
                        .zipCode(new ZipCode("12345"))
                        .build());
    }

    public static Customer.ExistingCustomerBuild existingAnonymizedCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .fullname(new FullName("Anonumous", "Anonymoys"))
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
                        .build());
    }

}
