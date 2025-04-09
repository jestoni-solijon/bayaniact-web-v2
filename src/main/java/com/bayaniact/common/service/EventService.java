package com.bayaniact.common.service;

import java.util.Arrays;
import java.util.List;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getEnabledEvents() {
        return eventRepository.findEnabledEvents(1);
    }

    public List<Event> findAll() { return eventRepository.findAll(); }

    public Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public void deleteEvent(List<Long> eventId) {
        eventRepository.deleteAllById(eventId);
    }

    public Page<Event> findByStatus(Integer status, Pageable pageable) {
        return eventRepository.findByEventStatus(status, pageable);
    }

    public List<Event> findAllActiveEvents() {
        // Fetch events where status is 0 (Upcoming) or 1 (Ongoing)
        return eventRepository.findByEventStatusIn(Arrays.asList(0, 1));
    }

    public List<Event> findAll(Sort sort) {
        return eventRepository.findAll(sort);
    }

}
