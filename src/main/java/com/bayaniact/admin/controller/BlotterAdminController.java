package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Blotter;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.service.BlotterService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/dashboard/blotter")
public class BlotterAdminController {

    @Autowired private BlotterService blotterService;

    @GetMapping("/list")
    public String getBlotterList(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  @RequestParam(name = "status", required = false) String status,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Blotter> blotterPage;

       // if (status == null) {
         //   blotterPage = blotterService.findAll(pageable);
            //blotterPage = blotterService.findByIncidentType(status, pageable); // Fetch filtered events
        //}
        //else {

        //}
        blotterPage = blotterService.findAll(pageable);

        //model.addAttribute("brgyOfficials", brgyOfficialService.findAll());
        model.addAttribute("blotter ", new Blotter());
        model.addAttribute("blotters", blotterPage);
        return "admin/blotter-list";
    }

    @GetMapping("/appointment")
    public String getBlotterAppointments(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  @RequestParam(name = "status", required = false) String status,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Blotter> blotterPage;

        blotterPage = blotterService.findAll(pageable);

        //model.addAttribute("brgyOfficials", brgyOfficialService.findAll());
        model.addAttribute("blotter ", new Blotter());
        model.addAttribute("blotters", blotterPage);
        model.addAttribute("blotterStatuses", Blotter.BlotterStatus.values());
        return "admin/blotter-appointment";
    }


    @PostMapping("/update/schedule")
    public String updateBlotterSchedule(@RequestParam(name = "schedule", required = false) LocalDateTime schedule,
                                        @RequestParam(name = "blotterId") Long blotterId) throws MessagingException {

        System.out.println(schedule + "" + blotterId);
        blotterService.updateBlotterSchedule(blotterId, schedule);

        return "redirect:/dashboard/blotter/appointment";
    }

    @PostMapping("/update/status")
    public String updateIncidentStatus(@RequestParam("blotterId") Long blotterId,
                                       @RequestParam("status") int statusOrdinal) throws MessagingException {
        Blotter.BlotterStatus status = Blotter.BlotterStatus.values()[statusOrdinal];
        blotterService.updateBlotterStatus(blotterId, status);
        return "redirect:/dashboard/blotter/appointment";
    }
}
