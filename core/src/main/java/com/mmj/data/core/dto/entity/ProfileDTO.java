package com.mmj.data.core.dto.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mmj.data.core.constant.Constants;
import com.mmj.data.core.serializer.BooleanDeserializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

public class ProfileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "email may not be null")
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "email must match " + Constants.EMAIL_REGEX)
    private String email;

    @NotNull(message = "firstName may not be null")
    @Pattern(regexp = Constants.FIRST_NAME_REGEX, message = "firstName must match " + Constants.FIRST_NAME_REGEX)
    private String firstName;

    @Pattern(regexp = Constants.MIDDLE_NAME_REGEX, message = "middleName must match " + Constants.MIDDLE_NAME_REGEX)
    private String middleName;

    @NotNull(message = "lastName may not be null")
    @Pattern(regexp = Constants.LAST_NAME_REGEX, message = "lastName must match " + Constants.LAST_NAME_REGEX)
    private String lastName;

    @Pattern(regexp = Constants.PHONE_REGEX, message = "phone must match " + Constants.PHONE_REGEX)
    private String phone;

    @NotNull(message = "gender may not be null")
    @Pattern(regexp = Constants.EMPLOYEE_GENDER_REGEX, message = "gender must match " + Constants.EMPLOYEE_GENDER_REGEX)
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
     * H = hot
     * C = cold
     * N = normal
     */
    /*@NotNull(message = "hotColdNormal may not be null")
    @Min(1)
    @Max(3)*/
    private String hotColdNormal;

    /*@NotNull(message = "amountNicotineDay may not be null")
    @Min(0)
    @Max(100)*/
    private int amountNicotineDay;

    @NotNull(message = "hadMenopause may not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean hadMenopause;

    @NotNull(message = "ageRange may not be null")
    private Integer ageRange;

    @NotNull(message = "sleep may not be null")
    private Integer sleep;

    @NotNull(message = "caffeineDrinks may not be null")
    private Integer caffeineDrinks;

    @NotNull(message = "bowelMovement may not be null")
    private Integer bowelMovement;

    @NotNull(message = "activityLevel may not be null")
    private Integer activityLevel;

    @NotNull(message = "bodyfat may not be null")
    private Integer bodyfat;

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

    public Boolean getOnPrescriptionMeds() {
        return onPrescriptionMeds;
    }

    public void setOnPrescriptionMeds(Boolean onPrescriptionMeds) {
        this.onPrescriptionMeds = onPrescriptionMeds;
    }

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
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

    public Boolean getPreferSalivas() {
        return preferSalivas;
    }

    public void setPreferSalivas(Boolean preferSalivas) {
        this.preferSalivas = preferSalivas;
    }

    public Boolean getChildren() {
        return children;
    }

    public void setChildren(Boolean children) {
        this.children = children;
    }

    public Boolean getGeneticItems() {
        return geneticItems;
    }

    public void setGeneticItems(Boolean geneticItems) {
        this.geneticItems = geneticItems;
    }

    public String getHotColdNormal() {
        return hotColdNormal;
    }

    public void setHotColdNormal(String hotColdNormal) {
        this.hotColdNormal = hotColdNormal;
    }

    public int getAmountNicotineDay() {
        return amountNicotineDay;
    }

    public void setAmountNicotineDay(int amountNicotineDay) {
        this.amountNicotineDay = amountNicotineDay;
    }

    public Boolean getHadMenopause() {
        return hadMenopause;
    }

    public void setHadMenopause(Boolean hadMenopause) {
        this.hadMenopause = hadMenopause;
    }

    public Integer getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(Integer ageRange) {
        this.ageRange = ageRange;
    }

    public Integer getSleep() {
        return sleep;
    }

    public void setSleep(Integer sleep) {
        this.sleep = sleep;
    }

    public Integer getCaffeineDrinks() {
        return caffeineDrinks;
    }

    public void setCaffeineDrinks(Integer caffeineDrinks) {
        this.caffeineDrinks = caffeineDrinks;
    }

    public Integer getBowelMovement() {
        return bowelMovement;
    }

    public void setBowelMovement(Integer bowelMovement) {
        this.bowelMovement = bowelMovement;
    }

    public Integer getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Integer activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Integer getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(Integer bodyfat) {
        this.bodyfat = bodyfat;
    }
}
