package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PassengerBagDto extends G4FlightDto {

    private static final long serialVersionUID = 1569470113630034214L;
    private String type;
    private Integer count;
    private BigDecimal feeAmount;
    private List<String> bagTags;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public List<String> getBagTags() {

        if(bagTags == null){
            bagTags = new ArrayList<String>();
        }

        return bagTags;
    }
}
