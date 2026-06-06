package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.vo.Address;
import com.algaworks.algashop.ordering.domain.vo.BirthDate;
import com.algaworks.algashop.ordering.domain.vo.Document;
import com.algaworks.algashop.ordering.domain.vo.Email;
import com.algaworks.algashop.ordering.domain.vo.FullName;
import com.algaworks.algashop.ordering.domain.vo.LoyaltPoints;
import com.algaworks.algashop.ordering.domain.vo.Phone;
import com.algaworks.algashop.ordering.domain.vo.id.CustomerId;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private CustomerId id;
    private FullName fullname;
    private BirthDate birtDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltPoints loyaltyPoints;
    private Address address;

    @Builder(builderClassName = "ExistingCustomerBuild", builderMethodName = "existing")
    private Customer(CustomerId id, FullName fullname, BirthDate birtDate,
                     Email email, Phone phone, Document document,
                     Boolean promotionNotificationsAllowed, Boolean archived,
                     OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                     LoyaltPoints loyaltyPoints, Address address) {
        this.setId(id);
        this.setFullname(fullname);
        this.setBirtDate(birtDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
        this.setAddress(address);
    }

    private Customer(CustomerId id, FullName fullname, BirthDate birtDate, Email email, Phone phone, Document document,
                     Boolean promotionNotificationsAllowed, OffsetDateTime registeredAt, Address address) {

        this.setId(id);
        this.setFullname(fullname);
        this.setBirtDate(birtDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setRegisteredAt(registeredAt);
        this.setArchived(false);
        this.setLoyaltyPoints(LoyaltPoints.ZERO);
        this.setAddress(address);
    }

    @Builder(builderClassName = "BrandNewCustomerBuild", builderMethodName = "brandNew")
    private static Customer createBrandNew(FullName fullname, BirthDate birtDate, Email email, Phone phone, Document document,
                                           Boolean promotionNotificationsAllowed, Address address) {

        return new Customer(new CustomerId(),
                fullname,
                birtDate,
                email,
                phone,
                document,
                promotionNotificationsAllowed,
                false,
                OffsetDateTime.now(),
                null,
                LoyaltPoints.ZERO,
                address);
    }

    public void addLoyaltyPoints(LoyaltPoints points) {
        this.verifyIfChangeable();
        this.setLoyaltyPoints(this.loyaltyPoints.add(points));

    }

    public void archive() {
        this.verifyIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullname(new FullName("Anonymous", "Anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID().toString().concat("@anonymous.com")));
        this.setBirtDate(null);
        this.setPromotionNotificationsAllowed(false);
        this.setAddress(this.address.toBuilder()
                .number("Anonymized")
                .complement(null).build());
    }


    public void enablePromotionNotifications() {
        this.verifyIfChangeable();
        this.setPromotionNotificationsAllowed(true);

    }

    public void disablePromotionNotifications() {
        this.verifyIfChangeable();
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(FullName fullname) {
        this.verifyIfChangeable();
        this.setFullname(fullname);

    }

    public void changeEmail(Email email) {
        this.verifyIfChangeable();
        this.setEmail(email);

    }

    public void changePhone(Phone phone) {
        this.verifyIfChangeable();
        this.setPhone(phone);
    }

    public void changeAddress(Address address) {
        verifyIfChangeable();
        this.verifyIfChangeable();
        this.setAddress(address);
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullname() {
        return fullname;
    }

    public BirthDate birtDate() {
        return birtDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
        return document;
    }

    public Boolean isPromotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    public Address address() {
        return address;
    }

    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullname(FullName fullname) {
        Objects.requireNonNull(fullname, ErrorMessages.VALIDATION_ERROR_FULLNAME_CANNOT_BE_NULL);
        this.fullname = fullname;
    }

    private void setBirtDate(BirthDate birtDate) {
        if (birtDate == null) {
            this.birtDate = null;
            return;
        }

        this.birtDate = birtDate;
    }

    private void setEmail(Email email) {
        this.email = email;
    }

    private void setPhone(Phone phone) {
        Objects.requireNonNull(phone, ErrorMessages.VALIDATION_ERROR_PHONE_CANNOT_BE_BLANK);
        this.phone = phone;
    }

    private void setDocument(Document document) {
        Objects.requireNonNull(document, ErrorMessages.VALIDATION_ERROR_DOCUMENT_CANNOT_BE_BLANK);
        this.document = document;
    }

    private void setPromotionNotificationsAllowed(Boolean promotionNotificationsAllowed) {
        Objects.requireNonNull(promotionNotificationsAllowed, ErrorMessages.VALIDATION_ERROR_PROMOTION_NOTIFICATIONS_ALLOWED_CANNOT_BE_NULL);
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived, ErrorMessages.VALIDATION_ERROR_ARCHIVED_CANNOT_BE_NULL);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt, ErrorMessages.VALIDATION_ERROR_REGISTEREDAT_CANNOT_BE_NULL);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints, ErrorMessages.VALIDATION_ERROR_LOYALTYPOINTS_CANNOT_BE_NULL);
        this.loyaltyPoints = loyaltyPoints;
    }

    private void setAddress(Address address) {
        Objects.requireNonNull(address, ErrorMessages.VALIDATION_ERROR_ADDRESS_CANNOT_BE_NULL);
        this.address = address;
    }

    private void verifyIfChangeable() {
        if (this.isArchived()) {
            throw new CustomerArchivedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
