package com.mmj.data.core.extclient.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class SegmentDto extends G4FlightDto{

    private static final long serialVersionUID = -1548757077241283971L;
    private String airlineCode;
    private String fltNum;
    private EquipmentDto equipment;
    private String flightStatus;
    private String closeFlag;

    private List<LegDto> legs;

    public List<LegDto> getLegs() {
        return legs;
    }

    public void setLegs(List<LegDto> legs) {
        this.legs = legs;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getFltNum() {
        return fltNum;
    }

    public void setFltNum(String fltNum) {
        this.fltNum = fltNum;
    }

    public void addLegDto(LegDto legDto) {
        if (null == legs) {
            legs = new ArrayList<LegDto>();
        }
        legs.add(legDto);
    }

    public EquipmentDto getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDto equipment) {
        this.equipment = equipment;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getCloseFlag() {
        return closeFlag;
    }

    public void setCloseFlag(String closeFlag) {
        this.closeFlag = closeFlag;
    }

    @JsonIgnore
    public LegDto getFirstLeg(){
        if(legs != null && !legs.isEmpty()){
            return legs.get(0);
        }

        return null;
    }
}
