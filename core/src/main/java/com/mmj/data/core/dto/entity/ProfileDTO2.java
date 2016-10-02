package com.mmj.data.core.dto.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mmj.data.core.constant.Constants;
import com.mmj.data.core.serializer.BooleanDeserializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

public class ProfileDTO2 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "email may not be null")
    @Pattern(regexp=Constants.EMAIL_REGEX, message = "email must match " + Constants.EMAIL_REGEX)
    private String email;

    @NotNull(message = "firstName may not be null")
    @Pattern(regexp=Constants.FIRST_NAME_REGEX, message = "firstName must match " + Constants.FIRST_NAME_REGEX)
    private String firstName;

    @Pattern(regexp=Constants.MIDDLE_NAME_REGEX, message = "middleName must match " + Constants.MIDDLE_NAME_REGEX)
    private String middleName;

    @NotNull(message = "lastName may not be null")
    @Pattern(regexp=Constants.LAST_NAME_REGEX, message = "lastName must match " + Constants.LAST_NAME_REGEX)
    private String lastName;

    //@NotNull(message = "dob may not be null")
    //@Pattern(regexp=Constants.DOB_REGEX)
    private LocalDate dob;

    @Pattern(regexp = Constants.PHONE_REGEX, message = "phone must match " + Constants.PHONE_REGEX)
    private String phone;

    @NotNull(message = "gender may not be null")
    @Pattern(regexp=Constants.EMPLOYEE_GENDER_REGEX, message = "gender must match " + Constants.EMPLOYEE_GENDER_REGEX)
    private String gender;

    /*@NotNull
    @Min(0)
    @Max(100)*/
    private int weight;

    @NotNull(message = "onPrescriptionMeds may not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean onPrescriptionMeds;

    @NotNull(message = "vegetarian may not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean vegetarian;

    /*@NotNull
    @Min(0)
    @Max(100)*/
    private int avgOunceMeatDay;

    /*@NotNull
    @Min(0)
    @Max(100)*/
    private int avgSexWeek;

    /*@NotNull
    @Min(0)
    @Max(100)*/
    private int avgHoursSweatWeek;

    /*@NotNull
    @Min(0)
    @Max(100)*/
    private int avgOuncePotThcWeek;

    @NotNull(message = "preferSalivas may not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean preferSalivas;

    @NotNull(message = "children may not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean children;

    @NotNull(message = "geneticItems may not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean geneticItems;
    /**
     * 1 = hot
     * 2 = cold
     * 3 = normal
     */
    /*@NotNull(message = "hotColdNormal may not be null")
    @Min(1)
    @Max(3)*/
    private int hotColdNormal;

    /*@NotNull(message = "amountNicotineDay may not be null")
    @Min(0)
    @Max(100)*/
    private int amountNicotineDay;

    @NotNull(message = "hadMenopause may not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean hadMenopause;

    @NotNull(message = "ageRange may not be null")
    private QuestionRangeDTO ageRange;

    @NotNull(message = "sleep may not be null")
    private QuestionRangeDTO sleep;

    @NotNull(message = "caffeineDrinks may not be null")
    private QuestionRangeDTO caffeineDrinks;

    @NotNull(message = "bowelMovement may not be null")
    private QuestionRangeDTO bowelMovement;

    @NotNull(message = "activityLevel may not be null")
    private QuestionRangeDTO activityLevel;

    @NotNull(message = "bodyfat may not be null")
    private QuestionRangeDTO bodyfat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean getOnPrescriptionMeds() {
        return onPrescriptionMeds;
    }

    public void setOnPrescriptionMeds(boolean onPrescriptionMeds) {
        this.onPrescriptionMeds = onPrescriptionMeds;
    }

    public boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public int getAvgOunceMeatDay() {
        return avgOunceMeatDay;
    }

    public void setAvgOunceMeatDay(int avgOunceMeatDay) {
        this.avgOunceMeatDay = avgOunceMeatDay;
    }

    public int getAvgSexWeek() {
        return avgSexWeek;
    }

    public void setAvgSexWeek(int avgSexWeek) {
        this.avgSexWeek = avgSexWeek;
    }

    public int getAvgHoursSweatWeek() {
        return avgHoursSweatWeek;
    }

    public void setAvgHoursSweatWeek(int avgHoursSweatWeek) {
        this.avgHoursSweatWeek = avgHoursSweatWeek;
    }

    public int getAvgOuncePotThcWeek() {
        return avgOuncePotThcWeek;
    }

    public void setAvgOuncePotThcWeek(int avgOuncePotThcWeek) {
        this.avgOuncePotThcWeek = avgOuncePotThcWeek;
    }

    public boolean getPreferSalivas() {
        return preferSalivas;
    }

    public void setPreferSalivas(boolean preferSalivas) {
        this.preferSalivas = preferSalivas;
    }

    public boolean getChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public boolean getGeneticItems() {
        return geneticItems;
    }

    public void setGeneticItems(boolean geneticItems) {
        this.geneticItems = geneticItems;
    }

    public int getHotColdNormal() {
        return hotColdNormal;
    }

    public void setHotColdNormal(int hotColdNormal) {
        this.hotColdNormal = hotColdNormal;
    }

    public int getAmountNicotineDay() {
        return amountNicotineDay;
    }

    public void setAmountNicotineDay(int amountNicotineDay) {
        this.amountNicotineDay = amountNicotineDay;
    }

    public boolean getHadMenopause() {
        return hadMenopause;
    }

    public void setHadMenopause(boolean hadMenopause) {
        this.hadMenopause = hadMenopause;
    }

    public QuestionRangeDTO getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(QuestionRangeDTO ageRange) {
        this.ageRange = ageRange;
    }

    public QuestionRangeDTO getSleep() {
        return sleep;
    }

    public void setSleep(QuestionRangeDTO sleep) {
        this.sleep = sleep;
    }

    public QuestionRangeDTO getCaffeineDrinks() {
        return caffeineDrinks;
    }

    public void setCaffeineDrinks(QuestionRangeDTO caffeineDrinks) {
        this.caffeineDrinks = caffeineDrinks;
    }

    public QuestionRangeDTO getBowelMovement() {
        return bowelMovement;
    }

    public void setBowelMovement(QuestionRangeDTO bowelMovement) {
        this.bowelMovement = bowelMovement;
    }

    public QuestionRangeDTO getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(QuestionRangeDTO activityLevel) {
        this.activityLevel = activityLevel;
    }

    public QuestionRangeDTO getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(QuestionRangeDTO bodyfat) {
        this.bodyfat = bodyfat;
    }
}
