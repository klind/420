package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.SuspensionCommentDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Comparator;

@Entity
@Table(name = "suspension_comment")
public class SuspensionCommentEN {

    public static final Comparator<SuspensionCommentEN> ID_COMPARATOR = new Comparator<SuspensionCommentEN>() {
        @Override public int compare(SuspensionCommentEN suspensionEN1, SuspensionCommentEN suspensionEN2) {
            return suspensionEN1.getId().compareTo(suspensionEN2.getId());
        }
    };

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "suspension_id")
    private SuspensionEN suspension;

    @Column(name = "comment", length = 255)
    @NotNull
    private String comment;

    @Column(name = "created", columnDefinition = "TimeStamp")
    @NotNull
    private Timestamp created;

    @Column(name = "userid", length = 6)
    @NotNull
    private String userid;

    @Column(name = "username", length = 50)
    @NotNull
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuspensionEN getSuspension() {
        return suspension;
    }

    public void setSuspension(SuspensionEN suspension) {
        this.suspension = suspension;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SuspensionCommentDTO getSuspensionCommentDTO() {
        return new SuspensionCommentDTO(getId(), getSuspension().getId(), getComment(), getCreated(), getUserid(), getUsername());
    }
}
