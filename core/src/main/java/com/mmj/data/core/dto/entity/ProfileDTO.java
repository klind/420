package com.mmj.data.core.dto.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mmj.data.core.serializer.BooleanDeserializer;

import java.io.Serializable;
import java.util.Date;

public class ProfileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;
    private String phone;
    private String gender;
    private int weight;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean onPrescriptionMeds;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean vegetarian;
    private int avgOunceMeatDay;
    private int avgSexWeek;
    private int avgHoursSweatWeek;
    private int avgOuncePotThcWeek;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean preferSalivas;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean children;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean geneticItems;
    /**
     * 1 = hot
     * 2 = cold
     * 3 = normal
     */
    private int hotColdNormal;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean useNicotine;
    private int amountNicotineDay;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean hadMenopause;
    private QuestionRangesDTO ageRange;
    private QuestionRangesDTO sleep;
    private QuestionRangesDTO caffeineDrinks;
    private QuestionRangesDTO bowelMovement;
    private QuestionRangesDTO activityLevel;
    private QuestionRangesDTO bodyfat;

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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

    public boolean getUseNicotine() {
        return useNicotine;
    }

    public void setUseNicotine(boolean useNicotine) {
        this.useNicotine = useNicotine;
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

    public QuestionRangesDTO getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(QuestionRangesDTO ageRange) {
        this.ageRange = ageRange;
    }

    public QuestionRangesDTO getSleep() {
        return sleep;
    }

    public void setSleep(QuestionRangesDTO sleep) {
        this.sleep = sleep;
    }

    public QuestionRangesDTO getCaffeineDrinks() {
        return caffeineDrinks;
    }

    public void setCaffeineDrinks(QuestionRangesDTO caffeineDrinks) {
        this.caffeineDrinks = caffeineDrinks;
    }

    public QuestionRangesDTO getBowelMovement() {
        return bowelMovement;
    }

    public void setBowelMovement(QuestionRangesDTO bowelMovement) {
        this.bowelMovement = bowelMovement;
    }

    public QuestionRangesDTO getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(QuestionRangesDTO activityLevel) {
        this.activityLevel = activityLevel;
    }

    public QuestionRangesDTO getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(QuestionRangesDTO bodyfat) {
        this.bodyfat = bodyfat;
    }
}
