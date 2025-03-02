package com.bayaniact.common.repository;

import com.bayaniact.common.entity.BrgyOfficial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrgyOfficialRepository extends JpaRepository<BrgyOfficial, Long> {

    Optional<BrgyOfficial> findByPosition(String position);

    @Query("SELECT b.brgyOfficialId FROM BrgyOfficial b WHERE b.position = :position")
    Long findOfficialIdByPosition(@Param("position") String position);
}
