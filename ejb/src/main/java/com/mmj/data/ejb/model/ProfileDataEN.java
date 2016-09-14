package com.mmj.data.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "profile_data")
public class ProfileDataEN implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private int weight;

    @Column(name = "on_prescription_meds")
    @NotNull
    /**
     * are you on prescription meds? y= time broadens by 10%
     */
    private boolean onPrescriptionMeds;

    @NotNull
    /**
     * are you a vegetarian = if Y=time shrinks by 10%
     */
    private boolean vegetarian;

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

    @Column(name = "prefer_salivas")
    @NotNull
    /**
     * do you typically prefer indices or salivas or hybrid (i don’t know = 0) - no weight, just informational - no part of calculation
     */
    private boolean preferSalivas;

    @NotNull
    /**
     * do you have children? - for Men, this doesn’t get calculated, for women it does.  if =y time decreases by 10%
     */
    private boolean children;

    @Column(name = "generic_items")
    @NotNull
    /**
     * any genetic items that affect metabolism? - no weight or calculation, for informational collection purposes
     */
    private boolean geneticItems;

    @Column(name = "hot_cold_normal")
    @NotNull
    /**
     * are you generally hot/cold/normal - for hot or cold, time increases by 10%
     */
    private int hotColdNormal;

    @Column(name = "use_nicotine")
    @NotNull
    /**
     * Do you use nicotine?  if Y then next question
     */
    private boolean useNicotine;

    @Column(name = "amount_nicotine_day")
    @NotNull
    /**
     * how much nicotine do you use/day - over ½ pack/day decreases time by 10%
     */
    private int amountNicotineDay;

    @Column(name = "had_menopause")
    @NotNull
    /**
     * (Women only) Have you gone through menopause? if y=time increases by 10%
     */
    private boolean hadMenopause;


    private ScoresEN ageRange;
    private ScoresEN sleep;
    private ScoresEN caffeineDrinks;
    private ScoresEN bowelMovement;
    private ScoresEN activityLevel;
    private ScoresEN bodyfat;




}
