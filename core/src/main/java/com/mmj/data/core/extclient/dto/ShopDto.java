package com.mmj.data.core.extclient.dto;

public class ShopDto extends G4FlightDto {

    private static final long serialVersionUID = -2703642786233483383L;
    private SegmentDto segment;
    private FareAndAvailDto fareAndAvail;
    private boolean canCancel;
    public SegmentDto getSegment() {
        return segment;
    }

    public void setSegment(SegmentDto segment) {
        this.segment = segment;
    }

    public FareAndAvailDto getFareAndAvail() {
        return fareAndAvail;
    }

    public void setFareAndAvail(FareAndAvailDto fareAndAvail) {
        this.fareAndAvail = fareAndAvail;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }
}
