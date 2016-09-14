package com.mmj.data.core.dto.entity;

import java.time.LocalDate;
import java.util.Set;

/**
 *
 */
public class EmployeeFullDTO extends EmployeePartialDTO {

    private boolean travelStatus;
    private Set<PassengerDTO> passengers;
    private Set<SuspensionDTO> suspensions;
    private Integer guestPassesAlloted;
    private Integer guestPassesBooked;
    private Integer vacationPassesAlloted;
    private Integer vacationPassesUsed;
    private SaCodeDTO saCode;

    public EmployeeFullDTO() {
        super();
    }

    public EmployeeFullDTO(String employeeId, String salutation, String suffix, String firstName, String middleName, String lastName, String email, String title, String status, LocalDate dob, LocalDate lastHireDate, LocalDate termDate, String phone, String jobCode, String location, String departmentId, String departmentName, String managerId, String managerName, String employeeType, boolean travelStatus, Set<PassengerDTO> passengers, Set<SuspensionDTO> suspensions, Integer guestPassesAlloted, Integer guestPassesBooked, Integer vacationPassesAlloted, Integer vacationPassesUsed, SaCodeDTO saCodeDTO, String gender) {
        super(employeeId, salutation, suffix, firstName, middleName, lastName, email, title, status, dob, lastHireDate, termDate, phone, jobCode, location, departmentId, departmentName, managerId, managerName, employeeType, gender);
        this.travelStatus = travelStatus;
        this.passengers = passengers;
        this.suspensions = suspensions;
        this.guestPassesAlloted = guestPassesAlloted;
        this.guestPassesBooked = guestPassesBooked;
        this.vacationPassesAlloted = vacationPassesAlloted;
        this.vacationPassesUsed = vacationPassesUsed;
        this.saCode = saCodeDTO;
    }

    public boolean getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(boolean travelStatus) {
        this.travelStatus = travelStatus;
    }

    public Set<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<PassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public Set<SuspensionDTO> getSuspensions() {
        return suspensions;
    }

    public void setSuspensions(Set<SuspensionDTO> suspensions) {
        this.suspensions = suspensions;
    }

    public Integer getGuestPassesAlloted() {
        return guestPassesAlloted;
    }

    public void setGuestPassesAlloted(Integer guestPassesAlloted) {
        this.guestPassesAlloted = guestPassesAlloted;
    }

    public Integer getGuestPassesBooked() {
        return guestPassesBooked;
    }

    public void setGuestPassesBooked(Integer guestPassesBooked) {
        this.guestPassesBooked = guestPassesBooked;
    }

    public Integer getVacationPassesAlloted() {
        return vacationPassesAlloted;
    }

    public void setVacationPassesAlloted(Integer vacationPassesAlloted) {
        this.vacationPassesAlloted = vacationPassesAlloted;
    }

    public Integer getVacationPassesUsed() {
        return vacationPassesUsed;
    }

    public void setVacationPassesUsed(Integer vacationPassesUsed) {
        this.vacationPassesUsed = vacationPassesUsed;
    }

    public SaCodeDTO getSaCode() {
        return saCode;
    }

    public void setSaCode(SaCodeDTO saCode) {
        this.saCode = saCode;
    }

    public boolean isTravelStatus() {
        return travelStatus;
    }
}
