package com.mmj.data.core.extclient.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseStatusDto extends G4FlightDto {

    private static final long serialVersionUID = -7894810532810589963L;
    private List<StatusDto> warnings;

    private List<StatusDto> errors;

    protected ResponseStatusDto() {
    }

    public ResponseStatusDto(StatusDto error) {
    }


    public List<StatusDto> getWarnings() {
        if (null == warnings) {
            warnings = new ArrayList<StatusDto>();
        }
        return warnings;
    }

    public void setWarnings(List<StatusDto> warnings) {
        this.warnings = warnings;
    }

    public void addWarning(StatusDto status) {
        getWarnings().add(status);
    }


    public List<StatusDto> getErrors() {
        if(null == errors) {
            errors = new ArrayList<StatusDto>();
        }
        return errors;
    }

    public void setErrors(List<StatusDto> errors) {
        this.errors = errors;
    }

    public void addErrors(StatusDto status) {
        getErrors().add(status);
    }

}
