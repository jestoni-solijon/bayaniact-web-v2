package com.bayaniact.common.repository;

import com.bayaniact.common.entity.Request;
import com.bayaniact.common.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByResident(Resident resident);
}
