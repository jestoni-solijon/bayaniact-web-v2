package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Event;
import com.bayaniact.common.entity.Request;
import com.bayaniact.common.service.RequestService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard/request")
public class RequestAdminController {

    @Autowired private RequestService requestService;

    /*@GetMapping("/list")
    public String getRequestList(Model model) {

        model.addAttribute("requestObj", new Request());
        model.addAttribute("requests", requestService.findAll());
        return "admin/request-list";
    }*/

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
                              @RequestParam("status") byte status) throws MessagingException {

        requestService.updateStatus(requestId, status);
        return "redirect:/dashboard/request/list";
    }
}
