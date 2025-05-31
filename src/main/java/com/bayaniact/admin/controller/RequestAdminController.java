package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Request;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.service.PdfService;
import com.bayaniact.common.service.RequestService;
import com.bayaniact.common.service.ResidentService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/request")
public class RequestAdminController {

    @Autowired private RequestService requestService;
    @Autowired private PdfService pdfService;
    @Autowired private ResidentService residentService;

    @GetMapping("/list")
    public String getRequestList(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                 @RequestParam(name = "status", required = false) Integer status, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Request> requestPage;

        if (status != null) {
            requestPage = requestService.findByRequestStatus(status, pageable); // Fetch filtered events
        } else {
            requestPage = requestService.findAll(pageable); // Fetch all events
        }

        model.addAttribute("requestObj", new Request()); // Event object
        model.addAttribute("requests", requestPage); // Paginated event list
        model.addAttribute("selectedStatus", status); // Preserve selected status in the dropdown

        return "admin/request-list";
    }

    @PostMapping
    public String editRequest(@RequestParam("requestId") Long requestId,
                              @RequestParam("status") byte status,
                              @RequestParam("pickupDate") LocalDateTime pickupDate) throws MessagingException {

        requestService.updateStatus(requestId, status, pickupDate);
        return "redirect:/dashboard/request/list";
    }

    @GetMapping("/pdf/generate")
    public void downloadInvoice(HttpServletResponse response,
                                @RequestParam(name = "residentId", required = false) Long residentId) throws IOException {

        Map<String, Object> data = new HashMap<>();

        if (residentId != null) {
            Optional<Resident> residentOpt = residentService.findById(residentId);
            if (residentOpt.isPresent()) {
                Resident resident = residentOpt.get();

                String firstName = capitalize(resident.getFirstName());
                String middleInitial = resident.getMiddleName() != null && !resident.getMiddleName().isEmpty()
                        ? resident.getMiddleName().substring(0, 1).toUpperCase() + "."
                        : "";
                String lastName = capitalize(resident.getLastName());
                String fullName = String.format("%s %s %s", firstName, middleInitial, lastName);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                String formattedBirthDate = resident.getBirthDate().format(formatter);

                data.put("name", fullName);
                data.put("birthdate", formattedBirthDate);
            } else {
                // Handle resident not found
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resident not found");
                return;
            }
        } else {
            // Handle missing residentId
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing residentId parameter");
            return;
        }

        DateTimeFormatter issueDateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedIssueDate = LocalDate.now().format(issueDateFormatter);
        data.put("issueDate", formattedIssueDate);
        data.put("bdzLogo", "data:image/png;base64," + encodeImageToBase64("static/images/logo/bdz.png"));
        data.put("lqpLogo", "data:image/png;base64," + encodeImageToBase64("static/images/logo/lqp.png"));

        byte[] pdfBytes = pdfService.generatePdf(data, "pdf/certificate-residency");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=certificate.pdf");
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }


    private String encodeImageToBase64(String imagePath) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(imagePath);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return Base64.getEncoder().encodeToString(bytes);
    }

}
