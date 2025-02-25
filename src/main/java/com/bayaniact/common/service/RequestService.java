package com.bayaniact.common.service;

import com.bayaniact.common.email.EmailService;
import com.bayaniact.common.entity.Request;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.repository.RequestRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired private RequestRepository requestRepository;

    @Autowired private EmailService emailService;

    public void save(Request request) {
        requestRepository.save(request);
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    // Update status of a specific request by requestId
    public void updateStatus(Long requestId, byte status) throws MessagingException {
        // Find the request by id
        Optional<Request> requestOptional = requestRepository.findById(requestId);

        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            // Update the status
            request.setStatus(status);
            // Save the updated request
            requestRepository.save(request);

            String recipientEmail = request.getResident().getEmail();
            String recipientFirstName = request.getResident().getFirstName();
            String recipientLastName = request.getResident().getLastName();
            Double formPrice = request.getForm().getFormPrice();

            if (status == 2) {
                emailService.sendDeclinedFormRequest(recipientEmail, recipientFirstName, recipientLastName);
            } else if (status == 1) {
                emailService.sendApprovedFormRequest(request);
            }

        } else {
            // Handle the case where the request is not found (optional)
            throw new RuntimeException("Request not found with id: " + requestId);
        }
    }

    public List<Request> findByResident(Resident resident) {
        return requestRepository.findByResident(resident);
    }
}
