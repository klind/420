package com.mmj.data.ejb.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "profile")
public class ProfileEN implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 50)
    @Length(min = 5, max = 50)
    @NotNull
    private String email;

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

    @Column(name = "phone", length = 30)
    //@NotNull
    private String phone;

    @Column(name = "gender", length = 1)
    @NotNull
    private String gender;


}
