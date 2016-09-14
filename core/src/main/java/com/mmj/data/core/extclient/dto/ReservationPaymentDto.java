package com.mmj.data.core.extclient.dto;

import javax.annotation.Generated;
@Generated("org.jsonschema2pojo")
public class ReservationPaymentDto {

    private Object paymentTypeId;
    private String paymentType;
    private Object paymentAmount;
    private String cardName;
    private Object cardNumber;
    private Object cardCvv;
    private BillingAddress billingAddress;
    private Object cardPhone;
    private String cardExpireDate;
    private String email;
    private String authorizationCode;
    private String authorizationDate;
    private Object voucherNumber;
    private Object creditCardSurcharge;
    private Object encryptionType;

    /**
     * No args constructor for use in serialization
     */
    public ReservationPaymentDto() {
    }

    /**
     * @param cardCvv
     * @param creditCardSurcharge
     * @param paymentTypeId
     * @param paymentType
     * @param authorizationCode
     * @param encryptionType
     * @param paymentAmount
     * @param cardName
     * @param email
     * @param authorizationDate
     * @param voucherNumber
     * @param billingAddress
     * @param cardExpireDate
     * @param cardPhone
     * @param cardNumber
     */
    public ReservationPaymentDto(Object paymentTypeId, String paymentType, Object paymentAmount, String cardName, Object cardNumber, Object cardCvv, BillingAddress billingAddress, Object cardPhone, String cardExpireDate, String email, String authorizationCode, String authorizationDate, Object voucherNumber, Object creditCardSurcharge, Object encryptionType) {
        this.paymentTypeId = paymentTypeId;
        this.paymentType = paymentType;
        this.paymentAmount = paymentAmount;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardCvv = cardCvv;
        this.billingAddress = billingAddress;
        this.cardPhone = cardPhone;
        this.cardExpireDate = cardExpireDate;
        this.email = email;
        this.authorizationCode = authorizationCode;
        this.authorizationDate = authorizationDate;
        this.voucherNumber = voucherNumber;
        this.creditCardSurcharge = creditCardSurcharge;
        this.encryptionType = encryptionType;
    }

    /**
     * @return The paymentTypeId
     */
    public Object getPaymentTypeId() {
        return paymentTypeId;
    }

    /**
     * @param paymentTypeId The paymentTypeId
     */
    public void setPaymentTypeId(Object paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    /**
     * @return The paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType The paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return The paymentAmount
     */
    public Object getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * @param paymentAmount The paymentAmount
     */
    public void setPaymentAmount(Object paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * @return The cardName
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * @param cardName The cardName
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * @return The cardNumber
     */
    public Object getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber The cardNumber
     */
    public void setCardNumber(Object cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return The cardCvv
     */
    public Object getCardCvv() {
        return cardCvv;
    }

    /**
     * @param cardCvv The cardCvv
     */
    public void setCardCvv(Object cardCvv) {
        this.cardCvv = cardCvv;
    }

    /**
     * @return The billingAddress
     */
    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    /**
     * @param billingAddress The billingAddress
     */
    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * @return The cardPhone
     */
    public Object getCardPhone() {
        return cardPhone;
    }

    /**
     * @param cardPhone The cardPhone
     */
    public void setCardPhone(Object cardPhone) {
        this.cardPhone = cardPhone;
    }

    /**
     * @return The cardExpireDate
     */
    public String getCardExpireDate() {
        return cardExpireDate;
    }

    /**
     * @param cardExpireDate The cardExpireDate
     */
    public void setCardExpireDate(String cardExpireDate) {
        this.cardExpireDate = cardExpireDate;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The authorizationCode
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * @param authorizationCode The authorizationCode
     */
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    /**
     * @return The authorizationDate
     */
    public String getAuthorizationDate() {
        return authorizationDate;
    }

    /**
     * @param authorizationDate The authorizationDate
     */
    public void setAuthorizationDate(String authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    /**
     * @return The voucherNumber
     */
    public Object getVoucherNumber() {
        return voucherNumber;
    }

    /**
     * @param voucherNumber The voucherNumber
     */
    public void setVoucherNumber(Object voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    /**
     * @return The creditCardSurcharge
     */
    public Object getCreditCardSurcharge() {
        return creditCardSurcharge;
    }

    /**
     * @param creditCardSurcharge The creditCardSurcharge
     */
    public void setCreditCardSurcharge(Object creditCardSurcharge) {
        this.creditCardSurcharge = creditCardSurcharge;
    }

    /**
     * @return The encryptionType
     */
    public Object getEncryptionType() {
        return encryptionType;
    }

    /**
     * @param encryptionType The encryptionType
     */
    public void setEncryptionType(Object encryptionType) {
        this.encryptionType = encryptionType;
    }
}
