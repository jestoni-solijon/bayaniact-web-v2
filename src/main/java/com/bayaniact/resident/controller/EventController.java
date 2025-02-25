package com.bayaniact.resident.controller;

import com.bayaniact.common.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public String getEventList(Model model) {

        model.addAttribute("events", eventService.findAll());

        return "resident/event";
    }
}