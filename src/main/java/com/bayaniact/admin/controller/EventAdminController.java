package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/event")
public class EventAdminController {

    @Autowired
    private EventService eventService;

    @GetMapping("/form")
    public String getEventForm(Model model) {

        model.addAttribute("event", new Event());
        //model.addAttribute("content", "event-form");
        return "admin/event-form";
    }

    @PostMapping("/save")
    public String saveEvent(@ModelAttribute("event") Event event, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Print each error
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + " - " + error.getDefaultMessage());
            });

            return "admin/event-form";
        }
        eventService.save(event);

        return "redirect:/dashboard/event/list";
    }

    @GetMapping("/list")
    public String getEventList(Model model) {

        model.addAttribute("event", new Event());
        return "admin/event-list";
    }

    @PostMapping("/delete")
    public String deleteEvent(@RequestParam(name = "eventId") List<Long> eventId, RedirectAttributes redirectAttributes) {

        if (eventId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No Event selected for deletion.");
        } else {
            eventService.deleteEvent(eventId);
            redirectAttributes.addFlashAttribute("message", "Selected Brgy Official deleted successfully.");
        }

        return "redirect:/dashboard/event/list";
    }
}
