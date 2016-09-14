package com.mmj.data.core.extclient.dto;

import java.util.List;

public class BaseResponseDto extends G4FlightDto {

    private static final long serialVersionUID = -3542086207483625816L;
    private ResponseStatusDto responseStatusDto;

    public BaseResponseDto() {
        this.responseStatusDto = new ResponseStatusDto();
    }

    public ResponseStatusDto getResponseStatusDto() {
        return this.responseStatusDto;
    }

    public void setResponseStatusDto(ResponseStatusDto responseStatusDto) {
        this.responseStatusDto = responseStatusDto;
    }

    public void setError(StatusDto error) {
        this.responseStatusDto.addErrors(error);
    }

    public void addWarning(StatusDto status) {
        this.responseStatusDto.addWarning(status);
    }

    public void setWarnings(List<StatusDto> statusList) {
        this.responseStatusDto.setWarnings(statusList);
    }
}
