package com.bayaniact.resident.controller;

import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.entity.User;
import com.bayaniact.common.security.UserService;
import com.bayaniact.common.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/incident")
public class IncidentController {

    private static final String RESIDENT_INCIDENT_FORM_VIEW = "resident/incident-form";

    @Autowired private IncidentService incidentService;
    @Autowired private UserService userService;

    /*@GetMapping("/form")
    public String getIncidentForm(Model model, Principal principal) {

        Resident resident = new Resident();

        // Fetch user details of the currently logged-in user
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUserName(username);

            // Populate a new Resident object with user details
            if (user != null) {
                resident.setFirstName(user.getFirstName());
                resident.setLastName(user.getLastName());
                resident.setMiddleName(user.getMiddleName());
                resident.setEmail(user.getEmail());
                resident.setContactNumber(user.getPhoneNumber());
                resident.setAddress(user.getAddress());
            }
        }

        // Add the Resident object to the model for the form
        model.addAttribute("resident", resident);
        model.addAttribute("incident", new Incident());
        return RESIDENT_INCIDENT_FORM_VIEW;
    }*/

    @GetMapping("/form")
    public String getIncidentForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Resident resident = new Resident();

        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = userService.findByUserName(username);

            if (user != null) {
                resident.setFirstName(user.getFirstName());
                resident.setLastName(user.getLastName());
                resident.setMiddleName(user.getMiddleName());
                resident.setEmail(user.getEmail());
                resident.setContactNumber(user.getPhoneNumber());
                resident.setAddress(user.getAddress());
            }
        }

        model.addAttribute("resident", resident);
        model.addAttribute("incident", new Incident());
        return RESIDENT_INCIDENT_FORM_VIEW;
    }


    @PostMapping("/save")
    public String saveIncident(@Valid @ModelAttribute Incident incident,
                               BindingResult bindingResult,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String middleName,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String phone) {

        //if (bindingResult.hasErrors()) { return RESIDENT_INCIDENT_FORM_VIEW; }

        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Find the user from the username
        User user = userService.findByUserName(userName);

        if (user == null) {
            incident.setFirstName(firstName);
            incident.setLastName(lastName);
            incident.setMiddleName(middleName);
            incident.setEmail(email);
            incident.setPhone(phone);
        } else {
            incident.setUser(user);
        }

        if (Objects.equals(incident.getIncidentType(), "Priority")) {
            System.out.println("test" + incident.getIncidentType());
        }

        incidentService.save(incident);
        return "redirect:/";
    }
}
