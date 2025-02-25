package com.bayaniact.util.incident;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.entity.Incident;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_official_assignment")
public class IncidentOfficialAssignment {

    @EmbeddedId
    private IncidentOfficialAssignmentId id;

    @ManyToOne
    @MapsId("incidentId")
    @JoinColumn(name = "incident_id")
    private Incident incident;

    @ManyToOne
    @MapsId("brgyOfficialId")
    @JoinColumn(name = "brgy_official_id")
    private BrgyOfficial brgyOfficial;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    public IncidentOfficialAssignmentId getId() {
        return id;
    }

    public void setId(IncidentOfficialAssignmentId id) {
        this.id = id;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public BrgyOfficial getBrgyOfficial() {
        return brgyOfficial;
    }

    public void setBrgyOfficial(BrgyOfficial brgyOfficial) {
        this.brgyOfficial = brgyOfficial;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
