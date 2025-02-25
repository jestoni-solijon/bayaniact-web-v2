package com.bayaniact.admin.controller;

import com.bayaniact.common.entity.Request;
import com.bayaniact.common.service.RequestService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/list")
    public String getRequestList(Model model) {

        model.addAttribute("requestObj", new Request());
        model.addAttribute("requests", requestService.findAll());
        return "admin/request-list";
    }

    @PostMapping
    public String editRequest(@RequestParam("requestId") Long requestId,
                              @RequestParam("status") byte status) throws MessagingException {

        requestService.updateStatus(requestId, status);
        return "redirect:/dashboard/request/list";
    }
}
