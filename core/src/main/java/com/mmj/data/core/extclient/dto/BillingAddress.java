package com.mmj.data.core.extclient.dto;

public class BillingAddress {

    private Object address1;
    private Object address2;
    private Object city;
    private Object state;
    private Object zipCode5;
    private Object zipCode4;
    private Object country;

    /**
     * No args constructor for use in serialization
     */
    public BillingAddress() {
    }

    /**
     * @param state
     * @param address1
     * @param address2
     * @param zipCode5
     * @param zipCode4
     * @param country
     * @param city
     */
    public BillingAddress(Object address1, Object address2, Object city, Object state, Object zipCode5, Object zipCode4, Object country) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zipCode5 = zipCode5;
        this.zipCode4 = zipCode4;
        this.country = country;
    }

    /**
     * @return The address1
     */
    public Object getAddress1() {
        return address1;
    }

    /**
     * @param address1 The address1
     */
    public void setAddress1(Object address1) {
        this.address1 = address1;
    }

    /**
     * @return The address2
     */
    public Object getAddress2() {
        return address2;
    }

    /**
     * @param address2 The address2
     */
    public void setAddress2(Object address2) {
        this.address2 = address2;
    }

    /**
     * @return The city
     */
    public Object getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(Object city) {
        this.city = city;
    }

    /**
     * @return The state
     */
    public Object getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(Object state) {
        this.state = state;
    }

    /**
     * @return The zipCode5
     */
    public Object getZipCode5() {
        return zipCode5;
    }

    /**
     * @param zipCode5 The zipCode5
     */
    public void setZipCode5(Object zipCode5) {
        this.zipCode5 = zipCode5;
    }

    /**
     * @return The zipCode4
     */
    public Object getZipCode4() {
        return zipCode4;
    }

    /**
     * @param zipCode4 The zipCode4
     */
    public void setZipCode4(Object zipCode4) {
        this.zipCode4 = zipCode4;
    }

    /**
     * @return The country
     */
    public Object getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(Object country) {
        this.country = country;
    }
}
