package com.mmj.data.core.dto.entity;

import com.mmj.data.core.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SuspensionDTO implements Serializable {
    private Long id;
    @NotNull(message = "begin may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate begin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate end;
    private SuspensionTypeDTO suspensionType;
    @Valid
    private List<SuspensionCommentDTO> comments = new ArrayList<>();
    // employeeId is a string because of leading 0s.
    @NotNull(message = "employeeId may not be null")
    @Pattern(regexp= Constants.EMPLOYEE_ID_REGEX, message = "employeeId must match " + Constants.EMPLOYEE_ID_REGEX)
    private String employeeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Timestamp created;
    private String userid;
    private String username;

    public SuspensionDTO() {
    }

    public SuspensionDTO(Long id, LocalDate begin, LocalDate end, SuspensionTypeDTO suspensionType, List<SuspensionCommentDTO> comments, String employeeId, Timestamp created, String userid, String username) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.suspensionType = suspensionType;
        this.comments = comments;
        this.employeeId = employeeId;
        this.created = created;
        this.userid = userid;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public SuspensionTypeDTO getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(SuspensionTypeDTO suspensionType) {
        this.suspensionType = suspensionType;
    }

    public List<SuspensionCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<SuspensionCommentDTO> comments) {
        this.comments = comments;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public void addSuspensionComment(SuspensionCommentDTO suspensionCommentDTO) {
        comments.add(suspensionCommentDTO);
    }
}
