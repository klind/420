package com.mmj.data.core.extclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class G4FlightDto implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(G4FlightDto.class);
    private static final long serialVersionUID = -5500992437940490637L;

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String returnString = "\n\"" + this.getClass().getSimpleName() + "\": ";
        try {
            returnString += ow.writeValueAsString(this);
        } catch (IOException e) {
            LOGGER.error("\nError converting " + this.getClass() + " to JSON:\n" + e.getStackTrace());
            returnString += "null";
        }

        return returnString;
    }
}
