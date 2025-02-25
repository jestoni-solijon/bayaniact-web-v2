package com.bayaniact.resident.controller;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.file.BrgyOfficialFile;
import com.bayaniact.common.service.BrgyOfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("brgy-official")
public class BrgyOfficialController {

    @Autowired
    private BrgyOfficialService brgyOfficialService;

    @GetMapping
    public String getBrgyOfficialPage(Model model) {

        model.addAttribute("brgyOfficials", brgyOfficialService.findAll());
        return "resident/brgy-official";
    }
}
