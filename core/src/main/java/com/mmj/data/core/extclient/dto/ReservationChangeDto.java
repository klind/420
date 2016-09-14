package com.mmj.data.core.extclient.dto;

import javax.validation.constraints.NotNull;

public class ReservationChangeDto<T> extends G4FlightDto{

    @NotNull(message = "Company number cannot be null")
    private String company;
    @NotNull(message = "Confirmation number cannot be null")
    private String confirmationNumber;
    @NotNull(message = "Segment cannot be null")
    private String segment;
    @NotNull(message = "Ind cannot be null")
    private String ind;
    @NotNull(message = "PassengerWaitlistDto number cannot be null")
    private String paxNum;
    @NotNull(message = "Entity type cannot be null")
    private String entityType;
    @NotNull(message = "Entity cannot be null")
    private T entity;
    @NotNull(message = "Action cannot be null")
    private String action;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getPaxNum() {
        return paxNum;
    }

    public void setPaxNum(String paxNum) {
        this.paxNum = paxNum;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
