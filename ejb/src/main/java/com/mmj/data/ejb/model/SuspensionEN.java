package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.SuspensionCommentDTO;
import com.mmj.data.core.dto.entity.SuspensionDTO;
import com.mmj.data.core.util.DateTimeUtil;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "suspension")
public class SuspensionEN {

    public static final Comparator<SuspensionEN> ID_COMPARATOR = new Comparator<SuspensionEN>() {
        @Override public int compare(SuspensionEN suspensionEN1, SuspensionEN suspensionEN2) {
            return suspensionEN1.getId().compareTo(suspensionEN2.getId());
        }
    };

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEN employee;

    @Column(name = "begin")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date begin;

    @Column(name = "end")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "suspensionType", referencedColumnName = "id")
    private SuspensionTypeEN suspensionType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suspension", fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<SuspensionCommentEN> comments = new HashSet<SuspensionCommentEN>();

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

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public SuspensionTypeEN getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(SuspensionTypeEN suspensionType) {
        this.suspensionType = suspensionType;
    }

    public Set<SuspensionCommentEN> getComments() {
        return comments;
    }

    public void setComments(Set<SuspensionCommentEN> comments) {
        this.comments = comments;
    }

    public EmployeeEN getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEN employee) {
        this.employee = employee;
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

    public void addSuspensionComment(SuspensionCommentEN suspensionCommentEN) {
        comments.add(suspensionCommentEN);
    }

    public SuspensionDTO getSuspensionDTO() {
        List<SuspensionCommentDTO> suspensionCommentDTOs = new ArrayList<>();
        Set<SuspensionCommentEN> comments = getComments();
        for (SuspensionCommentEN comment : comments) {
            suspensionCommentDTOs.add(comment.getSuspensionCommentDTO());
        }
        return new SuspensionDTO(getId(), DateTimeUtil.dateToLocalDate(getBegin()), DateTimeUtil.dateToLocalDate(getEnd()), getSuspensionType().getSuspensionTypeDTO(), suspensionCommentDTOs, getEmployee().getEmployeeId(), getCreated(), getUserid(), getUsername());
    }

    public SuspensionDTO getSuspensionDTOWithoutComments(String employeeId) {
        // Suspension comments are sensitive data, so we pass in null instead of real comments.
        return new SuspensionDTO(getId(), DateTimeUtil.dateToLocalDate(getBegin()), DateTimeUtil.dateToLocalDate(getEnd()), getSuspensionType().getSuspensionTypeDTO(), null, employeeId, getCreated(), getUserid(), getUsername());
    }



}
