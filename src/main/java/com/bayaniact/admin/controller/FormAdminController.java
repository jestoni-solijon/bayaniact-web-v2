package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Form;
import com.bayaniact.common.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/form")
public class FormAdminController {

    @Autowired
    private FormService formService;

    @GetMapping("/manage")
    public String getManageFormPage(Model model) {

        model.addAttribute("forms", formService.findAll());
        model.addAttribute("form", new Form());
        return "admin/manage-form";
    }

    @PostMapping("/save")
    public String saveEvent(@ModelAttribute("form") Form form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Print each error
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + " - " + error.getDefaultMessage());
            });

            return "admin/manage-form";
        }
        formService.save(form);

        return "redirect:/dashboard/form/manage";
    }

    @PostMapping("/delete")
    public String deleteEvent(@RequestParam(name = "formId") List<Long> formId, RedirectAttributes redirectAttributes) {

        if (formId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No form selected for deletion.");
        } else {
            formService.deleteById(formId);
            redirectAttributes.addFlashAttribute("message", "Selected form deleted successfully.");
        }

        return "redirect:/dashboard/form/manage";
    }

}
