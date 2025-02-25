package com.bayaniact.admin.controller;

import com.bayaniact.common.constant.ApplicationMessageConst;
import com.bayaniact.common.constant.RequestMappingConst;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.entity.User;
import com.bayaniact.util.enums.ApprovalStatus;
import com.bayaniact.common.file.ExcelService;
import com.bayaniact.common.file.FileService;
import com.bayaniact.common.security.UserService;
import com.bayaniact.common.service.ResidentService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/resident")
public class ResidentAdminController {

    // Injecting required services for resident management
    @Autowired private ResidentService residentService;
    @Autowired private UserService userService;
    @Autowired private FileService fileService;
    @Autowired private ExcelService excelService;

    /**
     * Fetches a specific resident by ID and displays the resident form.
     *
     * @param residentId Optional resident ID to fetch the resident details.
     * @param model      Model object to pass data to the view.
     * @return View name for the resident form.
     */
    @GetMapping
    public String getResident(@RequestParam(name = "residentId", required = false) Long residentId, Model model) {
        Optional<Resident> resident = residentService.findById(residentId); // Fetch resident by ID
        model.addAttribute("resident", resident); // Add resident details to the model
        return "admin/resident-form"; // Return resident form view
    }

    /**
     * Displays an empty resident form for creating a new resident.
     *
     * @param model Model object to pass data to the view.
     * @return View name for the resident form.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_ADMIN_FORM_PATH)
    public String getResidentForm(Model model) {
        model.addAttribute("resident", new Resident()); // Add an empty Resident object to the model
        return "admin/resident-form"; // Return resident form view
    }

    /**
     * Saves a resident's details and optional file upload.
     *
     * @param resident      Resident object with validated data.
     * @param file          Optional file uploaded with the resident.
     * @param bindingResult Binding result for validation errors.
     * @param principal     Currently authenticated user.
     * @param model         Model object to pass data to the view.
     * @return Redirect URL or view name based on processing result.
     * @throws IOException If file upload processing fails.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_ADMIN_SAVE_PATH)
    public String saveResident(@Valid @ModelAttribute Resident resident,
                               BindingResult bindingResult,
                               @RequestParam(name = "file", required = false) MultipartFile file,
                               Principal principal, Model model) throws IOException {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + " - " + error.getDefaultMessage());
            });
            return "admin/resident-form"; // Return form view with errors
        }

        // Set the authenticated user as the resident's user
        User user = userService.findByUserName(principal.getName());
        resident.setUser(user);

        // Save the resident and handle optional file upload
        Resident savedResident = residentService.save(resident);
        if (file != null && !file.isEmpty()) {
            fileService.saveFile(file, savedResident);
        }

        return "redirect:/dashboard/resident/list"; // Redirect to the resident list
    }

    /**
     * Retrieves a paginated list of residents.
     *
     * @param page  Page number for pagination.
     * @param size  Number of items per page.
     * @param model Model object to pass data to the view.
     * @return View name for the resident list.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_ADMIN_LIST_PATH)
    public String getResidentList(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  Model model) {
        Pageable pageable = PageRequest.of(page, size); // Create pageable object
        Page<Resident> residentsPage = residentService.findAll(pageable); // Fetch paginated residents
        model.addAttribute("resident", new Resident()); // Fetch resident Object to view
        model.addAttribute("residents", residentsPage); // Add residents to the model
        return "admin/resident-list"; // Return resident list view
    }

    /**
     * Updates the approval status of a resident.
     *
     * @param residentId ID of the resident to update.
     * @param status     New approval status value.
     * @return Redirect URL after updating the status.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_ADMIN_UPDATE_STATUS_PATH)
    public String updateResidentStatus(@RequestParam Long residentId, @RequestParam String status, @RequestParam(value = "reason", required = false) String reason) {
        status = status.trim().replaceAll("^,|,$", ""); // Clean up status input

        try {
            ApprovalStatus approvalStatus = ApprovalStatus.fromValue(status); // Convert to enum
            residentService.updateStatus(residentId, approvalStatus, reason); // Update the status
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage()); // Log invalid status
            return "error"; // Return error view
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/dashboard/resident/list"; // Redirect to resident list
    }

    /**
     * Searches for residents based on multiple filters.
     *
     * @param firstName  First name filter.
     * @param lastName   Last name filter.
     * @param middleName Middle name filter.
     * @param birthDate  Birthdate filter.
     * @param status     Approval status filter.
     * @param pageable   Pagination information.
     * @param model      Model object to pass data to the view.
     * @return View name for the resident list.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_ADMIN_SEARCH_PATH)
    public String searchResidents(@RequestParam(value = "firstName", required = false) String firstName,
                                  @RequestParam(value = "lastName", required = false) String lastName,
                                  @RequestParam(value = "middleName", required = false) String middleName,
                                  @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
                                  @RequestParam(value = "status", required = false) String status,
                                  Pageable pageable, Model model) {
        Page<Resident> residentsPage = residentService.findByFilters(firstName, lastName, status, middleName, birthDate, pageable);
        model.addAttribute("residents", residentsPage); // Add search results to the model
        return "admin/resident-list"; // Return resident list view
    }

    /**
     * Deletes selected residents by their IDs.
     *
     * @param residentId         List of resident IDs to delete.
     * @param redirectAttributes Attributes for redirect messages.
     * @return Redirect URL after deletion.
     */
    @PostMapping(value = RequestMappingConst.RESIDENT_ADMIN_DELETE_PATH)
    public String deleteResident(@RequestParam(name = "residentId") List<Long> residentId, RedirectAttributes redirectAttributes) {
        if (residentId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", ApplicationMessageConst.NO_RESIDENT_SELECTED_FOR_DELETION);
        } else {
            residentService.deleteResidents(residentId); // Delete residents by IDs
            redirectAttributes.addFlashAttribute("message", ApplicationMessageConst.SELECTED_RESIDENT_DELETED_SUCCESSFULLY);
        }
        return "redirect:/dashboard/resident/list"; // Redirect to resident list
    }

    /**
     * Exports resident data to an Excel file.
     *
     * @param response HTTP response for writing the Excel file.
     * @throws IOException If file export fails.
     */
    @GetMapping(value = RequestMappingConst.RESIDENT_ADMIN_EXPORT_PATH)
    public void downloadResidentsExcel(HttpServletResponse response) throws IOException {
        excelService.exportResidentsToExcel(response); // Export residents to Excel
    }
}

