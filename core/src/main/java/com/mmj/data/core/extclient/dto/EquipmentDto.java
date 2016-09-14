package com.mmj.data.core.extclient.dto;

public class EquipmentDto extends G4FlightDto{

    private static final long serialVersionUID = -7524908694629868830L;
    private String make;
    private String model;
    private String tailNbr;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTailNbr() {
        return tailNbr;
    }

    public void setTailNbr(String tailNbr) {
        this.tailNbr = tailNbr;
    }

}
