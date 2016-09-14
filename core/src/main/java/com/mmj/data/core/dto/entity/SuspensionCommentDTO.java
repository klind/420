package com.mmj.data.core.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class SuspensionCommentDTO {

    private Long id;
    private Long suspensionId;
    @NotNull(message = "comment may not be null")
    @Size(message = "comment must be between 1 and 255 characters.", min = 1, max = 255)
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Timestamp created;
    private String userid;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuspensionCommentDTO() {
    }

    public SuspensionCommentDTO(Long id, Long suspensionId, String comment, Timestamp created, String userid, String username) {
        this.id = id;
        this.suspensionId = suspensionId;
        this.comment = comment;
        this.created = created;
        this.userid = userid;
        this.username = username;
    }

    public Long getSuspensionId() {
        return suspensionId;
    }

    public void setSuspensionId(Long suspensionId) {
        this.suspensionId = suspensionId;
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
}
