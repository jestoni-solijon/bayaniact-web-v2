package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Blotter;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.service.BlotterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard/blotter")
public class BlotterAdminController {

    @Autowired private BlotterService blotterService;

    @GetMapping("/list")
    public String getIncidentList(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  @RequestParam(name = "status", required = false) String status,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Blotter> blotterPage;

        /*f (status != null) {
            blotterPage = blotterService.findByIncidentType(status, pageable); // Fetch filtered events
        } else {
            blotterPage = blotterService.findAll(pageable);
        }*/
        blotterPage = blotterService.findAll(pageable);

        //model.addAttribute("brgyOfficials", brgyOfficialService.findAll());
        model.addAttribute("blotter ", new Blotter());
        model.addAttribute("blotters", blotterPage);
        return "admin/blotter-list";
    }
}
