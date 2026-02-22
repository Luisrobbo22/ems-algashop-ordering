package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.validator.FieldsValidations;
import com.algaworks.algashop.ordering.vo.BirthDate;
import com.algaworks.algashop.ordering.vo.CustomerId;
import com.algaworks.algashop.ordering.vo.Document;
import com.algaworks.algashop.ordering.vo.Email;
import com.algaworks.algashop.ordering.vo.Fullname;
import com.algaworks.algashop.ordering.vo.LoyaltPoints;
import com.algaworks.algashop.ordering.vo.Phone;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private CustomerId id;
    private Fullname fullname;
    private BirthDate birtDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltPoints loyaltyPoints;

    public Customer(CustomerId id, Fullname fullname, BirthDate birtDate,
                    Email email, Phone phone, Document document,
                    Boolean promotionNotificationsAllowed, Boolean archived,
                    OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                    LoyaltPoints loyaltyPoints) {
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
    }

    public Customer(CustomerId id, Fullname fullname, BirthDate birtDate, Email email, Phone phone, Document document,
                    Boolean promotionNotificationsAllowed, OffsetDateTime registeredAt) {

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
    }

    public void addLoyaltyPoints(LoyaltPoints points) {
        this.verifyIfChangeable();
        this.setLoyaltyPoints(this.loyaltyPoints.add(points));

    }

    public void archive() {
        this.verifyIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullname(new Fullname("Anonymous", "Anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID().toString().concat("@anonymous.com")));
        this.setBirtDate(null);
        this.setPromotionNotificationsAllowed(false);
    }


    public void enablePromotionNotifications() {
        this.verifyIfChangeable();
        this.setPromotionNotificationsAllowed(true);

    }

    public void disablePromotionNotifications() {
        this.verifyIfChangeable();
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(Fullname fullname) {
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

    public CustomerId id() {
        return id;
    }

    public Fullname fullname() {
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

    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullname(Fullname fullname) {
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
