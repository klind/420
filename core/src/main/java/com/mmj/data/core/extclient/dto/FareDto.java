package com.mmj.data.core.extclient.dto;

import java.util.List;

public class FareDto extends G4FlightDto {

    private static final long serialVersionUID = 349369182572811016L;
    private String fareClassCode;
    private double baseFare;
    private List<FeeDto> fee;

    public String getFareClassCode() {
        return fareClassCode;
    }

    public void setFareClassCode(String fareClassCode) {
        this.fareClassCode = fareClassCode;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    /**
     * @return the fees
     */
    public List<FeeDto> getFee() {
        return fee;
    }

    /**
     * @param fees
     *            the fees to set
     */
    public void setFees(List<FeeDto> fees) {
        this.fee = fees;
    }

}
