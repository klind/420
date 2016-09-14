package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.EmployeeBaseDTO;
import com.mmj.data.core.dto.entity.EmployeeFullDTO;
import com.mmj.data.core.dto.entity.EmployeePartialDTO;
import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.dto.entity.SuspensionDTO;
import com.mmj.data.core.util.DateTimeUtil;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Employee Entity.
 */
@Entity
@Table(name = "employee")
public class EmployeeEN implements Serializable {
    private static final long serialVersionUID = 1L;

    // Employee Id's are controlled by pipeline, so we don't generate our own. It is also String, because it can have leading 0s.
    @Id
    @Column(name = "employee_id", length = 6)
    @NotNull
    private String employeeId;

    @Column(name = "salutation", length = 5)
    @Length(max = 5)
    private String salutation;

    @Column(name = "suffix", length = 10)
    @Length(max = 10)
    private String suffix;

    @Column(name = "first_name", length = 30)
    @Length(min = 2, max = 30)
    @NotNull
    private String firstName;

    @Column(name = "middle_name", length = 30)
    @Length(min = 0, max = 30)
    private String middleName;

    @Column(name = "last_name", length = 30)
    @Length(min = 1, max = 30)
    @NotNull
    private String lastName;

    @Column(name = "email", length = 50)
    @Length(min = 5, max = 50)
    @NotNull
    private String email;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dob;

    @Column(name = "last_hire_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date lastHireDate;

    @Column(name = "term_date")
    @Temporal(TemporalType.DATE)
    private Date termDate;

    @Column(name = "phone", length = 30)
    //@NotNull
    private String phone;

    @Column(name = "title", length = 50)
    @Length(max = 50)
    @NotNull
    private String title;

    @Column(name = "status", length = 1)
    @NotNull
    private String status;

    @Column(name = "guest_passes_alloted")
    private Integer guestPassesAlloted;

    @Column(name = "guest_passes_booked")
    private Integer guestPassesBooked;

    @Column(name = "vacation_passes_alloted")
    private Integer vacationPassesAlloted;

    @Column(name = "vacation_passes_used")
    private Integer vacationPassesUsed;

    @Column(name = "job_code", length = 4)
    //@NotNull should be NotNull, but old data from CMSDB have null values
    private String jobCode;

    @Column(length = 4)
    @NotNull
    private String location;

    @Column(name = "department_id", length = 3)
    @NotNull
    private String departmentId;

    @Column(name = "department_name", length = 50)
    @NotNull
    private String departmentName;

    @Column(name = "manager_id", length = 6)
    @NotNull
    private String managerId;

    @Column(name = "employee_type", length = 1)
    @NotNull
    private String employeeType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<PassengerEN> passengers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<SuspensionEN> suspensions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<GuestEN> guests = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="sa_code_id")
    private SaCodeEN saCode;

    @Column(name = "gender", length = 1)
    //@NotNull  // TODO: Make this Not Null once we get the info from pipeline. Commenting out as per Krisitian's request.
    private String gender;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getLastHireDate() {
        return lastHireDate;
    }

    public void setLastHireDate(Date lastHireDate) {
        this.lastHireDate = lastHireDate;
    }

    public Date getTermDate() {
        return termDate;
    }

    public void setTermDate(Date termDate) {
        this.termDate = termDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public Set<PassengerEN> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<PassengerEN> passengers) {
        this.passengers = passengers;
    }

    public Set<SuspensionEN> getSuspensions() {
        return suspensions;
    }

    public void setSuspensions(Set<SuspensionEN> suspensions) {
        this.suspensions = suspensions;
    }

    public SaCodeEN getSaCode() {
        return saCode;
    }

    public void setSaCode(SaCodeEN saCode) {
        this.saCode = saCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns an instance of EmployeeFullDTO with passengers and suspensions.
     *
     * @return EmployeeFullDTO
     */
    public EmployeeFullDTO getEmployeeDTO() {
        Set<PassengerDTO> passengerDTOs = new HashSet<>();
        for (PassengerEN passenger : passengers) {
            passengerDTOs.add(passenger.getPassengerDTO());
        }
        boolean travelStatus = true;
        Set<SuspensionDTO> suspensionDTOs = new HashSet<>();
        for (SuspensionEN suspension : suspensions) {
            if (travelStatus) {
                travelStatus = !DateTimeUtil.isNowBetween(suspension.getBegin(), suspension.getEnd());
            }
            suspensionDTOs.add(suspension.getSuspensionDTO());
        }

        return new EmployeeFullDTO(getEmployeeId(), getSalutation(), getSuffix(), getFirstName(), getMiddleName(), getLastName(), getEmail(), getTitle(), getStatus(), DateTimeUtil.dateToLocalDate(getDob()), DateTimeUtil.dateToLocalDate(getLastHireDate()), DateTimeUtil.dateToLocalDate(getTermDate()), getPhone(), getJobCode(), getLocation(), getDepartmentId(), getDepartmentName(), getManagerId(), "", getEmployeeType(), travelStatus, passengerDTOs, suspensionDTOs, getGuestPassesAlloted(), getGuestPassesBooked(), getVacationPassesAlloted(), getVacationPassesUsed(), getSaCode() != null ? getSaCode().getSaCodeDTO() : null, getGender());
    }

    /**
     * Returns an instance of EmployeeBaseDTO.
     *
     * @return EmployeeBaseDTO
     */
    public EmployeeBaseDTO getEmployeeBaseDTO() {
        return new EmployeeBaseDTO(getEmployeeId(), getSalutation(), getSuffix(), getFirstName(), getMiddleName(), getLastName(), getEmail(), getTitle(), getStatus(), getGender(), getLocation());
    }

    /**
     * Returns an instance of EmployeePartialDTO.
     * This is the instance that will be returned to the search result
     *
     * @return EmployeePartialDTO
     */
    public EmployeePartialDTO getEmployeePartialDTO() {
        return new EmployeePartialDTO(getEmployeeId(), getSalutation(), getSuffix(), getFirstName(), getMiddleName(), getLastName(), getEmail(), getTitle(), getStatus(), DateTimeUtil.dateToLocalDate(getDob()), DateTimeUtil.dateToLocalDate(getLastHireDate()), DateTimeUtil.dateToLocalDate(getTermDate()), getPhone(), getJobCode(), getLocation(), getDepartmentId(), getDepartmentName(), getManagerId(), "", getEmployeeType(), getGender());
    }
}
