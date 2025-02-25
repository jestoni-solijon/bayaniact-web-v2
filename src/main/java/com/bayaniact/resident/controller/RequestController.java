package com.bayaniact.resident.controller;

import com.bayaniact.common.entity.Form;
import com.bayaniact.common.entity.Request;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.entity.User;
import com.bayaniact.common.security.UserService;
import com.bayaniact.common.service.FormService;
import com.bayaniact.common.service.RequestService;
import com.bayaniact.common.service.ResidentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired private FormService formService;
    @Autowired private ResidentService residentService;
    @Autowired private RequestService requestService; // Assuming you have a RequestService to handle the request logic
    @Autowired private UserService userService;

    @PostMapping
    public String saveRequest(@RequestParam("form") Long formId, HttpServletRequest request) {

        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Find the user from the username (this assumes you have a user service)
        User user = userService.findByUserName(userName);

        // Retrieve the user_uuid and find the associated resident
        String userUUID = user.getUserUUID();
        Resident resident = residentService.findByUserUUID(userUUID);
        if (resident == null) {
            throw new RuntimeException("Resident not found for userUuid: " + userUUID);
        }

        // Find the form using the formId (this assumes you have a form service)
        Form form = formService.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        // Create a new request and link it to the form and resident
        Request requestEntity = new Request();
        requestEntity.setForm(form);
        requestEntity.setResident(resident);
        requestEntity.setRequestDate(LocalDate.now().atStartOfDay()); // You can set the request date to the current date
        // Add additional fields to set if necessary (e.g. request status, etc.)

        // Save the request to the database
        requestService.save(requestEntity);

        // Redirect or return a success message
        return "redirect:/"; // Adjust the redirect as necessary
    }
}
