package com.bayaniact.common.repository;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByUser_UserUUID(String userUUID);

    @Query("SELECT i FROM Incident i ORDER BY CASE WHEN i.incidentType = 'Priority' THEN 1 WHEN i.incidentType = 'Moderate' THEN 2 WHEN i.incidentType = 'Low' THEN 3  ELSE 4 END")
    Page<Incident> findAllSorted(Pageable pageable);

    Page<Incident> findByIncidentType(String status, Pageable pageable);


}

