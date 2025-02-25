package com.bayaniact.common.repository;

import java.time.LocalDate;

import com.bayaniact.common.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface ResidentRepository extends JpaRepository<Resident, Long>, PagingAndSortingRepository<Resident, Long> {

    Page<Resident> findByLastName(String lastName, PageRequest pageRequest);

    boolean existsByResidentId(Long residentId);

    Page<Resident> findByStatus(Byte status, Pageable pageable);

    @Query("SELECT r FROM Resident r WHERE " +
            "(:firstName IS NULL OR r.firstName LIKE %:firstName%) AND " +
            "(:lastName IS NULL OR r.lastName LIKE %:lastName%) AND " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:middleName IS NULL OR r.middleName LIKE %:middleName%) AND " +
            "(:birthDate IS NULL OR r.birthDate = :birthDate)")
    Page<Resident> findByFilters(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("status") String status,
            @Param("middleName") String middleName,
            @Param("birthDate") LocalDate birthDate,
            Pageable pageable);

    Resident findByUser_UserUUID(String userUUID);
}
