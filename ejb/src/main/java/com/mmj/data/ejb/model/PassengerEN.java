package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.util.DateTimeUtil;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.Comparator;
import java.util.Date;

@Entity
@Table(name = "passenger")
public class PassengerEN {
    private static final Logger LOG = LoggerFactory.getLogger(PassengerEN.class);

    public static final Comparator<PassengerEN> ID_COMPARATOR = new Comparator<PassengerEN>() {
        @Override public int compare(PassengerEN passengerEN1, PassengerEN passengerEN2) {
            return passengerEN1.getId().compareTo(passengerEN2.getId());
        }
    };

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEN employee;

    @Column(name = "employee_id", nullable = false, insertable = false, updatable = false)
    private String employeeId;

    @Column(name = "first_name", length = 30)
    @Length(max = 30)
    @NotNull
    private String firstName;

    @Column(name = "middle_name", length = 30)
    @Length(max = 30)
    private String middleName;

    @Column(name = "last_name", length = 30)
    @Length(max = 30)
    @NotNull
    private String lastName;

    @Column(name = "gender", length = 1)
    @Length(min = 1, max = 1)
    @NotNull
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "relationship", referencedColumnName = "id")
    @NotNull
    private TravelerRelationshipEN relationship;

    @Column(name = "dob")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "phone", length = 10)
    private Long phone;

    @Column(name = "verified", columnDefinition = "BIT", length = 1)
    private boolean verified;

    @Column(name = "re_address")
    private String reAddress;

    @Column(name = "known_traveler_number")
    private String knownTravelerNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeEN getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEN employee) {
        this.employee = employee;
    }

    public String getEmployeeId() {
        return employeeId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public TravelerRelationshipEN getRelationship() {
        return relationship;
    }

    public void setRelationship(TravelerRelationshipEN relationship) {
        this.relationship = relationship;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getKnownTravelerNumber() {
        return knownTravelerNumber;
    }

    public void setKnownTravelerNumber(String knownTravelerNumber) {
        this.knownTravelerNumber = knownTravelerNumber;
    }

    public boolean getVerified() {
        return verified;
    }

    public PassengerDTO getPassengerDTO() {
        return new PassengerDTO(getId(), getEmployeeId(), getFirstName(), getMiddleName(), getLastName(), getGender(), getRelationship().getTravelerRelationshipDTO(), DateTimeUtil.dateToLocalDate(getDob()), getPhone(), getReAddress(), getKnownTravelerNumber(), getVerified());
    }

}
