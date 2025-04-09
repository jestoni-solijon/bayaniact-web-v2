package com.bayaniact.common.service;

import com.bayaniact.common.entity.Blotter;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.repository.BlotterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlotterService {

    @Autowired private BlotterRepository blotterRepository;

    public void save(Blotter blotter) {
        blotterRepository.save(blotter);
    }

    public Page<Blotter> findAll(Pageable pageable) { return blotterRepository.findAll(pageable); }

    public void deleteById(List<Long> incidentId) { blotterRepository.deleteAllById(incidentId);}


}
