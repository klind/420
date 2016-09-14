package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.GuestDTO;
import com.mmj.data.core.util.DateTimeUtil;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Guest Entity.
 */
@Entity
@Table(name = "guest")
public class GuestEN implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @NotNull
    private EmployeeEN employee;

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

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dob;

    @Column(name = "gender", length = 1)
    @NotNull
    private String gender;

    @Column(name = "knowntravelernumber", length = 30)
    private String knownTravelerNumber;

    public Long getId() {
        return id;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKnownTravelerNumber() {
        return knownTravelerNumber;
    }

    public void setKnownTravelerNumber(String knownTravelerNumber) {
        this.knownTravelerNumber = knownTravelerNumber;
    }

    public EmployeeEN getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEN employee) {
        this.employee = employee;
    }

    /**
     * @return GuestDTO
     */
    public GuestDTO getGuestDTO() {
        return new GuestDTO(getId(), getEmployee().getEmployeeId(), getFirstName(), getMiddleName(), getLastName(), DateTimeUtil.dateToLocalDate(getDob()), getGender(), getKnownTravelerNumber());
    }
}
