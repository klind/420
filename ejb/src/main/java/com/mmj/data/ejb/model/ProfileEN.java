package com.mmj.data.ejb.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "profile",
        uniqueConstraints=
        @UniqueConstraint(name = "UQ_EMAIL", columnNames={"email"}))
public class ProfileEN implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name="profile_survey",
            joinColumns=@JoinColumn(name="profile_id", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="survey_id", referencedColumnName="ID"))
    private List<SurveyEN> surveys = new ArrayList<>();


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

    @Column(name = "phone", length = 30)
    //@NotNull
    private String phone;

    @Column(name = "gender", length = 1)
    @NotNull
    private String gender;

    @NotNull
    private int weight;

    private BigDecimal score;

    @Column(name = "on_prescription_meds", columnDefinition = "BIT", length = 1)
    @NotNull
    /**
     * are you on prescription meds? y= time broadens by 10%
     */
    private Boolean onPrescriptionMeds;

    @Column(columnDefinition = "BIT", length = 1)
    @NotNull
    /**
     * are you a vegetarian = if Y=time shrinks by 10%
     */
    private Boolean vegetarian;

    @Column(name = "avg_ounce_meat_day")
    @NotNull
    /**
     * avg ounces of meat consumed daily 16+oz = time increases by 10%
     */
    private int avgOunceMeatDay;

    @Column(name = "avg_sex_week")
    @NotNull
    /**
     * average # of sexual release/week - move than 4 = time decreases by 10%
     */
    private int avgSexWeek;

    @Column(name = "avg_hours_sweat_week")
    @NotNull
    /**
     * avg hours of exercise to a sweat each week - more than 2 hours= time decreases by 15%
     */
    private int avgHoursSweatWeek;

    @Column(name = "avg_ounce_pot_thc_week")
    @NotNull
    /**
     * avg amount (in ounces) of Pot/THC you consume weekly - over 1/2oz time decreases by 10%
     */
    private int avgOuncePotThcWeek;

    @Column(name = "prefer_salivas", columnDefinition = "BIT", length = 1)
    @NotNull

    /**
     * do you typically prefer indices or salivas or hybrid (i don’t know = 0) - no weight, just informational - no part of calculation
     */
    private Boolean preferSalivas;

    @Column(columnDefinition = "BIT", length = 1)
    @NotNull
    /**
     * do you have children? - for Men, this doesn’t get calculated, for women it does.  if =y time decreases by 10%
     */
    private Boolean children;

    @Column(name = "generic_items", columnDefinition = "BIT", length = 1)
    @NotNull
    /**
     * any genetic items that affect metabolism? - no weight or calculation, for informational collection purposes
     */
    private Boolean geneticItems;

    @Column(name = "hot_cold_normal")
    @NotNull
    /**
     * are you generally hot/cold/normal - for hot or cold, time increases by 10%
     */
    private String hotColdNormal;

    @Column(name = "amount_nicotine_day")
    @NotNull
    /**
     * how much nicotine do you use/day - over ½ pack/day decreases time by 10%
     */
    private int amountNicotineDay;

    @Column(name = "had_menopause", columnDefinition = "BIT", length = 1)
    @NotNull
    /**
     * (Women only) Have you gone through menopause? if y=time increases by 10%
     */
    private Boolean hadMenopause;

    @NotNull
    private Integer ageRange;

    @NotNull
    private Integer sleep;

    @NotNull
    private Integer caffeineDrinks;

    @NotNull
    private Integer bowelMovement;

    @NotNull
    private Integer activityLevel;

    @NotNull
    private Integer bodyfat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SurveyEN> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyEN> surveys) {
        this.surveys = surveys;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
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
