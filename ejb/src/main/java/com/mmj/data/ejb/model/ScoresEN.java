package com.mmj.data.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "question_ranges")
public class ScoresEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", length = 25)
    @NotNull
    private String category;

    @NotNull
    private int lower;

    @NotNull
    private int upper;

    @Column(name = "score")
    @NotNull
    private BigDecimal score;

}
