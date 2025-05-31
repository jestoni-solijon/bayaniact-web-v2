package com.bayaniact.common.repository;

import com.bayaniact.common.entity.Blotter;
import com.bayaniact.common.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlotterRepository extends JpaRepository<Blotter, Long> {

    //List<Blotter> findByUser_UserUUID(String userUUID);

    //Page<Blotter> findByBlotterType(String status, Pageable pageable);

    List<Blotter> findByUser_UserUUID(String userUUID);

}
