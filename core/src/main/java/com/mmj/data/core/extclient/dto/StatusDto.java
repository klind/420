package com.mmj.data.core.extclient.dto;

public class StatusDto extends G4FlightDto {

    private static final long serialVersionUID = -203359737386698771L;
    private String code;
    private String message;

    public StatusDto(){}

    public StatusDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
