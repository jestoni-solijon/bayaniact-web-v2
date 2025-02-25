package com.bayaniact.common.service;

import java.util.List;
import java.util.Optional;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.repository.BrgyOfficialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrgyOfficialService {

    @Autowired private BrgyOfficialRepository brgyOfficialRepository;

    public BrgyOfficial save(BrgyOfficial brgyOfficial) {
        return brgyOfficialRepository.save(brgyOfficial);
    }

    public Optional<BrgyOfficial> findById(Long brgyOfficialId) {
        return brgyOfficialRepository.findById(brgyOfficialId);
    }

    public List<BrgyOfficial> findAll() {
        return brgyOfficialRepository.findAll();
    }

    public void deleteBrgyOfficials(List<Long> residentIds) {
        brgyOfficialRepository.deleteAllById(residentIds);
    }
}