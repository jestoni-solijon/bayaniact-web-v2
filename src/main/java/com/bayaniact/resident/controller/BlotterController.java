package com.bayaniact.resident.controller;

import com.bayaniact.common.email.EmailService;
import com.bayaniact.common.entity.Blotter;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.entity.User;
import com.bayaniact.common.security.UserService;
import com.bayaniact.common.service.BlotterService;
import com.bayaniact.common.service.BrgyOfficialService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/blotter")
public class BlotterController {

    @Autowired private UserService userService;
    @Autowired private BrgyOfficialService brgyOfficialService;
    @Autowired private EmailService emailService;
    @Autowired private BlotterService blotterService;

    @GetMapping("/form")
    public String getBlotterPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
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
        model.addAttribute("blotter", new Blotter());
        return "resident/blotter";
    }

    @PostMapping("/save")
    public String saveBlotter(@Valid @ModelAttribute Blotter blotter,
                               BindingResult bindingResult,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String middleName,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String phone) throws MessagingException {

        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Find the user from the username
        User user = userService.findByUserName(userName);

        if (user != null) {
            blotter.setFirstName(user.getFirstName());
            blotter.setLastName(user.getLastName());
            blotter.setEmail(user.getEmail());
            blotter.setPhone(user.getPhoneNumber());
        }

        if (user == null) {
            blotter.setFirstName(firstName);
            blotter.setLastName(lastName);
            blotter.setMiddleName(middleName);
            blotter.setEmail(email);
            blotter.setPhone(phone);
        } else {
            blotter.setUser(user);
        }

        blotterService.save(blotter);

        return "redirect:/blotter/form";
    }

}
