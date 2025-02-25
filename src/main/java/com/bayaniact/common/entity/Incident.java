package com.bayaniact.common.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.bayaniact.util.incident.IncidentOfficialAssignment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @Column(name = "incident_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long incidentId;

    @NotBlank(message = "Incident title is required")
    @Column(name = "incident_title")
    private String incidentTitle;

    @NotBlank(message = "Incident Description is required")
    @Column(name = "incident_desc")
    private String incidentDesc;

    @NotBlank(message = "Incident Type is required")
    @Column(name = "incident_type")
    private String incidentType;

    @NotNull(message = "Incident Date is required")
    @Column(name = "incident_date")
    private LocalDate incidentDate;

    @NotNull(message = "Incident Time is required")
    @Column(name = "incident_time")
    private LocalTime incidentTime;

    @Column(name = "incident_status")
    private IncidentStatus incidentStatus;

    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IncidentOfficialAssignment> assignments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = true)
    private User user;

    @Column(name = "address")
    private String address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public enum IncidentStatus {
        OPEN, CLOSED, RESOLVED, CANCELLED;
    }

    /**
     * @return the incidentId
     */
    public Long getIncidentId() {
        return incidentId;
    }

    /**
     * @param incidentId the incidentId to set
     */
    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    /**
     * @return the incidentTitle
     */
    public String getIncidentTitle() {
        return incidentTitle;
    }

    /**
     * @param incidentTitle the incidentTitle to set
     */
    public void setIncidentTitle(String incidentTitle) {
        this.incidentTitle = incidentTitle;
    }

    /**
     * @return the incidentDesc
     */
    public String getIncidentDesc() {
        return incidentDesc;
    }

    /**
     * @param incidentDesc the incidentDesc to set
     */
    public void setIncidentDesc(String incidentDesc) {
        this.incidentDesc = incidentDesc;
    }

    /**
     * @return the incidentType
     */
    public String getIncidentType() {
        return incidentType;
    }

    /**
     * @param incidentType the incidentType to set
     */
    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    /**
     * @return the incidentDate
     */
    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    /**
     * @param incidentDate the incidentDate to set
     */
    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    /**
     * @return the incidentTime
     */
    public LocalTime getIncidentTime() {
        return incidentTime;
    }

    /**
     * @param incidentTime the incidentTime to set
     */
    public void setIncidentTime(LocalTime incidentTime) {
        this.incidentTime = incidentTime;
    }

    /**
     * @return the incidentStatus
     */
    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    /**
     * @param incidentStatus the incidentStatus to set
     */
    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public List<IncidentOfficialAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<IncidentOfficialAssignment> assignments) {
        this.assignments = assignments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
