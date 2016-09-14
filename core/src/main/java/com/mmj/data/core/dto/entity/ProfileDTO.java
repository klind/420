package com.mmj.data.core.dto.entity;

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
    private boolean onPrescriptionMeds;
    private boolean vegetarian;
    private int avgOunceMeatDay;
    private int avgSexWeek;
    private int avgHoursSweatWeek;
    private int avgOuncePotThcWeek;
    private boolean preferSalivas;
    private boolean children;
    private boolean geneticItems;
    private int hotColdNormal;
    private boolean useNicotine;
    private int amountNicotineDay;
    private boolean hadMenopause;

    public ProfileDTO(Long id, String email, String firstName, String middleName, String lastName, Date dob, String phone, String gender, int weight, boolean onPrescriptionMeds, boolean vegetarian, int avgOunceMeatDay, int avgSexWeek, int avgHoursSweatWeek, int avgOuncePotThcWeek, boolean preferSalivas, boolean children, boolean geneticItems, int hotColdNormal, boolean useNicotine, int amountNicotineDay, boolean hadMenopause) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
        this.phone = phone;
        this.gender = gender;
        this.weight = weight;
        this.onPrescriptionMeds = onPrescriptionMeds;
        this.vegetarian = vegetarian;
        this.avgOunceMeatDay = avgOunceMeatDay;
        this.avgSexWeek = avgSexWeek;
        this.avgHoursSweatWeek = avgHoursSweatWeek;
        this.avgOuncePotThcWeek = avgOuncePotThcWeek;
        this.preferSalivas = preferSalivas;
        this.children = children;
        this.geneticItems = geneticItems;
        this.hotColdNormal = hotColdNormal;
        this.useNicotine = useNicotine;
        this.amountNicotineDay = amountNicotineDay;
        this.hadMenopause = hadMenopause;
    }

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

    public boolean isOnPrescriptionMeds() {
        return onPrescriptionMeds;
    }

    public void setOnPrescriptionMeds(boolean onPrescriptionMeds) {
        this.onPrescriptionMeds = onPrescriptionMeds;
    }

    public boolean isVegetarian() {
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

    public boolean isPreferSalivas() {
        return preferSalivas;
    }

    public void setPreferSalivas(boolean preferSalivas) {
        this.preferSalivas = preferSalivas;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public boolean isGeneticItems() {
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

    public boolean isUseNicotine() {
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

    public boolean isHadMenopause() {
        return hadMenopause;
    }

    public void setHadMenopause(boolean hadMenopause) {
        this.hadMenopause = hadMenopause;
    }
}
