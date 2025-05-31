package com.bayaniact.common.service;

import com.bayaniact.common.entity.Blotter;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.repository.BlotterRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlotterService {

    @Autowired private BlotterRepository blotterRepository;

    public void save(Blotter blotter) {
        blotterRepository.save(blotter);
    }

    public Page<Blotter> findAll(Pageable pageable) { return blotterRepository.findAll(pageable); }

    public void deleteById(List<Long> incidentId) { blotterRepository.deleteAllById(incidentId);}

    public void updateBlotterSchedule(Long blotterId, LocalDateTime schedule) throws MessagingException {
        Optional<Blotter> blotterOptional = blotterRepository.findById(blotterId);

        if (blotterOptional.isPresent()) {
            Blotter blotter = blotterOptional.get();
            blotter.setSchedule(schedule);

            blotterRepository.save(blotter);
        } else {
            throw new RuntimeException("Incident not found");
        }
    }

    public void updateBlotterStatus(Long blotterId, Blotter.BlotterStatus status) throws MessagingException {
        Optional<Blotter> blotterOptional = blotterRepository.findById(blotterId);

        if (blotterOptional.isPresent()) {
            Blotter blotter = blotterOptional.get();
            blotter.setBlotterStatus(status);

            blotterRepository.save(blotter);
        } else {
            throw new RuntimeException("Blotter not found");
        }
    }

    public List<Blotter> findBlotterByUserUUID(String userUUID) {
        return blotterRepository.findByUser_UserUUID(userUUID);
    }


}
