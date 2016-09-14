package com.mmj.data.core.extclient.dto;

import java.util.List;

public class PassengerIdentificationDto extends G4FlightDto {

    private static final long serialVersionUID = -6209630653612904028L;
    private List<TravelDocumentDto> travelDocuments;
    private String knownTraveller;
    private String redressNumber;

    public List<TravelDocumentDto> getTravelDocuments() {
        return travelDocuments;
    }

    public void setTravelDocuments(List<TravelDocumentDto> travelDocuments) {
        this.travelDocuments = travelDocuments;
    }

    public String getKnownTraveller() {
        return knownTraveller;
    }

    public void setKnownTraveller(String knownTraveller) {
        this.knownTraveller = knownTraveller;
    }

    public String getRedressNumber() {
        return redressNumber;
    }

    public void setRedressNumber(String redressNumber) {
        this.redressNumber = redressNumber;
    }
}

