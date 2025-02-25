package com.bayaniact.common.repository;

import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByUser_UserUUID(String userUUID);
}

