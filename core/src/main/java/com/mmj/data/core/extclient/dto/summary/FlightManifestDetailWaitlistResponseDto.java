package com.mmj.data.core.extclient.dto.summary;

import java.util.ArrayList;
import java.util.List;

public class FlightManifestDetailWaitlistResponseDto {

    private List<WaitlistDetailsDto> waitList = new ArrayList<>();
    private Integer priorityNumber;

    /**
     * No args constructor for use in serialization
     */
    public FlightManifestDetailWaitlistResponseDto() {
    }

    /**
     * @param waitList
     */
    public FlightManifestDetailWaitlistResponseDto(List<WaitlistDetailsDto> waitList) {
        this.waitList = waitList;
    }

    /**
     * @return The waitList
     */
    public List<WaitlistDetailsDto> getWaitList() {
        return waitList;
    }

    /**
     * @param waitList The waitList
     */
    public void setWaitList(List<WaitlistDetailsDto> waitList) {
        this.waitList = waitList;
    }

    public Integer getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Integer priorityNumber) {
        this.priorityNumber = priorityNumber;
    }
}
