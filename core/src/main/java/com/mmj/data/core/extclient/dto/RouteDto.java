package com.mmj.data.core.extclient.dto;

import java.util.ArrayList;
import java.util.List;

public class RouteDto extends G4FlightDto {

    private static final long serialVersionUID = -6418665258407536131L;
    private List<AirportDto> connections;

    private String code;

    private String displayName;

    private String title;

    private int locationId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public List<AirportDto> getConnections() {
        return connections;
    }

    public void setConnections(List<AirportDto> connections) {
        this.connections = connections;
    }

    public List<AirportDto> getDestination() {
        if(connections == null)
            connections = new ArrayList<AirportDto>();
        return connections;
    }

    public void addConnections(List<AirportDto> connections) {
        if (this.connections == null) {
            this.connections = new ArrayList<AirportDto>();
        }
        this.connections.addAll(connections);
    }
}
