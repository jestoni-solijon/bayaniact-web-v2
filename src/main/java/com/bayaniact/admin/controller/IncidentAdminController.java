package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.service.BrgyOfficialService;
import com.bayaniact.common.service.IncidentService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/incident")
public class IncidentAdminController {

    @Autowired private IncidentService incidentService;
    @Autowired private BrgyOfficialService brgyOfficialService;

    @GetMapping("/list")
    public String getIncidentList(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "3") int size,
                                  @RequestParam(name = "status", required = false) String status,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Incident> incidentPage;

        if (status != null) {
            incidentPage = incidentService.findByIncidentType(status, pageable); // Fetch filtered events
        } else {
            incidentPage = incidentService.findAll(pageable);
        }

        model.addAttribute("brgyOfficials", brgyOfficialService.findAll());
        model.addAttribute("incident", new Incident());
        model.addAttribute("incidents", incidentPage);
        return "admin/incident-list";
    }

    @PostMapping("/delete")
    public String deleteIncident(@RequestParam(name = "incidentId") List<Long> incidentId, RedirectAttributes redirectAttributes) {

        if (incidentId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No Incident selected for deletion.");
        } else {
            incidentService.deleteById(incidentId);
            redirectAttributes.addFlashAttribute("message", "Selected Incident deleted successfully.");
        }

        return "redirect:/dashboard/incident/list";
    }

    @PostMapping("/update")
    public String updateIncident(@RequestParam(name = "incidentId", required = false) Long incidentId,
                                 @RequestParam(name = "brgyOfficialId", required = false) Long brgyOfficialId) throws MessagingException {
        if (incidentId == null || brgyOfficialId == null) {
            throw new IllegalArgumentException("Incident ID or Brgy Official ID is missing");
        }

        System.out.println("Incident ID: " + incidentId);
        System.out.println("Brgy Official ID: " + brgyOfficialId);

        incidentService.assignIncidentToBrgyOfficial(incidentId, brgyOfficialId);

        return "redirect:/dashboard/incident/list";
    }

}
