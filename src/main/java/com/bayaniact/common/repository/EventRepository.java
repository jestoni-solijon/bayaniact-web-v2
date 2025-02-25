package com.bayaniact.common.repository;

import java.util.List;

import com.bayaniact.common.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM events WHERE enabled = :enabled", nativeQuery = true)
    List<Event> findEnabledEvents(@Param("enabled") int enabled);

}
