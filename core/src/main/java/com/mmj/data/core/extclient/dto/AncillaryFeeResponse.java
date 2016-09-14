package com.mmj.data.core.extclient.dto;

import java.util.ArrayList;
import java.util.List;

public class AncillaryFeeResponse extends BaseResponseDto {

    private static final long serialVersionUID = -3811611585067362504L;

    private List<AncillaryFeeDto> fees;

    public AncillaryFeeResponse() {
    }

    public List<AncillaryFeeDto> getFees() {
        if (null == fees) {
            fees = new ArrayList<AncillaryFeeDto>();
        }
        return fees;
    }

    public void setFees(List<AncillaryFeeDto> fees) {
        this.fees = fees;
    }

    public void add(AncillaryFeeDto fee) {
        getFees().add(fee);
    }
}
