package com.bayaniact.resident.controller;

import com.bayaniact.common.service.BrgyOfficialService;
import com.bayaniact.common.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    private static final String RESIDENT_INDEX_VIEW = "resident/index";

    @GetMapping
    public String getIndex(Model model) { return RESIDENT_INDEX_VIEW; }

    @GetMapping("/privacy")
    public String getPrivacyPage() { return "resident/privacy"; }

    @GetMapping("/contact")
    public String getContactUsPage() { return "resident/contact"; }

    @GetMapping("/about")
    public String getAboutPage() {
        return "resident/about";
    }

    @GetMapping("/term")
    public String getTermsAndServicesPage() {
        return "term";
    }
}

