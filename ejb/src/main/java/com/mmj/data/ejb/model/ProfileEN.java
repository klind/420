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

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dob;

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
    private int hotColdNormal;

    @Column(name = "use_nicotine", columnDefinition = "BIT", length = 1)
    @NotNull
    /**
     * Do you use nicotine?  if Y then next question
     */
    private Boolean useNicotine;

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

    @ManyToOne(cascade = CascadeType.DETACH)
    @NotNull
    private QuestionRangeEN ageRange;

    @ManyToOne(cascade = CascadeType.DETACH)
    @NotNull
    private QuestionRangeEN sleep;

    @ManyToOne(cascade = CascadeType.DETACH)
    @NotNull
    private QuestionRangeEN caffeineDrinks;

    @ManyToOne(cascade = CascadeType.DETACH)
    @NotNull
    private QuestionRangeEN bowelMovement;

    @ManyToOne(cascade = CascadeType.DETACH)
    @NotNull
    private QuestionRangeEN activityLevel;

    @ManyToOne(cascade = CascadeType.DETACH)
    @NotNull
    private QuestionRangeEN bodyfat;


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

    public BigDecimal getScore() {
        return score;
    }

    public void  setScore(BigDecimal score) {
        this.score = score;
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

    public QuestionRangeEN getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(QuestionRangeEN ageRange) {
        this.ageRange = ageRange;
    }

    public QuestionRangeEN getSleep() {
        return sleep;
    }

    public void setSleep(QuestionRangeEN sleep) {
        this.sleep = sleep;
    }

    public QuestionRangeEN getCaffeineDrinks() {
        return caffeineDrinks;
    }

    public void setCaffeineDrinks(QuestionRangeEN caffeineDrinks) {
        this.caffeineDrinks = caffeineDrinks;
    }

    public QuestionRangeEN getBowelMovement() {
        return bowelMovement;
    }

    public void setBowelMovement(QuestionRangeEN bowelMovement) {
        this.bowelMovement = bowelMovement;
    }

    public QuestionRangeEN getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(QuestionRangeEN activityLevel) {
        this.activityLevel = activityLevel;
    }

    public QuestionRangeEN getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(QuestionRangeEN bodyfat) {
        this.bodyfat = bodyfat;
    }
}
