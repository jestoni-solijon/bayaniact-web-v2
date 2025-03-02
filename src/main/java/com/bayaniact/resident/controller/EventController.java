package com.bayaniact.resident.controller;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public String getEventList(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               @RequestParam(name = "status", required = false) Integer status,
                               Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage;

        if (status != null) {
            eventsPage = eventService.findByStatus(status, pageable); // Fetch filtered events
        } else {
            eventsPage = eventService.findAll(pageable); // Fetch all events
        }

        model.addAttribute("event", new Event()); // Event object
        model.addAttribute("events", eventsPage); // Paginated event list
        model.addAttribute("selectedStatus", status); // Preserve selected status in the dropdown

        return "resident/event";
    }

}