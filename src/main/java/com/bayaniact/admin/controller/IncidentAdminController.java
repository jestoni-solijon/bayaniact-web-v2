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
    public String getIncidentList(Model model) {
        model.addAttribute("brgyOfficials", brgyOfficialService.findAll());
        model.addAttribute("incident", new Incident());
        model.addAttribute("incidents", incidentService.findAll());
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
