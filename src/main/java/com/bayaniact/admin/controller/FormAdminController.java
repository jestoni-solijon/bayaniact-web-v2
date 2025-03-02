package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Form;
import com.bayaniact.common.entity.Request;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String getManageFormPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "10") int size, Model model) {

        Pageable pageable = PageRequest.of(page, size); // Create pageable object
        Page<Form> formPage = formService.findAllIncident(pageable); // Fetch paginated residents
        model.addAttribute("formPage", formPage); // Fetch resident Object to view
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
