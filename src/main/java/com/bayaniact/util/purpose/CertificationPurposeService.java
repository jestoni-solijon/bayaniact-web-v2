package com.bayaniact.util.purpose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationPurposeService {

    @Autowired
    private CertificationPurposeRepository certificationPurposeRepository;

    public List<CertificationPurpose> findByIds(List<Integer> ids) {
        return certificationPurposeRepository.findAllById(ids);
    }

    public List<CertificationPurpose> findAll() {
        return certificationPurposeRepository.findAll();
    }
}
