package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.validator.FieldsValidations;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private UUID id;
    private String fullname;
    private LocalDate birtDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private Integer loyaltyPoints;

    public Customer(UUID id, String fullname, LocalDate birtDate,
                    String email, String phone, String document,
                    Boolean promotionNotificationsAllowed, Boolean archived,
                    OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                    Integer loyaltyPoints) {
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

    public Customer(UUID id, String fullname, LocalDate birtDate, String email, String phone, String document,
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
        this.setLoyaltyPoints(0);
    }

    public void addLoyaltyPoints(Integer points) {
        this.verifyIfChangeable();
        if (points <= 0)
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_LOYALTYPOINTS_MUST_BE_GREATER_THAN_ZERO);

        this.setLoyaltyPoints(this.loyaltyPoints + points);

    }

    public void archive() {
        this.verifyIfChangeable();


        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullname("Anonymous");
        this.setPhone("000-000-0000");
        this.setDocument("000-00-0000");
        this.setEmail(UUID.randomUUID().toString().concat("@anonymous.com"));
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

    public void changeName(String fullname) {
        this.verifyIfChangeable();
        this.setFullname(fullname);

    }

    public void changeEmail(String email) {
        this.verifyIfChangeable();
        this.setEmail(email);

    }

    public void changePhone(String phone) {
        this.verifyIfChangeable();
        this.setPhone(phone);
    }

    public UUID id() {
        return id;
    }

    public String fullname() {
        return fullname;
    }

    public LocalDate birtDate() {
        return birtDate;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String document() {
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

    public Integer loyaltyPoints() {
        return loyaltyPoints;
    }

    private void setId(UUID id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullname(String fullname) {
        Objects.requireNonNull(fullname, ErrorMessages.VALIDATION_ERROR_FULLNAME_CANNOT_BE_NULL);
        if (fullname.isBlank())
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_FULLNAME_CANNOT_BE_BLANK);

        this.fullname = fullname;
    }

    private void setBirtDate(LocalDate birtDate) {
        if (birtDate == null) {
            this.birtDate = null;
            return;
        }

        if (birtDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_BE_IN_PAST);
        }

        this.birtDate = birtDate;
    }

    private void setEmail(String email) {
        FieldsValidations.requiresValidEmail(email, ErrorMessages.VALIDATION_ERROR_EMAIL_IS_NOT_VALID);
        this.email = email;
    }

    private void setPhone(String phone) {
        Objects.requireNonNull(phone, ErrorMessages.VALIDATION_ERROR_PHONE_CANNOT_BE_NULL);
        this.phone = phone;
    }

    private void setDocument(String document) {
        Objects.requireNonNull(document, ErrorMessages.VALIDATION_ERROR_DOCUMENT_CANNOT_BE_NULL);
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

    private void setLoyaltyPoints(Integer loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints, ErrorMessages.VALIDATION_ERROR_LOYALTYPOINTS_CANNOT_BE_NULL);
        if (loyaltyPoints < 0)
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_LOYALTYPOINTS_MUST_BE_GREATER_THAN_ZERO);
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
