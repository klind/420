package com.mmj.data.core.extclient.dto;

import java.util.ArrayList;
import java.util.List;

public class RouteListResponseDto extends BaseResponseDto {

    private static final long serialVersionUID = -5888252781970184882L;
    private List<RouteDto> airports;

    public List<RouteDto> getAirports() {
        if (this.airports == null) {
            this.airports = new ArrayList<RouteDto>();
        }
        return airports;
    }

    public void setAirports(List<RouteDto> airports) {
        if (this.airports == null) {
            this.airports = new ArrayList<RouteDto>();
        }
        this.airports.addAll(airports);
    }
}
