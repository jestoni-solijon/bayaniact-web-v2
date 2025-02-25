package com.bayaniact.resident.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import com.bayaniact.common.constant.RequestMappingConst;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.entity.User;
import com.bayaniact.common.file.FileService;
import com.bayaniact.util.medical.MedicalCondition;
import com.bayaniact.util.medical.MedicalConditionService;
import com.bayaniact.util.purpose.CertificationPurpose;
import com.bayaniact.util.purpose.CertificationPurposeService;
import com.bayaniact.common.security.UserService;
import com.bayaniact.common.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/resident")
public class ResidentController {

    // Autowired services for business logic and data handling
    @Autowired private ResidentService residentService;
    @Autowired private UserService userService;
    @Autowired private FileService fileService;
    @Autowired private MedicalConditionService medicalConditionService;
    @Autowired private CertificationPurposeService certificationPurposeService;

    /**
     * Displays the resident form pre-filled with user details.
     *
     * @param model     Model object to pass data to the view.
     * @param principal Currently authenticated user's principal information.
     * @return The view name for the resident form.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_FORM_PATH)
    public String getResidentForm(Model model, Principal principal) {

        // Fetch user details of the currently logged-in user
        String username = principal.getName();
        User user = userService.findByUserName(username);

        // Populate a new Resident object with user details
        Resident resident = new Resident();
        resident.setFirstName(user.getFirstName());
        resident.setLastName(user.getLastName());
        resident.setMiddleName(user.getMiddleName());
        resident.setEmail(user.getEmail());
        resident.setContactNumber(user.getPhoneNumber());
        resident.setAddress(user.getAddress());

        // Add the Resident object to the model for the form
        model.addAttribute("resident", resident);

        return "resident/resident"; // Return the resident form view
    }

    /**
     * Saves a resident's information, including optional file uploads and associations.
     *
     * @param resident                 The Resident object to save.
     * @param bindingResult            Object for handling validation errors.
     * @param file                     Optional file to upload.
     * @param conditionIds             IDs of selected medical conditions (optional).
     * @param certificationPurposeIds  IDs of selected certification purposes (optional).
     * @param principal                Currently authenticated user's principal information.
     * @return Redirect URL after saving the resident.
     * @throws IOException If an error occurs during file processing.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_SAVE_PATH)
    public String saveResident(@Valid @ModelAttribute Resident resident, BindingResult bindingResult,
                               @RequestParam(name = "file", required = false) MultipartFile file,
                               @RequestParam(value = "medicalHistory", required = false) List<Long> conditionIds,
                               @RequestParam(value = "certificationPurpose", required = false) List<Integer> certificationPurposeIds,
                               Principal principal) throws IOException {

        // Check for validation errors in the form submission
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + " - " + error.getDefaultMessage());
            });
            return "resident/resident"; // Return the form view with errors
        }

        // Associate the resident with the currently authenticated user
        User user = userService.findByUserName(principal.getName());
        resident.setUser(user);

        // Process and associate medical conditions if provided
        if (conditionIds != null) {
            List<MedicalCondition> selectedConditions = medicalConditionService.findByIds(conditionIds);
            resident.setMedicalHistory(selectedConditions);
        }

        // Process and associate certification purposes if provided
        if (certificationPurposeIds != null) {
            List<CertificationPurpose> selectedPurpose = certificationPurposeService.findByIds(certificationPurposeIds);
            resident.setCertificationPurpose(selectedPurpose);
        }

        System.out.println(resident.getFirstName());
        System.out.println(resident.getMiddleName());
        // Save the resident to the database
        Resident savedResident = residentService.save(resident);

        // Process the optional file upload
        if (file != null && !file.isEmpty()) {
            fileService.saveFile(file, savedResident);
        }

        return "redirect:/"; // Redirect to the home page after saving
    }
}

