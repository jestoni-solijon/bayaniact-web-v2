package com.bayaniact.util.incident;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IncidentOfficialAssignmentId implements Serializable {
    private Long incidentId;
    private Long brgyOfficialId;

    // Default constructor
    public IncidentOfficialAssignmentId() {}

    public IncidentOfficialAssignmentId(Long incidentId, Long brgyOfficialId) {
        this.incidentId = incidentId;
        this.brgyOfficialId = brgyOfficialId;
    }

    // Getters and setters
    public Long getIncidentId() { return incidentId; }
    public void setIncidentId(Long incidentId) { this.incidentId = incidentId; }

    public Long getBrgyOfficialId() { return brgyOfficialId; }
    public void setBrgyOfficialId(Long brgyOfficialId) { this.brgyOfficialId = brgyOfficialId; }

    // Override equals() and hashCode() for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncidentOfficialAssignmentId that = (IncidentOfficialAssignmentId) o;
        return Objects.equals(incidentId, that.incidentId) &&
                Objects.equals(brgyOfficialId, that.brgyOfficialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incidentId, brgyOfficialId);
    }
}


