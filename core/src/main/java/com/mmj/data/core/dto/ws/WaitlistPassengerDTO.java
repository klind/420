package com.mmj.data.core.dto.ws;

import com.mmj.data.core.constant.Constants;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.extclient.Passenger;
import com.mmj.data.core.util.DateTimeUtil;

import java.io.Serializable;
import java.time.LocalDate;

public class WaitlistPassengerDTO implements Serializable, Passenger {

    private String firstName;
    private String middleName;
    private String lastName;
    private String priorityCode;
    private LocalDate dob;

    /**
     * No args constructor for use in serialization
     */
    public WaitlistPassengerDTO() {
    }

    /**
     * @param priorityCode
     * @param middleName
     * @param lastName
     * @param firstName
     */
    public WaitlistPassengerDTO(String firstName, String middleName, String lastName, String priorityCode) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.priorityCode = priorityCode;
    }

    @Override
    public String getDateOfBirth() {
        return dob != null ? dob.format(Constants.DATE_FORMATTER) : null;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName The middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The priorityCode
     */
    public String getPriorityCode() {
        return priorityCode;
    }

    /**
     * @param priorityCode The priorityCode
     */
    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setDobString(String dob) throws BusinessException {
        this.dob = DateTimeUtil.stringToLocalDate(dob);
    }
}
