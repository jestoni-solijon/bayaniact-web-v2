package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.file.EventFileService;
import com.bayaniact.common.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/dashboard/event")
public class EventAdminController {

    @Autowired private EventService eventService;
    @Autowired private EventFileService eventFileService;

    @GetMapping("/form")
    public String getEventForm(Model model) {

        model.addAttribute("event", new Event());
        //model.addAttribute("content", "event-form");
        return "admin/event-form";
    }

    @PostMapping("/save")
    public String saveEvent(@ModelAttribute("event") Event event,
                            @RequestParam(name = "file", required = false) MultipartFile file,
                            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            // Print each error
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + " - " + error.getDefaultMessage());
            });

            return "admin/event-form";
        }
        Event savedEvent = eventService.save(event);

        if (file != null && !file.isEmpty()) {
            eventFileService.saveFile(file, savedEvent);
        }

        return "redirect:/dashboard/event/list";
    }

    /*@GetMapping("/list")
    public String getEventList(Model model) {

        model.addAttribute("event", new Event());
        return "admin/event-list";
    }*/

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
