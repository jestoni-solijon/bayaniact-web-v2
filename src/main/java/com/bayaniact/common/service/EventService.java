package com.bayaniact.common.service;

import java.util.List;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void save(Event event) {
        eventRepository.save(event);
    }
    public List<Event> getEnabledEvents() {
        return eventRepository.findEnabledEvents(1);
    }

    public List<Event> findAll() { return eventRepository.findAll(); }

    public void deleteEvent(List<Long> eventId) {
        eventRepository.deleteAllById(eventId);
    }


}
