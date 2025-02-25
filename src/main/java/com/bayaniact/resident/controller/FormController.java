package com.bayaniact.resident.controller;

import com.bayaniact.common.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormController {

    @Autowired private FormService formService;

    @GetMapping
    public String getFormPage(Model model) {

        model.addAttribute("forms", formService.findAll());
        return "resident/form";
    }
}
