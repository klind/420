package com.mmj.data.core.extclient.dto.summary;

public class PassengerWaitlistDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String priorityCode;

    /**
     * No args constructor for use in serialization
     */
    public PassengerWaitlistDto() {
    }

    /**
     * @param priorityCode
     * @param middleName
     * @param lastName
     * @param firstName
     */
    public PassengerWaitlistDto(String firstName, String middleName, String lastName, String priorityCode) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.priorityCode = priorityCode;
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
}
