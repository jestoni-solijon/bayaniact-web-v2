package com.bayaniact.common.service;

import com.bayaniact.common.email.EmailService;
import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.util.incident.IncidentOfficialAssignment;
import com.bayaniact.util.incident.IncidentOfficialAssignmentId;
import com.bayaniact.util.incident.IncidentOfficialAssignmentRepository;
import com.bayaniact.common.repository.BrgyOfficialRepository;
import com.bayaniact.common.repository.IncidentRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    @Autowired private IncidentRepository incidentRepository;
    @Autowired private BrgyOfficialRepository brgyOfficialRepository;
    @Autowired private IncidentOfficialAssignmentRepository assignmentRepository;
    @Autowired private EmailService emailService;

    public Incident save(Incident incident) {
        return incidentRepository.save(incident);
    }

    public List<Incident> findAll() { return incidentRepository.findAll(); }

    public void deleteById(List<Long> incidentId) { incidentRepository.deleteAllById(incidentId);}

    public void assignIncidentToBrgyOfficial(Long incidentId, Long brgyOfficialId) throws MessagingException {
        Optional<Incident> incidentOptional = incidentRepository.findById(incidentId);
        Optional<BrgyOfficial> officialOptional = brgyOfficialRepository.findById(brgyOfficialId);

        if (incidentOptional.isPresent() && officialOptional.isPresent()) {
            Incident incident = incidentOptional.get();
            BrgyOfficial brgyOfficial = officialOptional.get();

            // Explicitly create an embedded ID
            IncidentOfficialAssignmentId assignmentId = new IncidentOfficialAssignmentId();
            assignmentId.setIncidentId(incident.getIncidentId());  // Ensure this matches entity field names
            assignmentId.setBrgyOfficialId(brgyOfficial.getBrgyOfficialId());

            // Create a new assignment
            IncidentOfficialAssignment assignment = new IncidentOfficialAssignment();
            assignment.setId(assignmentId); // Set composite key
            assignment.setIncident(incident);
            assignment.setBrgyOfficial(brgyOfficial);
            assignment.setAssignedAt(LocalDateTime.now());

            if (!brgyOfficial.getEmailAddress().isEmpty()) {
                emailService.sendIncidentOficialAssignment(brgyOfficial, incident);
            }
            // Save the assignment
            assignmentRepository.save(assignment);
        } else {
            throw new RuntimeException("Incident or BrgyOfficial not found");
        }
    }

    public List<Incident> findIncidentByUserUUID(String userUUID) {
        return incidentRepository.findByUser_UserUUID(userUUID);
    }

    public Page<Incident> findAll(Pageable pageable) {
        return incidentRepository.findAllSorted(pageable);
    }

    public Page<Incident> findByIncidentType(String status, Pageable pageable) {
        return incidentRepository.findByIncidentType(status, pageable);
    }

}

