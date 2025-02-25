package com.bayaniact.resident.controller;

import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.entity.Request;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.entity.User;
import com.bayaniact.common.security.UserService;
import com.bayaniact.common.service.IncidentService;
import com.bayaniact.common.service.RequestService;
import com.bayaniact.common.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired private RequestService requestService;
    @Autowired private UserService userService;
    @Autowired private ResidentService residentService;
    @Autowired private IncidentService incidentService;

    @GetMapping
    public String getAccountPage(Model model, Principal principal) {
        String username = principal.getName();  // From Spring Security
        User user = userService.findByUserName(username);

        if (user == null) { return "error"; } // Return error if user is not found

        Resident resident = residentService.findByUserUUID(user.getUserUUID());

        if (resident != null) {
            List<Request> requests = requestService.findByResident(resident);
            model.addAttribute("requests", requests);
        } else {
            model.addAttribute("message", "Apply for residency.");
        }

        List<Incident> incidents = incidentService.findIncidentByUserUUID(user.getUserUUID());

        if (incidents != null) {
            System.out.println(incidents);
            model.addAttribute("incidents", incidents);
        }

        // Ensure 'user' is always added to the model before returning the view
        model.addAttribute("user", user);

        return "resident/account";
    }


}
